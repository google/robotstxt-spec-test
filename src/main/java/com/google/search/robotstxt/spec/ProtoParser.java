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

import com.google.protobuf.TextFormat;
import com.google.search.robotstxt.spec.specification.SpecificationProtos;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/** Class for parsing Protocol Buffer test cases */
public class ProtoParser {

  /**
   * Creates TestInfo objects (from the Protocol Buffer Objects) and adds them to a list
   *
   * @param robotsTxtSpec The Protocol Buffer Object
   * @param testCases The list
   */
  private void addTestInfoObjects(
      SpecificationProtos.RobotsTxtSpecification robotsTxtSpec, List<TestInfo> testCases) {
    for (SpecificationProtos.RobotsTxtTest robotsTxtTest : robotsTxtSpec.getTestsList()) {
      String robotsTxtContent = robotsTxtTest.getRobotstxt();

      for (SpecificationProtos.Expectation expectation : robotsTxtTest.getTestExpectationsList()) {
        TestInfo testInfo = new TestInfo(robotsTxtContent, expectation);
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
  public List<TestInfo> readMessages(String dirPath)
      throws java.io.FileNotFoundException, java.io.IOException {

    // URL url = getClass().getResource("/CTC");
    File dir = new File(getClass().getResource("/CTC").getFile());
    File[] allFiles = dir.listFiles();
    List<TestInfo> testCases = new ArrayList<>();
    for (File testFile : allFiles) {
      String text = new String(Files.readAllBytes(testFile.toPath()), StandardCharsets.UTF_8);

      // Parse the proto content
      SpecificationProtos.RobotsTxtSpecification.Builder builder =
          SpecificationProtos.RobotsTxtSpecification.newBuilder();
      TextFormat.merge(text, builder);
      SpecificationProtos.RobotsTxtSpecification robotsTxtSpec = builder.build();

      addTestInfoObjects(robotsTxtSpec, testCases);
    }

    return testCases;
  }
}
