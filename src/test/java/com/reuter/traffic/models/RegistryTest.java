package com.reuter.traffic.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by aandra1 on 16/05/16.
 */
@RunWith(JUnit4.class)
public class RegistryTest {

  @Test(expected = IllegalArgumentException.class)
  public void createRegistryWithInvalidDates() {
    LocalDateTime dtm1 = LocalDateTime.now();
    LocalDateTime dtm2 = dtm1.plus(140L, ChronoUnit.MILLIS);
    Registry registry = new Registry(Hose.A, dtm2, dtm1);
  }

  @Test
  public void calculateDiffInMilliSeconds() {
    Long addMillis = new Long(140L);
    LocalDateTime dtm1 = LocalDateTime.now();
    LocalDateTime dtm2 = dtm1.plus(addMillis, ChronoUnit.MILLIS);
    Registry registry = new Registry(Hose.A, dtm1, dtm2);

    Assert.assertEquals(addMillis, registry.diffTimeInMilliseconds());
  }
}
