package com.reuter.traffic.models;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by aandra1 on 14/05/16.
 */
public class Registry implements Comparable<Registry> {

  private Hose hose = Hose.A;
  private LocalDateTime startAt;
  private LocalDateTime endAt;

  public Registry(LocalDateTime startAt, LocalDateTime endAt) {
    if (startAt.isAfter(endAt))
      throw new IllegalArgumentException("The start date is after the end date");

    this.startAt = startAt;
    this.endAt = endAt;
  }

  public Registry(Hose hose, LocalDateTime startAt, LocalDateTime endAt) {
    this(startAt, endAt);
    this.hose = hose;
  }

  public Hose getHose() {
    return this.hose;
  }

  public LocalDateTime getStartAt() {
    return this.startAt;
  }

  public Period period() {
    return Period.eval(this.startAt);
  }

  public Long diffTimeInMilliseconds() {
    return Duration.between(this.startAt, this.endAt).toMillis();
  }

  public String toString() {
    return String.format("%s: %s - %s", this.hose, this.startAt, this.endAt);
  }

  @Override
  public int compareTo(Registry target) {
    return this.startAt.compareTo(target.startAt);
  }
}
