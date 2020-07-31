package com.google.search.robotstxt.spec;

import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/** Handles a parser that outputs its outcome by printing at stdout */
public class PrintingParserMatcher implements ParserMatcher {
  @Override
  public SpecificationProtos.Outcome getOutcome(
      String robotsTxtContent, String url, String userAgent, CMDArgs cmdArgs) throws IOException {
    Path robotsTxtPath = Files.createTempFile(null, null, null);

    Process process =
        Runtime.getRuntime().exec(cmdArgs.getCommand(robotsTxtPath.toString(), url, userAgent));

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line = "";
    while ((line = reader.readLine()) != null) {
      if (line.contains(cmdArgs.getAllowedPattern())) {
        return SpecificationProtos.Outcome.ALLOWED;
      } else if (line.contains(cmdArgs.getDisallowedPattern())) {
        return SpecificationProtos.Outcome.DISALLOWED;
      }
    }
    return SpecificationProtos.Outcome.UNSPECIFIED;
  }
}
