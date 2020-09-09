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

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import org.apache.maven.shared.utils.cli.CommandLineUtils;

/** Flags setup by Command Line Options */
public class CMDArgs {
  private String callParserCommand;
  private String myTestsDir;
  private OutputType mode;
  private String allowedPattern;
  private String disallowedPattern;

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
    this.myTestsDir = myTestsDir;

    // "~/" will be interpreted as the home directory of the user
    this.callParserCommand =
        this.callParserCommand.replaceFirst(
            "^~/", Matcher.quoteReplacement(System.getProperty("user.home") + "/"));
    if (this.myTestsDir != null) {
      this.myTestsDir =
          this.myTestsDir.replaceFirst(
              "^~/", Matcher.quoteReplacement(System.getProperty("user.home") + "/"));
    }

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

  /**
   * Check if the exit code provided by the user is valid
   *
   * @param value The value of the exit code
   * @param flagname The name of the parameter (allowed / disallowed)
   */
  private void validateAsExitcode(String value, String flagname) {
    int exitCode;
    try {
      exitCode = Integer.parseInt(value);
    } catch (NumberFormatException numberFormatException) {
      throw new IllegalArgumentException(
          flagname + ": The exit code number must be an integer between 0 and 255");
    }

    if (exitCode < 0 || exitCode > 255) {
      throw new IllegalArgumentException(
          flagname + ": The exit code number must be an integer between 0 and 255");
    }
  }

  /** Check if the arguments provided by the user are valid */
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
  private String[] getCommand(String robotsTxtPath, String url, String userAgent) throws Exception {
    String command = this.callParserCommand;
    String[] args = CommandLineUtils.translateCommandline(command);
    for (int i = 0; i < args.length; i++) {
      args[i] = args[i].replace("%robots%", robotsTxtPath);
      args[i] = args[i].replace("%url%", url);
      args[i] = args[i].replace("%user-agent%", userAgent);
    }

    return args;
  }

  /**
   * Run the parser against a specific test case
   *
   * @param robotsTxtPath The path to the robots.txt file
   * @param url The URL
   * @param userAgent The user-agent
   * @return The process created by running the parser
   * @throws Exception
   */
  public Process runParser(File robotsTxtPath, String url, String userAgent) throws Exception {
    String[] command = this.getCommand(robotsTxtPath.getAbsolutePath(), url, userAgent);
    Process process = Runtime.getRuntime().exec(command);

    return process;
  }

  /**
   * Converts the InputStream into a String, interpreting as UTF-8 string and closes it.
   *
   * <p>The method is general purpose, but it is intended to be used with the stdout or stderr from
   * a process.
   */
  public static String outputToString(InputStream processOutput) throws IOException {
    try (InputStreamReader streamReader = new InputStreamReader(processOutput, Charsets.UTF_8)) {
      return CharStreams.toString(streamReader);
    }
  }
}
