# Robots.txt Specification Test

This project aims to allow for any implementation of a robots.txt parser to be
tested and have some confidence that it adheres to the standard, as specified by
the [internet draft](https://tools.ietf.org/html/draft-koster-rep).

## Background

In 2019 the Search Open Sourcing team open sourced [robots.txt parser and matcher](https://github.com/google/robotstxt). 
The open source code is in C++. Additionally, there is an [implementation of the 
parser in Java](https://github.com/google/robotstxt-java).

There are already Go and Rust implementations of the C++ parser in the open source community.

At the moment there is no standard way to check whether different ports of the parser 
comply with the standard. The project is a testing tool that can be run 
against implementations in different languages and can validate that they comply 
with the standard.

## Development

### Prerequisites

You need Maven to build this project.
[Download](https://maven.apache.org/download.html) and
[install](https://maven.apache.org/install.html) it from the official website.

You can also install it like this if your Linux supports it:

```
$ sudo apt-get install maven
```

You will also need to have the
[protocol buffer](https://developers.google.com/protocol-buffers/) compiler
([protoc](https://github.com/protocolbuffers/protobuf)) installed.
Get [the latest](https://github.com/protocolbuffers/protobuf/releases/latest)
for your platform (i.e. `protoc-x.x.x-win64.zip`, `protoc-x.x.x-osx-x86_64.zip`).

You can also install it like this if your Linux supports it:

```
$ sudo apt-get install protobuf-compiler
```


### Build it

Standard maven commands work here.

```
$ mvn install
```

Or if you want a build from scratch:

```
$ mvn clean install
```

### Run it

```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='<run_parser_command>' \
  [--userTestDir=<user_tests_directory>] [--outputType=<output_type>] \
  [--allowedPattern=<regular_expr>] [--disallowedPattern=<regular_expr>]"
```

### Usage

The command line arguments that are used by the testing framework must be specified for the flag `-Dexec.args="<args>"`, according to these usage specifications:
```
    --command=<callParserCommand>
    [--allowedPattern=<allowedPattern>]
    [--disallowedPattern=<disallowedPattern>]
    [--outputType=<outputType>] [--userTestDir=<myTestsDir>]
      
      --command=<callParserCommand>
         The command that runs the parser
      --outputType=<outputType>
         The format that the parser uses (either EXITCODE or PRINTING). Default value: EXITCODE
      --allowedPattern=<allowedPattern>
         The pattern used for -allowed-. Default value: 0
      --disallowedPattern=<disallowedPattern>
         The pattern used for -disallowed-. Default value: 1
      --userTestDir=<myTestsDir>
         The path to the directory that contains the user's test files. Default value: null
```
The parser must receive as arguments: the robots.txt file, the URL, the user-agent. The actual place inside the command that calls the parser will be specified by using the variables `%robots%`, `%url%`, `%user-agent%`. 

The format for the `--command` flag should be: `<path/to/your/parser> [...] %robots% [...] %url% [...] %user-agent%`.

An example of such command could be: `./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%`. 

An example for a whole command that calls the testing tool could be:
```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%' \
  --userTestDir=~/Desktop/myTests --outputType=PRINTING \
  --allowedPattern='\bALLOW\b' --disallowedPattern='\bDISALLOW\b'"
```

Also, the parser must exit with an exit code or must print at Standard Output a specific message for the outcome of the test.


If you want to see some examples of calling commands for a better understanding on how the testing tool can be configured, please check [COMMAND-EXAMPLES.md](https://github.com/google/robotstxt-spec-test/blob/master/COMMAND-EXAMPLES.md).

### Custom Tests

If you want to test your parser against your own tests, you can totally do that! :) 

You just need to provide the directory of your tests (`--userTestDir=<path-to-your-folder>`)

Here are some guidelines for creating your own custome tests to run your parser against them.

The test cases are written in plain text, following a specific structure ([the Protobuf Text Format](https://medium.com/@nathantnorth/protocol-buffers-text-format-14e0584f70a5)), in order to be human readable, but also easily parsable by the computer. 

The files **must** have a list with tests that contains:
- contents of the robot.txt file (`String`)
- test situations that contains: 
  * the URL to be tested (`String`)
  * the user-agent (`String`)
  * the expected outcome of the test (`ALLOWED` or `DISALLOWED`) 
  * the additional explanation of the desired outcome (`String`)
  
The test files **must** have the `.textproto` extension. (For example: `my_test_file.textproto`)

The format should be like this: 
```
tests:
[
    {
        robotstxt: "<robot.txt_file_content>"
        test_situations: 
        [ 
            { 
                testurl: "<URL_1>"
                useragent: "<user-agent_1>"
                expected_outcome: "<expected_outcome_1>"
                additional_explanation: "<explanation_1>"
            }
            { 
                testurl: "<URL_2>"
                useragent: "<user-agent_2>"
                expected_outcome: "<expected_outcome_2>"
                additional_explanation: "<explanation_2>"
            }
            [...]
        ]
        [...]
    }
]
```
If you want to understand better how to structure your test cases, take a look at our test files: [`{Specification_Test_Dir}/src/main/resources/CTC/correctness`](https://github.com/google/robotstxt-spec-test/tree/master/src/main/resources/CTC/correctness)


### Output

The Specification Test provides the following information about the compliance of the provided parser:

1. List of performed tests (with test progress information)
  - List of passed tests
  - List of failed tests 
    * Test file path (for better debugging of special characters)
    * The robots.txt content
    * The URL
    * The user-agent
    * The expected outcome
    * The additional explanation
    * The user's outcome (as how it's interpreted by the testing tool)
    * The user's output (Exitcode, STDOUT, STDERR)
2. Test Results
  - Metrics
    * Number of Compliance Tests (performed and failed)
    * Number of Google-specific Tests (performed and failed)
    * Number of User's Tests (performed and failed)
  - The compliance (`The parser FOLLOWS the standard and adheres to Google's specifications`, `The parser FOLLOWS the standard but do not adhere to Google's specifications`, `The parser DOES NOT FOLLOW the standard`)

## License

The robots.txt parser and matcher C++ library is licensed under the terms of the Apache license. See LICENSE for more information.

## Links

To learn more about this project:

- check out the [internet draft](https://tools.ietf.org/html/draft-koster-rep-02),
- how [Google's handling robots.txt](https://developers.google.com/search/reference/robots_txt),
- or for a high level overview, [the robots.txt page on Wikipedia](https://en.wikipedia.org/wiki/Robots_exclusion_standard).

Google's Robots.txt Parser/Matcher:

- [C++ Implementation](https://github.com/google/robotstxt)
- [Java Implementation](https://github.com/google/robotstxt-java)

## Source Code Headers

Every file containing source code must include copyright and license
information. This includes any JS/CSS files that you might be serving out to
browsers. (This is to help well-intentioned people avoid accidental copying that
doesn't comply with the license.)

Apache header:

    Copyright 2020 Google LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

It can be done easily by using the
[addlicense](https://github.com/google/addlicense) tool

Install it:

```
$ go get -u github.com/google/addlicense
```

Use it like this to make sure all files have the licence:

```
$ ~/go/bin/addlicense -c "Google LLC" -l apache .
```

