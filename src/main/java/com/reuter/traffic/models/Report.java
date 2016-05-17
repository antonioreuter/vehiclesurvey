package com.reuter.traffic.models;

import com.reuter.traffic.functions.NormalizeDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by aandra1 on 15/05/16.
 */
public class Report {

  private static final String NEW_LINE = System.getProperty("line.separator");

  private static final NormalizeDateTime normalizeDateTimeFunction = (period, dateTime) -> {
    int minutes = dateTime.getMinute();
    minutes = ((minutes / period.minutes()) * period.minutes());

    return dateTime.withMinute(0).plusMinutes(minutes).truncatedTo(ChronoUnit.MINUTES);
  };

  private List<Vehicle> vehicleList;

  public Report(List<Vehicle> vehicleList) {
    this.vehicleList = vehicleList;
  }

  /**
   * Informs the total of vehicles by date and time and DIRECTION (NORTH or SOUTH)
   *
   * @param period     - How the time will be grouped: FIFTEEN, TWENTY, THIRTY or SIXTY minutes
   * @param dateFilter - Filter by date. If you pass null, all the records
   *                   will be taken in count.
   * @return
   */
  public Map<LocalDate, Map<LocalDateTime, Map<Direction, Long>>> totalVehiclesByDirection(ReportTimeFilter period, LocalDate dateFilter) {
    return vehicleStream(dateFilter).collect(
        Collectors.groupingBy(Vehicle::getDate,
            Collectors.groupingBy(v -> normalizeDateTimeFunction.normalize(period, v.startAt()),
                Collectors.groupingBy(Vehicle::direction, Collectors.counting()
                ))));
  }

  /**
   * Informs the total of vehicles by date and time and PERIOD (MORNING or EVENING)
   *
   * @param period     - How the time will be grouped: FIFTEEN, TWENTY, THIRTY or SIXTY minutes
   * @param dateFilter - Filter by date. If you pass null, all the records
   *                   will be taken in count.
   * @return
   */
  public Map<LocalDate, Map<LocalDateTime, Map<Period, Long>>> totalVehiclesByPeriod(ReportTimeFilter period, LocalDate dateFilter) {
    return vehicleStream(dateFilter).collect(
        Collectors.groupingBy(Vehicle::getDate,
            Collectors.groupingBy(v -> normalizeDateTimeFunction.normalize(period, v.startAt()),
                Collectors.groupingBy(Vehicle::period,
                    Collectors.counting()))));
  }

  /**
   * Informs the total of vehicles by date and time
   *
   * @param period     - How the time will be grouped: FIFTEEN, TWENTY, THIRTY or SIXTY minutes
   * @param dateFilter - Filter by date. If you pass null, all the records
   *                   will be taken in count.
   * @return
   */
  public Map<LocalDate, Map<LocalDateTime, Long>> totalVehicles(final ReportTimeFilter period, LocalDate dateFilter) {
    return vehicleStream(dateFilter).collect(
        Collectors.groupingBy(Vehicle::getDate,
            Collectors.groupingBy(v -> normalizeDateTimeFunction.normalize(period, v.startAt()), Collectors.counting())));
  }

  /**
   * Informs the average speed.
   *
   * @param dateFilter - Filter by date. If you pass null, all the records
   *                   will be taken in count.
   * @return
   */
  public double avgSpeed(LocalDate dateFilter) {
    return vehicleStream(dateFilter).mapToDouble(Vehicle::speed).average().getAsDouble();
  }

  private Stream<Vehicle> vehicleStream(LocalDate dateFilter) {
    Stream<Vehicle> stream = vehicleList.stream();

    if (dateFilter != null) {
      stream = stream.filter(vehicle -> vehicle.getDate().equals(dateFilter));
    }

    return stream;
  }

  /**
   * Print the statistics.
   *
   * @return
   */
  public String reportStatistics() {
    return this.reportStatistics(null);
  }

  public String reportStatistics(LocalDate dateFilter) {
    Formatter fmt = new Formatter();
    StringBuilder sb = new StringBuilder();
    if (dateFilter != null)
      sb.append(System.out.printf("Filter by date %t", dateFilter));

    sb.append(fmt.format("Avg Speed: %.2f", avgSpeed(dateFilter)));
    sb.append(NEW_LINE);

    sb.append("Total Vehicles grouped by Direction:");
    sb.append(NEW_LINE);
    printMap(totalVehiclesByDirection(ReportTimeFilter.SIXTY, dateFilter), sb);
    sb.append("-------------------------------------------------------");
    sb.append(NEW_LINE);

    sb.append("Total Vehicles grouped by Period:");
    sb.append(NEW_LINE);
    printMap(totalVehiclesByPeriod(ReportTimeFilter.SIXTY, dateFilter), sb);
    sb.append("-------------------------------------------------------");
    sb.append(NEW_LINE);

    sb.append("All Vehicles grouped by date and period: " + ReportTimeFilter.FIFTEEN);
    sb.append(NEW_LINE);
    printMap(totalVehicles(ReportTimeFilter.FIFTEEN, null), sb);
    sb.append("-------------------------------------------------------");
    sb.append(NEW_LINE);

    sb.append("All Vehicles grouped by date and period: " + ReportTimeFilter.TWENTY);
    sb.append(NEW_LINE);
    printMap(totalVehicles(ReportTimeFilter.TWENTY, null), sb);
    sb.append("-------------------------------------------------------");
    sb.append(NEW_LINE);

    sb.append("All Vehicles grouped by date and period: " + ReportTimeFilter.THIRTY);
    sb.append(NEW_LINE);
    printMap(totalVehicles(ReportTimeFilter.THIRTY, null), sb);
    sb.append("-------------------------------------------------------");
    sb.append(NEW_LINE);

    sb.append("All Vehicles grouped by date and period: " + ReportTimeFilter.SIXTY);
    sb.append(NEW_LINE);
    printMap(totalVehicles(ReportTimeFilter.SIXTY, null), sb);
    sb.append("-------------------------------------------------------");
    sb.append(NEW_LINE);


    return sb.toString();
  }

  public String reportStatistics(LocalDate startDate, LocalDate endDate) {
    StringBuilder sb = new StringBuilder();
    long diff = ChronoUnit.DAYS.between(startDate, endDate);
    for (long startAt = 0; startAt <= diff; startAt++)
      sb.append(this.reportStatistics(startDate.plusDays(startAt)));

    return sb.toString();
  }

  private void printMap(Map data, StringBuilder sb) {
    if (!data.isEmpty()) {
      for (Object key : data.keySet()) {
        sb.append(key + ": ");
        Object value = data.get(key);
        if (value instanceof Map) {
          sb.append(NEW_LINE);
          printMap((Map) value, sb);
        } else {
          sb.append(value);
          sb.append(NEW_LINE);
        }
      }
    }
  }
}
