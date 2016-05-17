package com.reuter.traffic.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by aandra1 on 16/05/16.
 */
@RunWith(JUnit4.class)
public class VehicleTest {

  @Test
  public void calculateDirectionWithError() {
    LocalDateTime base = LocalDateTime.now();

    Registry reg01 = new Registry(base, base.plus(140L, ChronoUnit.MILLIS));
    Registry reg02 = new Registry(base.plus(160L, ChronoUnit.MILLIS), base.plus(240L, ChronoUnit.MILLIS));
    Vehicle vehicle = new Vehicle(reg01, reg02);

    Assert.assertNotEquals(Direction.SOUTH, vehicle.direction());
  }

  @Test
  public void calculateDirection() {
    LocalDateTime base = LocalDateTime.now();

    Registry reg01 = new Registry(base, base.plus(140L, ChronoUnit.MILLIS));
    Registry reg02 = new Registry(Hose.B, base.plus(160L, ChronoUnit.MILLIS), base.plus(240L, ChronoUnit.MILLIS));
    Vehicle vehicle = new Vehicle(reg01, reg02);

    Assert.assertEquals(Direction.SOUTH, vehicle.direction());
  }

  @Test
  public void calculatePeriod() {
    LocalDateTime morning = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0, 0));
    Registry regMorning = new Registry(morning, morning.plus(160L, ChronoUnit.MILLIS));
    Vehicle vehicle01 = new Vehicle(regMorning);

    LocalDateTime evening = LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0, 0));
    Registry regEvening = new Registry(evening, evening.plus(160L, ChronoUnit.MILLIS));
    Vehicle vehicle02 = new Vehicle(regEvening);

    Assert.assertEquals(Period.MORNING, vehicle01.period());
    Assert.assertEquals(Period.EVENING, vehicle02.period());
  }

  @Test
  public void calculateSpeed() {
    LocalDateTime startAt = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0, 0));
    Registry registry = new Registry(startAt, startAt.plus(140L, ChronoUnit.MILLIS));
    Vehicle vehicle = new Vehicle(registry);

    Assert.assertEquals(new Double(64.28571428571428D), vehicle.speed());
  }
}
