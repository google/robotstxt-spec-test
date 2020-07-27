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

import com.google.search.robotstxt.spec.testfile.TestFileProtos;
import java.util.ArrayList;

/** Holds the results of the tests performed */
public class TestsResult {
  private int totalNumberComplianceTests;
  private int totalNumberUserTests;
  private int numberFailedComplianceTests;
  private int numberFailedUserTests;
  ArrayList<Pair<TestInfo, TestFileProtos.Outcome>> failedComplianceTests;
  ArrayList<Pair<TestInfo, TestFileProtos.Outcome>> failedUserTests;

  /** Default constructor */
  public TestsResult() {}

  /** Reports the success of one Complience Test */
  public void reportSuccessComplianceTests() {
    this.totalNumberComplianceTests++;
  }

  /** Reports the success of one user test */
  public void reportSuccessUserTests() {
    this.totalNumberUserTests++;
  }

  /**
   * Adds the failure of one Compliance Test
   *
   * @param failedTest The info about the failed test
   * @param userOutcome The outcome of the parser
   */
  public void reportFailureComplianceTests(
      TestInfo failedTest, TestFileProtos.Outcome userOutcome) {
    this.totalNumberComplianceTests++;
    this.numberFailedComplianceTests++;
    this.failedComplianceTests.add(new Pair(failedTest, userOutcome));
    System.out.println("COMPLIANCE TEST FAILED");
    System.out.println(failedTest.toString());
    System.out.println("Your outcome: " + userOutcome.toString());
    System.out.println();
  }

  /**
   * Adds the failure of one user test
   *
   * @param failedTest The info about the failed test
   * @param userOutcome The outcome of the parser
   */
  public void reportFailureUserTests(TestInfo failedTest, TestFileProtos.Outcome userOutcome) {
    this.totalNumberUserTests++;
    this.numberFailedUserTests++;
    this.failedUserTests.add(new Pair(failedTest, userOutcome));
    System.out.println("USER TEST FAILED");
    System.out.println(failedTest.toString());
    System.out.println("Your outcome: " + userOutcome.toString());
    System.out.println();
  }

  @Override
  public String toString() {
    String result =
        "TEST RESULTS: \n\n"
            + "Number of Compliance Tests: "
            + this.totalNumberComplianceTests
            + "\nNumber of Compliance Tests Failed: "
            + this.failedComplianceTests
            + "\nNumber of user's tests: "
            + this.totalNumberUserTests
            + "\nNumber of user's tests failed: "
            + this.numberFailedUserTests;

    if (this.numberFailedComplianceTests == 0) {
      result = result + "\n\nThe parser FOLLOWS the standard";
    } else {
      result = result + "\n\nThe parser DOES NOT FOLLOW the standard";
    }

    return result;
  }
}
