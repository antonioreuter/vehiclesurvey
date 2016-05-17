package com.reuter.traffic.exceptions;

/**
 * Created by aandra1 on 16/05/16.
 */
public class ProcessStreamException extends RuntimeException {

  public ProcessStreamException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
