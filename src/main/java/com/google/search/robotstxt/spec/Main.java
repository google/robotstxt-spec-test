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

import java.io.IOException;
import java.util.List;
import picocli.CommandLine;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    // Here I should parse the command line arguments, as soon as I figure out how :)
    CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();
    int exitCode = new CommandLine(commandLineArgumentParser).execute(args);
    CMDArgs cmdArgs = commandLineArgumentParser.getCmdArgs();

    ParserMatcher parserMatcher;
    if (cmdArgs.getMode() == OutputType.PRINTING) {
      parserMatcher = new PrintingParserMatcher();
    } else {
      parserMatcher = new ExitcodeParserMatcher();
    }
    ProtoParser protoParser = new ProtoParser();
    TestsResult result = new TestsResult();
    List<TestInfo> testCases;
    TestRunner testRunner;

    testCases = protoParser.readMessages(cmdArgs.getComplianceTestsDir());
    testRunner = new ComplianceTestRunner();
    testRunner.runTests(testCases, parserMatcher, cmdArgs, result);

    if (cmdArgs.getMyTestsDir() != null) {
      testCases = protoParser.readMessages(cmdArgs.getMyTestsDir());
      testRunner = new UserTestRunner();
      testRunner.runTests(testCases, parserMatcher, cmdArgs, result);
    }

    System.out.println(result);
  }
}
