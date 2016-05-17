package com.reuter.traffic.models;

import java.time.LocalDateTime;

/**
 * Created by aandra1 on 14/05/16.
 */
public enum Period {
  MORNING, EVENING;

  public static Period eval(LocalDateTime date) {
    return (date.getHour() < 12) ? MORNING : EVENING;
  }
}
