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

import com.google.search.robotstxt.spec.specification.SpecificationProtos;

public interface ParserMatcher {
  // Method to get the outcome of a parser for a specific (robots.txt - URL - user-agent) case
  // Used byte[] instead of String or the robots.txt content because sometimes, in these files
  // can appear BOM characters (or broken BOM characters) that are altered in a regular Java String
  public SpecificationProtos.Outcome getOutcome(
      byte[] robotsTxtContent, String url, String userAgent, CMDArgs cmdArgs) throws Exception;
}
