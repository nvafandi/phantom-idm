package com.phantom.idm.utils;

import com.phantom.idm.constant.Constant;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;


public class DateUtil {

	private DateUtil() {
		throw new IllegalStateException("Date Utility class");
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp getCurrentTimestampGMT7() {
		var zdt = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp getCurrentTimestampGMT8() {
		var zdt = ZonedDateTime.now(ZoneId.of("Asia/Taipei"));
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp getCurrentTimestampGMT9() {
		var zdt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp getCurrentTimestampGMT0() {
		var zdt = ZonedDateTime.now(ZoneId.of("Zulu"));
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static String formatDate(Date date) {
		if (Objects.isNull(date)) {
			return null;
		}
		var outputDateFormat = new SimpleDateFormat(Constant.FORMAT_DATE_YYYYMMDD_1);
		return outputDateFormat.format(date);
	}

	public static Date parseDate(String date) throws ParseException {
		if (Objects.isNull(date) || date.trim().isEmpty()) {
			return null;
		}
		var outputDateFormat = new SimpleDateFormat(Constant.FORMAT_DATE_YYYYMMDD_1);
		return outputDateFormat.parse(date.trim());
	}

	public static Date expiredDate(Date date) {
		return Date.from(LocalDate.now()
				.plusMonths(3)
				.atStartOfDay(ZoneId.systemDefault())
				.toInstant());
	}
	
}
