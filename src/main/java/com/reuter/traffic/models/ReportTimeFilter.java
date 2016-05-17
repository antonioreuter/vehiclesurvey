package com.reuter.traffic.models;

/**
 * Created by aandra1 on 15/05/16.
 */
public enum ReportTimeFilter {
  FIFTEEN(15),
  TWENTY(20),
  THIRTY(30),
  SIXTY(60);

  private int minutes;

  private ReportTimeFilter(int min) {
    this.minutes = min;
  }

  public int minutes() {
    return this.minutes;
  }
}
