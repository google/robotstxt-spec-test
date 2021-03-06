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

import com.google.common.io.Files;
import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.File;

/** Handles a parser that outputs its outcome by exiting with a specific code */
public class ExitcodeParserMatcher implements ParserMatcher {
  @Override
  public TestOutcome getOutcome(
      byte[] robotsTxtContent, String url, String userAgent, CMDArgs cmdArgs) throws Exception {
    // Create temporary file for the robots.txt content and pass the path as argument
    File robotsTxtPath = File.createTempFile("robots_", ".tmp.txt");
    Files.asByteSink(robotsTxtPath).write(robotsTxtContent);

    // Run the parser
    Process process = cmdArgs.runParser(robotsTxtPath, url, userAgent);
    process.waitFor();

    TestOutcome.Builder testOutcome =
        TestOutcome.builder()
            .setStdOut(CMDArgs.outputToString(process.getInputStream()))
            .setStdErr(CMDArgs.outputToString(process.getErrorStream()))
            .setExitCode(process.exitValue())
            .setOutcome(SpecificationProtos.Outcome.UNSPECIFIED);

    // Convert the exitCode to a String because this is how it's represented in cmdArgs
    String exitCode = Integer.toString(process.exitValue());

    // Test the exit code
    if (exitCode.equals(cmdArgs.getAllowedPattern())) {
      testOutcome.setOutcome(SpecificationProtos.Outcome.ALLOWED);
    } else if (exitCode.equals(cmdArgs.getDisallowedPattern())) {
      testOutcome.setOutcome(SpecificationProtos.Outcome.DISALLOWED);
    }

    return testOutcome.build();
  }
}
