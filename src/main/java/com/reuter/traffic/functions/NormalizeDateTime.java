package com.reuter.traffic.functions;

import com.reuter.traffic.models.ReportTimeFilter;

import java.time.LocalDateTime;

/**
 * Created by aandra1 on 16/05/16.
 */
@FunctionalInterface
public interface NormalizeDateTime {

  LocalDateTime normalize(ReportTimeFilter period, LocalDateTime dtm);
}
