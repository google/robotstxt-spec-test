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
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;

/** Class for parsing Protocol Buffer test cases */
public class ProtoParser {

  /**
   * Creates TestInfo objects (from the Protocol Buffer Objects) and adds them to a list
   *
   * @param robotsTxtSpec The Protocol Buffer Object
   * @param testCases The list
   */
  private void addTestInfoObjects(
      SpecificationProtos.RobotsTxtSpecification robotsTxtSpec,
      String path,
      List<TestInfo> testCases) {
    for (SpecificationProtos.RobotsTxtTest robotsTxtTest : robotsTxtSpec.getTestsList()) {
      byte[] robotsTxtContent = new byte[robotsTxtTest.getRobotstxt().size()];
      robotsTxtTest.getRobotstxt().copyTo(robotsTxtContent, 0);

      for (SpecificationProtos.Expectation expectation : robotsTxtTest.getTestExpectationsList()) {
        TestInfo testInfo = new TestInfo(robotsTxtContent, path, expectation);
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
    File dir;
    if (dirPath.equals("/CTC")) {
      dir = new File(getClass().getResource("/CTC").getFile());
    } else {
      dir = new File(dirPath);
    }

    Iterator<File> allFiles = FileUtils.iterateFiles(dir, null, true);
    List<TestInfo> testCases = new ArrayList<>();
    while (allFiles.hasNext()) {
      File testFile = allFiles.next();

      // Ignore the files that don't have the .textproto extension
      if (!testFile.getName().contains(".textproto")) {
        continue;
      }
      String text = new String(Files.readAllBytes(testFile.toPath()), StandardCharsets.UTF_8);

      // Parse the proto content
      SpecificationProtos.RobotsTxtSpecification.Builder builder =
          SpecificationProtos.RobotsTxtSpecification.newBuilder();
      TextFormat.merge(text, builder);
      SpecificationProtos.RobotsTxtSpecification robotsTxtSpec = builder.build();

      addTestInfoObjects(robotsTxtSpec, testFile.toString(), testCases);
    }

    return testCases;
  }
}
