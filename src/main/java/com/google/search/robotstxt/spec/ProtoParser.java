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

import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/** Class for parsing Protocol Buffer test cases */
public class ProtoParser {

  /**
   * Creates TestInfo objects (from the Protocol Buffer Objects) and adds them to a list
   *
   * @param robotsTxtSpec The Protocol Buffer Object
   * @param testCases The list
   */
  private void addTestInfoObjects(
      SpecificationProtos.RobotsTxtSpecification robotsTxtSpec, ArrayList<TestInfo> testCases) {
    for (SpecificationProtos.RobotsTxtTest robotsTxtTest : robotsTxtSpec.getTestsList()) {
      String robotsTxtContent = robotsTxtTest.getRobotstxt();

      for (SpecificationProtos.Expectation expectation : robotsTxtTest.getTestSituationsList()) {
        TestInfo testInfo =
            new TestInfo(
                robotsTxtContent,
                expectation.getTesturl(),
                expectation.getUseragent(),
                expectation.getExpectedOutcome(),
                expectation.getAdditionalExplanation());
        testCases.add(testInfo);
      }
    }
  }

  /**
   * Creates a list of TestInfo objects based on multiple files
   *
   * @param dirPath The path to the directory containing the files
   * @return The list of TestInfo objects
   */
  public ArrayList<TestInfo> readMessages(String dirPath)
      throws java.io.FileNotFoundException, java.io.IOException {

    File dir = new File(dirPath);
    File[] allFiles = dir.listFiles();
    ArrayList<TestInfo> testCases = new ArrayList<>();
    for (File testFile : allFiles) {
      SpecificationProtos.RobotsTxtSpecification robotsTxtSpec =
          SpecificationProtos.RobotsTxtSpecification.parseFrom(new FileInputStream(testFile));
      addTestInfoObjects(robotsTxtSpec, testCases);
    }

    return testCases;
  }
}
