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

  @CommandLine.Option(
      names = "--printing",
      description = "The printing format that the parser uses")
  public String[] printingPatterns = {};

  @CommandLine.Option(
      names = "--exitcode",
      description = "The exit code format that the parser uses")
  public String[] exitcodePatterns = {"0=allowed", "1=disallowed"};
}
