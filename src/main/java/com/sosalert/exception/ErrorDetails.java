package com.sosalert.exception;

import java.util.Date;

public record ErrorDetails(Date timestamp, String message, String details) {

}
