package site.utli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	/**
	 * 把String转换成Date，不包括时间
	 */
	public static Date parseDate(String date) {
		// 固定写法：
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("日期格式转换失败");
		}
		return d;
	}
}
