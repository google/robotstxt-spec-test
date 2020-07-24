package com.google.search.robotstxt.spec;

/** Flags setup by Command Line Options */
public class CMDArgs {
  private String callParserCommand;
  private String complianceTestsDir;
  private String myTestsDir;
  private OutputType mode;
  private String allowedPattern;
  private String disallowedPattern;
  private String undefinedPattern;

  /** Default constructor */
  public CMDArgs() {}

  /**
   * Constructor with parameters
   *
   * @param callParserCommand The command that calls the parser
   * @param complianceTestsDir The directory of the Compliance Tests
   * @param myTestsDir The directory of the user's tests
   * @param mode The output mode
   * @param allowedPattern The allowed pattern (regular expression)
   * @param disallowedPattern The disallowed pattern (regular expression)
   * @param undefinedPattern The undefined pattern (regular expression)
   */
  public CMDArgs(
      String callParserCommand,
      String complianceTestsDir,
      String myTestsDir,
      OutputType mode,
      String allowedPattern,
      String disallowedPattern,
      String undefinedPattern) {
    this.callParserCommand = callParserCommand;

    if (complianceTestsDir == null) {
      this.complianceTestsDir =
          "../../../../../../../"; // We should find another way to access the "home directory" of
                                   // the app
    } else {
      this.complianceTestsDir = complianceTestsDir;
    }

    this.myTestsDir = myTestsDir;

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

    if (undefinedPattern == null) {
      this.undefinedPattern = "2";
    } else {
      this.undefinedPattern = undefinedPattern;
    }
  }

  public String getCallParserCommand() {
    return callParserCommand;
  }

  public String getComplianceTestsDir() {
    return complianceTestsDir;
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

  public String getUndefinedPattern() {
    return undefinedPattern;
  }
}
