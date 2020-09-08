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

/** Holds the results of the tests performed */
public class TestsResult {
  private int totalNumberComplianceTests;
  private int totalNumberUserTests;
  private int numberFailedComplianceTests;
  private int numberFailedUserTests;

  /** Default constructor */
  public TestsResult() {}

  /** Reports the success of one Complience Test */
  public void reportSuccessComplianceTests() {
    this.totalNumberComplianceTests++;
    System.out.println("COMPLIANCE TEST #" + totalNumberComplianceTests + " PASSED");
  }

  /** Reports the success of one user test */
  public void reportSuccessUserTests() {
    this.totalNumberUserTests++;
    System.out.println("USER TEST #" + totalNumberUserTests + "PASSED");
  }

  /**
   * Adds the failure of one Compliance Test
   *
   * @param failedTest The info about the failed test
   * @param userOutcome The outcome of the parser
   */
  public void reportFailureComplianceTests(TestInfo failedTest, TestOutcome testOutcome) {
    this.totalNumberComplianceTests++;
    this.numberFailedComplianceTests++;
    System.out.println("---------------------------------");
    System.out.println("COMPLIANCE TEST #" + totalNumberComplianceTests + " FAILED");
    System.out.println(failedTest.toString());
    System.out.println("Your outcome: " + testOutcome.outcome().toString());
    System.out.println();
    System.out.println("Exit code: " + testOutcome.exitCode());
    System.out.println("STDOUT:\n" + testOutcome.stdOut());
    System.out.println();
    System.out.println("STDERR:\n" + testOutcome.stdErr());
    System.out.println("---------------------------------");
  }

  /**
   * Adds the failure of one user test
   *
   * @param failedTest The info about the failed test
   * @param userOutcome The outcome of the parser
   */
  public void reportFailureUserTests(TestInfo failedTest, TestOutcome testOutcome) {
    this.totalNumberUserTests++;
    this.numberFailedUserTests++;
    System.out.println("---------------------------------");
    System.out.println("USER TEST #" + totalNumberUserTests + "FAILED");
    System.out.println(failedTest.toString());
    System.out.println("Your outcome: " + testOutcome.outcome().toString());
    System.out.println();
    System.out.println("Exit code: " + testOutcome.exitCode());
    System.out.println("STDOUT:\n" + testOutcome.stdOut());
    System.out.println();
    System.out.println("STDERR:\n" + testOutcome.stdErr());
    System.out.println("---------------------------------");
  }

  @Override
  public String toString() {
    String result =
        "TEST RESULTS:"
            + "\n"
            + "Number of Compliance Tests: "
            + this.totalNumberComplianceTests
            + "\nNumber of Compliance Tests Failed: "
            + this.numberFailedComplianceTests
            + "\nNumber of user's tests: "
            + this.totalNumberUserTests
            + "\nNumber of user's tests failed: "
            + this.numberFailedUserTests;

    if (this.numberFailedComplianceTests == 0) {
      result = result + "\n\n" + "The parser FOLLOWS the standard";
    } else {
      result = result + "\n\n" + "The parser DOES NOT FOLLOW the standard";
    }

    return result;
  }
}
