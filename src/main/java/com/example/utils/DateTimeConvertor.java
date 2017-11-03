package com.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTimeConvertor {

	private DateTimeConvertor() {
	}

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	private static final DateTimeFormatter USER_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	public static String localDateTimeToSql(LocalDateTime localDateTime) {
		return localDateTime.format(FORMAT);
	}

	public static LocalDateTime sqlToLocalDateTime(String sqlDateTime) {
		return LocalDateTime.parse(sqlDateTime, FORMAT);
	}

	public static String UserView(LocalDateTime localDateTime) {
		return localDateTime.format(USER_FORMAT);
	}

}
