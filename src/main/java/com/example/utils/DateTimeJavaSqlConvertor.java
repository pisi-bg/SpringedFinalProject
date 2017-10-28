package com.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTimeJavaSqlConvertor {

	private DateTimeJavaSqlConvertor() {
	}

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	public static String localDateTimeToSql(LocalDateTime localDateTime) {
		return localDateTime.format(FORMAT);
	}

	public static LocalDateTime sqlToLocalDateTime(String sqlDateTime) {
		return LocalDateTime.parse(sqlDateTime, FORMAT);
	}

}
