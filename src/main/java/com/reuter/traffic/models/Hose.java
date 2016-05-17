package com.reuter.traffic.models;

/**
 * Created by aandra1 on 14/05/16.
 */
public enum Hose {
  A, B;

  public static Hose eval(char hose) {
    if (Character.toUpperCase(hose) == 'A')
      return A;
    else
      return B;
  }
}
