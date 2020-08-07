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

import java.io.File;
import java.util.regex.Matcher;

/** Flags setup by Command Line Options */
public class CMDArgs {
  private String callParserCommand;
  private String myTestsDir;
  private OutputType mode;
  private String allowedPattern;
  private String disallowedPattern;

  /** Default constructor */
  public CMDArgs() {}

  /**
   * Constructor with parameters
   *
   * @param callParserCommand The command that calls the parser
   * @param myTestsDir The directory of the user's tests
   * @param mode The output mode
   * @param allowedPattern The allowed pattern (regular expression)
   * @param disallowedPattern The disallowed pattern (regular expression)
   */
  public CMDArgs(
      String callParserCommand,
      String myTestsDir,
      OutputType mode,
      String allowedPattern,
      String disallowedPattern) {
    this.callParserCommand = callParserCommand;
    this.callParserCommand =
        this.callParserCommand.replaceFirst(
            "^~/", Matcher.quoteReplacement(System.getProperty("user.home") + "/"));

    this.myTestsDir = myTestsDir;
    this.myTestsDir =
        this.myTestsDir.replaceFirst(
            "^~/", Matcher.quoteReplacement(System.getProperty("user.home") + "/"));

    if (mode == null) {
      this.mode = OutputType.EXITCODE;
    } else {
      this.mode = mode;
    }

    if (allowedPattern == null) {
      this.allowedPattern = "0";
    } else {
      this.allowedPattern = allowedPattern;
    }

    if (disallowedPattern == null) {
      this.disallowedPattern = "1";
    } else {
      this.disallowedPattern = disallowedPattern;
    }
  }

  public String getCallParserCommand() {
    return callParserCommand;
  }

  public String getMyTestsDir() {
    return myTestsDir;
  }

  public OutputType getMode() {
    return mode;
  }

  public String getAllowedPattern() {
    return allowedPattern;
  }

  public String getDisallowedPattern() {
    return disallowedPattern;
  }

  private void validateAsExitcode(String value, String flagname) {
    try {
      int exitPattern = Integer.parseInt(value);
    } catch (NumberFormatException numberFormatException) {
      throw new IllegalArgumentException(
          flagname + ": The exit code number must be an integer between 0 and 255");
    }

    if (Integer.parseInt(value) < 0) {
      throw new IllegalArgumentException(
          flagname + ": The exit code number must be an integer between 0 and 255");
    }
  }

  public void validateArguments() {
    if (!callParserCommand.contains("%robots%")
        || !callParserCommand.contains("%url%")
        || !callParserCommand.contains("%user-agent%")) {
      throw new IllegalArgumentException(
          "One or more of the parameter variables {%robots% %url% %user-agent%} are missing.");
    }

    if (myTestsDir != null && !(new File(myTestsDir).isDirectory())) {
      throw new IllegalArgumentException("The directory \'" + myTestsDir + "\' does not exist!");
    }

    if (mode == OutputType.EXITCODE) {
      validateAsExitcode(allowedPattern, "allowedPattern");
      validateAsExitcode(disallowedPattern, "disallowedPattern");
    }

    if (allowedPattern.equals(disallowedPattern)) {
      throw new IllegalArgumentException(
          "The allowed and disallowed patterns must not be the same.");
    }
  }

  /**
   * Get the specific command that will call the parser, in a specific test case
   *
   * @param robotsTxtPath The path to the robots.txt file
   * @param url The URL
   * @param userAgent The user-agent
   * @return The command
   */
  public String getCommand(String robotsTxtPath, String url, String userAgent) {
    String command = this.callParserCommand;
    command = command.replace("%robots%", robotsTxtPath);
    command = command.replace("%url%", url);
    command = command.replace("%user-agent%", userAgent);
    return command;
  }
}
