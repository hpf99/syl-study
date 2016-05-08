package org.syl.common.study.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateUtil {

	static Logger log = LoggerFactory.getLogger(LocalDateUtil.class);

	/**
	 * localDateTime 转换成为 日期字符串
	 * 
	 * @param date
	 *            localDateTime
	 * @param pattern
	 *            默认是 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String LocalDateTime2Str(LocalDateTime date, String pattern) {
		if (StringUtil.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		String format = date.format(DateTimeFormatter.ofPattern(pattern));
		log.info("pattern:[{}],datestr:[{}]", pattern, format);
		return format;
	}

	/**
	 * localDateTime 转换成为 yyyy-MM-dd HH:mm:ss 格式日期字符串
	 * 
	 * @param date
	 *            localDateTime
	 * @return
	 */
	public static String localDateTime2Str(LocalDateTime date) {
		return LocalDateTime2Str(date, null);
	}

	/**
	 * 日期字符串转换成 localDateTime
	 * 
	 * @param datestr
	 * @param pattern
	 *            默认 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static LocalDateTime str2LocalDateTime(String datestr, String pattern) {
		if (StringUtil.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		log.info("datestr:[{}],pattern:[{}]", datestr, pattern);
		return LocalDateTime.parse(datestr,
				DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 日期字符串转换成 localDateTime
	 * 
	 * @param datestr
	 *            默认 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static LocalDateTime str2LocalDateTime(String datestr) {
		return str2LocalDateTime(datestr, null);
	}

}
