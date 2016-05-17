package com.reuter.traffic.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aandra1 on 14/05/16.
 */
public class Vehicle {
  private static final double DISTANCE_BETWEEN_AXLES = 2.5;

  private Double distanceBetweenAxles = DISTANCE_BETWEEN_AXLES;
  private Direction direction = Direction.NORTH;
  private List<Registry> registries;

  public Vehicle(List<Registry> registries) {
    this.registries = registries;

    boolean goingToSouth = this.registries.stream().anyMatch(r -> r.getHose() == Hose.B);
    if (goingToSouth) {
      this.direction = Direction.SOUTH;
    }
  }

  public Vehicle(Registry... registries) {
    this(Arrays.asList(registries));
  }

  /**
   * Informs the direction. NORTH or SOUTH.
   *
   * @return
   */
  public Direction direction() {
    return this.direction;
  }

  /**
   * Informs the period. MORNING or EVENING.
   *
   * @return
   */
  public Period period() {
    return this.registries.get(0).period();
  }

  public LocalDate getDate() {
    return startAt().toLocalDate();
  }

  public int getHour() {
    return startAt().getHour();
  }

  public LocalDateTime startAt() {
    return registries.get(0).getStartAt();
  }

  public Registry startAtRegistry() {
    return registries.get(0);
  }

  /**
   * Informs the car speed at the moment when it passed over the hose.
   *
   * @return
   */
  public Double speed() {
    double time = registries.get(0).diffTimeInMilliseconds() / 1000D;
    double speedMetersPerSecond = this.distanceBetweenAxles / time;
    Double speedKmPerHour = new Double(speedMetersPerSecond * 3.6D);

    return speedKmPerHour;
  }
}
