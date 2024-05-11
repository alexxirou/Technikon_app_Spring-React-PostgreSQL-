package com.scytalys.technikon.utility;

import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HeaderUtility {
    public static HttpHeaders createHeaders(String headerType, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerType, message);
        headers.add("Timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return headers;
    }
}
