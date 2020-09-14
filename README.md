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
  -Dexec.args="--command='<run_parser_command>' [--userTestDir=<user_tests_directory>] [--outputType=<output_type>] [--allowedPattern=<regular_expr>] [--disallowedPattern=<regular_expr>]"
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

Also, the parser must exit with an exit code or must print at Standard Output a specific message for the outcome of the test.


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
