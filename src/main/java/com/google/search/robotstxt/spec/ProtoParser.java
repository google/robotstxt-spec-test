package com.google.search.robotstxt.spec;

import com.google.search.robotstxt.spec.testfile.TestFileProtos;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/** Class for parsing Protocol Buffer test cases */
public class ProtoParser {

  /**
   * Creates TestInfo objects (from the Protocol Buffer Objects) and adds them to a list
   *
   * @param robotsTxtSpec The Protocol Buffer Object
   * @param testCases The list
   */
  private void addTestInfoObjects(
      TestFileProtos.RobotsTxtSpecification robotsTxtSpec, ArrayList<TestInfo> testCases) {
    for (TestFileProtos.RobotsTxtTest robotsTxtTest : robotsTxtSpec.getTestsList()) {
      String robotsTxtContent = robotsTxtTest.getRobotstxt();

      for (TestFileProtos.Expectation expectation : robotsTxtTest.getTestSituationsList()) {
        TestInfo testInfo =
            new TestInfo(
                robotsTxtContent,
                expectation.getTesturl(),
                expectation.getUseragent(),
                expectation.getExpectedOutcome(),
                expectation.getAdditionalExplanation());
        testCases.add(testInfo);
      }
    }
  }

  /**
   * Creates a list of TestInfo objects based on multiple files
   *
   * @param dirPath The path to the directory containing the files
   * @return The list of TestInfo objects
   */
  public ArrayList<TestInfo> readMessages(String dirPath)
      throws java.io.FileNotFoundException, java.io.IOException {

    File dir = new File(dirPath);
    File[] allFiles = dir.listFiles();
    ArrayList<TestInfo> testCases = new ArrayList<>();
    for (File testFile : allFiles) {
      TestFileProtos.RobotsTxtSpecification robotsTxtSpec =
          TestFileProtos.RobotsTxtSpecification.parseFrom(new FileInputStream(testFile));
      addTestInfoObjects(robotsTxtSpec, testCases);
    }

    return testCases;
  }
}
