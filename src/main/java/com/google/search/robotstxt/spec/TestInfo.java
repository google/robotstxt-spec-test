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

/** Information about a specific test case */
public class TestInfo {
  private String robotsTxtContent;
  private String url;
  private String userAgent;
  private SpecificationProtos.Outcome expectedOutcome;
  private String additionalExplanation;

  /** Default constructor */
  public TestInfo() {}

  /**
   * Constructor with parameters (sets all the fields
   *
   * @param robotsTxtContent The robots.txt file contents
   * @param url The URL
   * @param userAgent The user-agent
   * @param expectedOutcome The expected outcome
   * @param additionalExplanation The additional explanation
   */
  public TestInfo(
      String robotsTxtContent,
      String url,
      String userAgent,
      SpecificationProtos.Outcome expectedOutcome,
      String additionalExplanation) {
    this.robotsTxtContent = robotsTxtContent;
    this.url = url;
    this.userAgent = userAgent;
    this.expectedOutcome = expectedOutcome;
    this.additionalExplanation = additionalExplanation;
  }

  public String getRobotxtxtContent() {
    return robotsTxtContent;
  }

  public String getUrl() {
    return url;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public SpecificationProtos.Outcome getExpectedOutcome() {
    return expectedOutcome;
  }

  public String getAdditionalExplanation() {
    return additionalExplanation;
  }

  public String toString() {
    return "The robots.txt content: \n\n"
        + this.robotsTxtContent
        + "\n\n"
        + "The URL: "
        + this.url
        + "\n"
        + "The user-agent: "
        + this.userAgent
        + "\n"
        + "The expected outcome: "
        + this.expectedOutcome
        + "\n"
        + "The additional explanation: "
        + this.additionalExplanation
        + "\n";
  }
}
