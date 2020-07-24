package com.google.search.robotstxt.spec;

/** Models a Pair type (key, value) */
public class Pair<K, V> {
  private K key;
  private V value;

  /** Default constructor */
  public Pair() {}

  /**
   * Constructor with parameters
   *
   * @param key The key
   * @param value The value
   */
  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }
}
