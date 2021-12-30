package site.model;

import site.utli.DateRange;

/**
 * 继承类
 */
public class SiteSearchBean extends Site {
	private DateRange startRange;// 存货的日期范围

	/**
	 * 继承类取出存取货日期
	 */
	public DateRange getStartRange() {
		return startRange;
	}

	/**
	 * 设置 货物存取日期 已经用字符串进行分隔
	 * 
	 * @param startRange
	 */
	public void setStartRange(DateRange startRange) {
		this.startRange = startRange;
	}

}
