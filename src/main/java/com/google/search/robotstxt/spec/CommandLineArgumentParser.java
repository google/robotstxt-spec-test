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

import picocli.CommandLine;

/** Handle Command Line arguments */
@CommandLine.Command(name = "testRunner")
public class CommandLineArgumentParser {
  @CommandLine.Option(
      names = "--command",
      required = true,
      description = "The command that runs the parser")
  public String callParserCommand;

  @CommandLine.Option(
      names = "--testDir",
      description = "The path to the directory that contains the Compliance Test Files")
  public String complianceTestsDir = "CTC";

  @CommandLine.Option(
      names = "--userTestDir",
      description = "The path to the directory that contains the user's test files")
  public String myTestsDir = null;

  @CommandLine.Option(names = "--outputType", description = "The format that the parser uses")
  public OutputType outputType = OutputType.EXITCODE;

  @CommandLine.Option(names = "--allowedPattern", description = "The pattern used for -allowed-")
  public String allowedPattern = "0";

  @CommandLine.Option(
      names = "--disallowedPattern",
      description = "The pattern used for -disallowed-")
  public String disallowedPattern = "1";

  public CMDArgs createCMDArgs() {

    CMDArgs cmdArgs =
        new CMDArgs(
            callParserCommand,
            complianceTestsDir,
            myTestsDir,
            outputType,
            allowedPattern,
            disallowedPattern);
    return cmdArgs;
  }
}
