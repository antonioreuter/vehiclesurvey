package com.reuter.traffic;

import com.reuter.traffic.exceptions.ProcessStreamException;
import com.reuter.traffic.models.Hose;
import com.reuter.traffic.models.Registry;
import com.reuter.traffic.models.Report;
import com.reuter.traffic.models.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aandra1 on 14/05/16.
 */
public class VehicleSurvey {

  private LocalDateTime startAt;
  private List<Vehicle> dataSource;
  private Report survey;

  public VehicleSurvey(BufferedReader reader) {
    LocalDateTime midnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    startAt = midnight.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

    dataSource = new ArrayList<>();
    processStream(reader);
    survey = new Report(dataSource);
  }

  public VehicleSurvey(List<Vehicle> vehicleList) {
    dataSource = vehicleList;
    survey = new Report(dataSource);
  }

  /**
   * Prints the statistic values from traffic report
   *
   * @return
   */
  public String statistics() {
    return survey.reportStatistics();
  }

  private void processStream(BufferedReader reader) {
    try {
      if (reader == null)
        throw new IllegalArgumentException("You need to pass a valid data source.");

      if (!reader.ready())
        throw new IllegalArgumentException("The data source is empty.");


      List<String> records = null;
      while (reader.ready()) {
        records = new ArrayList<>();
        String reg01 = reader.readLine();
        String reg02 = reader.readLine();
        records.add(reg01);
        records.add(reg02);

        char hose = reg02.charAt(0);
        if (hose == 'B') {
          String reg03 = reader.readLine();
          String reg04 = reader.readLine();
          records.add(reg03);
          records.add(reg04);
        }

        dataSource.add(createVehicle(records));
      }
    } catch (IOException ex) {
      new ProcessStreamException("Was impossible to process the records.", ex);
    }
  }

  private Vehicle createVehicle(List<String> records) {
    List<Registry> registries = new ArrayList<>();
    Map<Hose, List<String>> registryMap = new HashMap<>();

    for (String record : records) {
      Hose hose = Hose.eval(record.charAt(0));
      if (!registryMap.containsKey(hose))
        registryMap.put(hose, new ArrayList<>());

      registryMap.get(hose).add(record.substring(1, record.length()));
    }

    for (Map.Entry<Hose, List<String>> entry : registryMap.entrySet()) {
      Long startAtMillis = Long.valueOf(entry.getValue().get(0));
      Long endAtMillis = Long.valueOf(entry.getValue().get(1));

      LocalDateTime startAtRegistry = this.startAt.plus(startAtMillis, ChronoUnit.MILLIS);
      LocalDateTime endAtRegistry = this.startAt.plus(endAtMillis, ChronoUnit.MILLIS);

      registries.add(new Registry(entry.getKey(), startAtRegistry, endAtRegistry));
    }

    return new Vehicle(registries);
  }

  public static void main(String[] args) throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
    VehicleSurvey vehicleSurvey = new VehicleSurvey(reader);
    System.out.println(vehicleSurvey.statistics());
  }
}
