package com.google.search.robotstxt.spec;

import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Handles a parser that outputs its outcome by printing at stdout */
public class PrintingParserMatcher implements ParserMatcher {
  @Override
  public SpecificationProtos.Outcome getOutcome(
      String robotsTxtContent, String url, String userAgent, CMDArgs cmdArgs)
      throws IOException, InterruptedException {
    // Create temporary file for the robots.txt content and pass the path as argument
    File dir = new File("./src/main/resources/Temp");
    File robotsTxtPath = File.createTempFile("robots_", ".tmp", dir);

    FileWriter writer = new FileWriter(robotsTxtPath);
    writer.write(robotsTxtContent);
    writer.close();

    // Run the parser
    String command =
        cmdArgs.getCommand(robotsTxtPath.getAbsolutePath(), url, "\"" + userAgent + "\"");
    Process process = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", command});
    process.waitFor();

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line = "";

    Pattern allowedPattern = Pattern.compile(cmdArgs.getAllowedPattern());
    Pattern disallowedPattern = Pattern.compile(cmdArgs.getDisallowedPattern());

    // Pattern-matching for the regular expression
    while ((line = reader.readLine()) != null) {
      Matcher allowedMatcher = allowedPattern.matcher(line);
      Matcher disallowedMatcher = disallowedPattern.matcher(line);

      // Test the outcome
      if (disallowedMatcher.find()) {
        return SpecificationProtos.Outcome.DISALLOWED;
      } else if (allowedMatcher.find()) {
        return SpecificationProtos.Outcome.ALLOWED;
      }
    }
    return SpecificationProtos.Outcome.UNSPECIFIED;
  }
}
