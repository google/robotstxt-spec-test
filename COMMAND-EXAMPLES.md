# Command Examples

This file contains some examples that you can use for a better understanding on how the [Robots.txt Specification Test](https://github.com/google/robotstxt-spec-test) can be used and configured for your needs. 

Please, note that these are real-life examples of usage, so you may need to adapt them. These are **not** templates for the command. Please, make sure that you have read the [usage guidlines](https://github.com/google/robotstxt-spec-test#run-it) of the command before you take a look at these examples. 

### Example #1

```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%'"
```

This is the most basic command. 
The parser is called with the template `./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%`. For a specific test case, the testing tool will call the parser with some specific parameters: `./my_parser --url https://example.com/page --user-agent 'Foo' --robotsPath=~/Desktop/robots.txt`. 

Since other parameters are not specified, it mean they will have the default value, so the parser exists with an exit code, that it's `0` for Allowed and `1` for Disallowed. 

### Example #2

```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='./my_parser %robots% --fooArg=foo %url% %user-agent%' \
  --outputType=EXITCODE --allowedPattern=2 --disallowedPattern=3"
```

The parser is called with the template `./my_parser %robots% --fooArg=foo %url% %user-agent%`. For a specific test case, the testing tool will call the parser with some specific parameters: `./my_parser ~/Desktop/robots.txt --fooArg=foo https://example.com/page 'Foo'`. 

The parser exists with an exit code, that it's `2` for Allowed and `3` for Disallowed. 

### Example #3

```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%' \
  --outputType=PRINTING --allowedPattern='The outcome: ALLOWED' --disallowedPattern='The outcome: DISALLOWED'"
```

The parser is called with the template `./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%`. 

The parser prints the outcome at `STDOUT`. It will print `The outcome: ALLOWED` for Allowed and `The outcome: DISALLOWED` for Disallowed. 

### Example #4

```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%' \
  --outputType=PRINTING --allowedPattern='\bALLOW\b' --disallowedPattern='\bDISALLOW\b'"
```

The parser is called with the template `./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%`. 

The parser prints the outcome at `STDOUT`. It will print `[some text...] ALLOW [some text...]` for Allowed and `[some text...] DISALLOW [some text...]` for Disallowed. As you can notice, here we use regular expressions (`\bALLOW\b`) to announce the testing tool that if it finds the word `ALLOW` at Standard Output, it means that the outcome of the parser is Allowed. Also, if it finds the word `DISALLOW` at Standard Output, it means that the outcome of the parser is Disallowed.

We encourage you to use regular expressions when providing the allowed and disallowed pattern to the Robots.txt Specification Test because it's a safer and faultless way to make sure that our testing tool interprets the output of the parser in a correct way. 

### Example #5

```
$ mvn exec:java -Dexec.mainClass="com.google.search.robotstxt.spec.Main" \
  -Dexec.args="--command='./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%' \
  --outputType=PRINTING --allowedPattern='The outcome: ALLOWED' --disallowedPattern='The outcome: DISALLOWED' \
  --userTestDir=~/Desktop/myTests"
```

The parser is called with the template `./my_parser --url %url% --user-agent %user-agent% --robotsPath=%robots%`. 

The parser prints the outcome at `STDOUT`. It will print `The outcome: ALLOWED` for Allowed and `The outcome: DISALLOWED` for Disallowed. 

Also, the user wants to test the parser against their own test cases, so they provide the path to the folder of these test files (`~/Desktop/myTests`). If you want to find out more on how to create your own test cases, please refer to [this section]() of the README.md file. 
