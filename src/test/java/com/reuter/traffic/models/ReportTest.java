package com.reuter.traffic.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aandra1 on 16/05/16.
 */
@RunWith(JUnit4.class)
public class ReportTest {

  @Test
  public void computeTotalVehiclesByDirectionWithNoSpecificDate() {
    Report subject = new Report(dataSource());
    Map<LocalDate, Map<LocalDateTime, Map<Direction, Long>>> statistics = subject.totalVehiclesByDirection(ReportTimeFilter.SIXTY, null);

    LocalDate key1 = LocalDate.of(2016, 05, 15);
    LocalDateTime subKey11 = LocalDateTime.of(key1, LocalTime.of(0, 0));
    LocalDate key2 = LocalDate.of(2016, 05, 16);
    LocalDateTime subKey21 = LocalDateTime.of(key2, LocalTime.of(19, 0));

    Assert.assertEquals(new Long(2L), statistics.get(key1).get(subKey11).get(Direction.NORTH));
    Assert.assertEquals(new Long(2L), statistics.get(key2).get(subKey21).get(Direction.SOUTH));
  }

  @Test
  public void computeTotalVehiclesByPeriodWithNoSpecificDate() {
    Report subject = new Report(dataSource());
    Map<LocalDate, Map<LocalDateTime, Map<Period, Long>>> statistics = subject.totalVehiclesByPeriod(ReportTimeFilter.SIXTY, null);

    LocalDate key1 = LocalDate.of(2016, 05, 15);
    LocalDateTime subKey11 = LocalDateTime.of(key1, LocalTime.of(0, 0));
    LocalDate key2 = LocalDate.of(2016, 05, 16);
    LocalDateTime subKey21 = LocalDateTime.of(key2, LocalTime.of(19, 0));

    Assert.assertEquals(new Long(2L), statistics.get(key1).get(subKey11).get(Period.MORNING));
    Assert.assertEquals(new Long(2L), statistics.get(key2).get(subKey21).get(Period.EVENING));
  }

  @Test
  public void computeTotalVehiclesByPeriodWithSpecificDate() {
    Report subject = new Report(dataSource());
    Map<LocalDate, Map<LocalDateTime, Long>> statistics = subject.totalVehicles(ReportTimeFilter.SIXTY, LocalDate.of(2016, 05, 15));

    LocalDate key1 = LocalDate.of(2016, 05, 15);
    LocalDateTime subKey1 = LocalDateTime.of(key1, LocalTime.of(0, 0));
    LocalDateTime subKey2 = LocalDateTime.of(key1, LocalTime.of(23, 0));

    Assert.assertEquals(new Long(2L), statistics.get(key1).get(subKey1));
    Assert.assertEquals(new Long(2L), statistics.get(key1).get(subKey2));
  }

  private List<Vehicle> dataSource() {
    LocalDateTime base = LocalDateTime.of(LocalDate.of(2016, 05, 15), LocalTime.MIDNIGHT);

    List<Vehicle> vehicleList = new ArrayList<>();

    Registry reg = new Registry(base.plus(268981L, ChronoUnit.MILLIS), base.plus(269123L, ChronoUnit.MILLIS));
    Vehicle vehicle = new Vehicle(reg);
    vehicleList.add(vehicle);

    Registry reg1 = new Registry(base.plus(269523L, ChronoUnit.MILLIS), base.plus(269643L, ChronoUnit.MILLIS));
    Vehicle vehicle1 = new Vehicle(reg1);
    vehicleList.add(vehicle1);

    Registry reg2 = new Registry(base.plus(86328771L, ChronoUnit.MILLIS), base.plus(86328899L, ChronoUnit.MILLIS));
    Vehicle vehicle2 = new Vehicle(reg2);
    vehicleList.add(vehicle2);

    Registry reg3 = new Registry(base.plus(86338899L, ChronoUnit.MILLIS), base.plus(86338940L, ChronoUnit.MILLIS));
    Vehicle vehicle3 = new Vehicle(reg3);
    vehicleList.add(vehicle3);

    Registry reg4A = new Registry(base.plus(156789312L, ChronoUnit.MILLIS), base.plus(156789454L, ChronoUnit.MILLIS));
    Registry reg4B = new Registry(Hose.B, base.plus(156789352L, ChronoUnit.MILLIS), base.plus(156789484L, ChronoUnit.MILLIS));
    Vehicle vehicle4 = new Vehicle(reg4A, reg4B);
    vehicleList.add(vehicle4);

    Registry reg5A = new Registry(base.plus(156789684L, ChronoUnit.MILLIS), base.plus(156789743L, ChronoUnit.MILLIS));
    Registry reg5B = new Registry(Hose.B, base.plus(156789700L, ChronoUnit.MILLIS), base.plus(156789719L, ChronoUnit.MILLIS));
    Vehicle vehicle5 = new Vehicle(reg5A, reg5B);
    vehicleList.add(vehicle5);

    return vehicleList;
  }

}
