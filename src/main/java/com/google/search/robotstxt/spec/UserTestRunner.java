// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.search.robotstxt.spec;

import com.google.search.robotstxt.spec.ParserMatcher.TestOutcome;
import java.util.List;

/** Runs User Test Cases */
public class UserTestRunner implements TestRunner {
  @Override
  public void runTests(
      List<TestInfo> testCases, ParserMatcher parserMatcher, CMDArgs cmdArgs, TestsResult result)
      throws Exception {
    // Calculate the total number of User test cases
    result.setTotalNumberUserTests(testCases.size());

    // Run each test case
    for (TestInfo testInfo : testCases) {
      TestOutcome userOutcome =
          parserMatcher.getOutcome(
              testInfo.getRobotsTxtContent(), testInfo.getUrl(), testInfo.getUserAgent(), cmdArgs);

      if (userOutcome.outcome() == testInfo.getExpectedOutcome()) {
        result.reportSuccessUserTests();
      } else {
        result.reportFailureUserTests(testInfo, userOutcome);
      }
    }
  }
}
