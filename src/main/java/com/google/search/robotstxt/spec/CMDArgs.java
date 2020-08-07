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

  public boolean invalidArguments() {
    if (callParserCommand.contains("%robots%") == false
        || callParserCommand.contains("%url%") == false
        || callParserCommand.contains("%user-agent%") == false) {
      System.out.println("[INVALID ARGUMENTS]");
      System.out.println(
          "One or more of the parameter variables {%robots% %url% %user-agent%} are missing.");
      System.out.println(
          "For further details on how to run the testing tool, please check the documentation!");
      return true;
    }
    if (myTestsDir != null && new File(myTestsDir).isDirectory() == false) {
      System.out.println("[INVALID ARGUMENTS]");
      System.out.println("The directory " + myTestsDir + " does not exist!");
      return true;
    }
    if (mode == OutputType.EXITCODE) {
      try {
        int exitPattern = Integer.parseInt(allowedPattern);
        exitPattern = Integer.parseInt(disallowedPattern);
      } catch (NumberFormatException numberFormatException) {
        System.out.println("[INVALID ARGUMENTS]");
        System.out.println("The exit code pattern must be an integer between 0 and 255.");
        return true;
      }

      if (Integer.parseInt(allowedPattern) < 0 || Integer.parseInt(disallowedPattern) < 0) {
        System.out.println("[INVALID ARGUMENTS]");
        System.out.println("The exit code pattern must be an integer between 0 and 255.");
        return true;
      }
    }
    if (allowedPattern.equals(disallowedPattern)) {
      System.out.println("[INVALID ARGUMENTS]");
      System.out.println("The allowed and disallowed patterns must not be the same.");
      return true;
    }
    return false;
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
