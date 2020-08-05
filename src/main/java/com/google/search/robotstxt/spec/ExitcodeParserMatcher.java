package com.google.search.robotstxt.spec;

import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** Handles a parser that outputs its outcome by exiting with a specific code */
public class ExitcodeParserMatcher implements ParserMatcher {
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

    // Convert the exitCode to a String because this is how it's represented in cmdArgs
    String exitCode = Integer.toString(process.exitValue());

    // Test the exit code
    if (exitCode.equals(cmdArgs.getAllowedPattern())) {
      return SpecificationProtos.Outcome.ALLOWED;
    } else if (exitCode.equals(cmdArgs.getDisallowedPattern())) {
      return SpecificationProtos.Outcome.DISALLOWED;
    }
    return SpecificationProtos.Outcome.UNSPECIFIED;
  }
}
