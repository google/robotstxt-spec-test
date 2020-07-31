package com.google.search.robotstxt.spec;

import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Handles a parser that outputs its outcome by exiting with a specific code */
public class ExitcodeParserMatcher implements ParserMatcher {
  @Override
  public SpecificationProtos.Outcome getOutcome(
      String robotsTxtContent, String url, String userAgent, CMDArgs cmdArgs) throws IOException {
    Path robotsTxtPath = Files.createTempFile(null, null, null);

    Process process =
        Runtime.getRuntime().exec(cmdArgs.getCommand(robotsTxtPath.toString(), url, userAgent));

    int exitCode = process.exitValue();
    if (Integer.toString(exitCode).equals(cmdArgs.getAllowedPattern())) {
      return SpecificationProtos.Outcome.ALLOWED;
    } else if (Integer.toString(exitCode).equals(cmdArgs.getDisallowedPattern())) {
      return SpecificationProtos.Outcome.DISALLOWED;
    }
    return SpecificationProtos.Outcome.UNSPECIFIED;
  }
}
