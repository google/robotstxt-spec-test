package com.google.search.robotstxt;

import com.google.search.robotstxt.example.ExampleProtos.Example;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    Example aProto = Example.newBuilder().setSomeField("Helo World!").addSomeRepeated(42).build();

    System.out.println("My proto:\n" + aProto);
  }
}
