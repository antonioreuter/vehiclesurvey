package com.reuter.traffic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aandra1 on 15/05/16.
 */
public class Poc {

  private static LocalDateTime convert(LocalDateTime dtm, int baseTime) {
    int minutes = dtm.getMinute();
    minutes = ((minutes / baseTime) * baseTime);

    return dtm.withMinute(0).plusMinutes(minutes);
  }

  public static void main(String[] args) {

    LocalDateTime dtm0 = LocalDateTime.now();
    System.out.println(dtm0);

    LocalDateTime dtm1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 06));


    LocalDateTime dtm2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 15));
    LocalDateTime dtm3 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 18));
    LocalDateTime dtm4 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 20));
    LocalDateTime dtm5 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 21));
    LocalDateTime dtm6 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 29));
    LocalDateTime dtm7 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 30));
    LocalDateTime dtm8 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 35));
    LocalDateTime dtm9 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 50));
    LocalDateTime dtm10 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 59));

    List<LocalDateTime> list = new ArrayList<>();
    list.add(dtm1);
    list.add(dtm2);
    list.add(dtm3);
    list.add(dtm4);
    list.add(dtm5);
    list.add(dtm6);
    list.add(dtm7);
    list.add(dtm8);
    list.add(dtm9);
    list.add(dtm10);


    for (LocalDateTime dtm : list) {
      System.out.println(dtm + " : " + convert(dtm, 60));
    }

  }
}
