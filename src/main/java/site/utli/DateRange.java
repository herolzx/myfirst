package site.utli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期范围工具类
 */
public class DateRange {
	// 第一参数根据第二个参数继续宁分割
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private final Date from;// 起始日期
	private final Date to;// 结束日期

	/**
	 * 前面的字符串以后面的拆分
	 */
	public static DateRange of(String source, String split) {
		if (source == null || source.trim().length() == 0) {
			return null;
		}

		String[] tokens = source.split(split);
		Date from = null;
		Date to = null;
		// 如果长度大于0说明拆分了一个日期
		if (tokens.length > 0) {
			try {
				from = sdf.parse(tokens[0].trim());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 如果长度大于1，分割出来两个日期
		if (tokens.length > 1) {
			try {
				to = sdf.parse(tokens[1].trim());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return new DateRange(from, to);
	}

	public DateRange(Date from, Date to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	/**
	 * 将日期范围转换为字符串
	 * 
	 * @param split
	 * @return
	 */
	public String toString(String split) {
		return sdf.format(from) + split + sdf.format(to);// fromat 将时间日期转化为时间日期字符串
	}

	/**
	 * 默认用 - 分隔
	 */
	@Override
	public String toString() {
		return toString(" - ");
	}

}
