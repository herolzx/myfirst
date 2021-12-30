package site.utli;

/**
 * 页面信息
 */
public final class PaginateInfo {
	// 查询全部
	public static final PaginateInfo MAX_LINIT = new PaginateInfo(1, Integer.MAX_VALUE);
	public static final PaginateInfo DEFAULT = new PaginateInfo(1, 10);

	private Integer pageNo; // 第几页/当前页
	private final Integer pageSize;// 每页显示多少行
	private Integer count;// 总行数
	private Integer pages = 4;// 总页数
	private Integer navItemCount = 4;// 页导航

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getNavItemCount() {
		return navItemCount;
	}

	public void setNavItemCount(Integer navItemCount) {
		this.navItemCount = navItemCount;
	}

	public PaginateInfo(Integer pageNo, Integer pageSize) {
		super();
		this.pageNo = pageNo < 1 ? 1 : pageNo;
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getCount() {
		return count;

	}

	/**
	 * 设置总记录数
	 */
	public void setCount(Integer count) {
		this.count = count;
		this.pages = count / getPageSize();// 总页数
		if (count % getPageSize() > 0) {
			this.pages += 1;
		}
		// 重新修正当前页码，当总页大于0且当前页码大于总页时
		if (this.pages > 0 && this.pageNo > pages) {// 1>0时会把 当前页码会变成0
			this.pageNo = pages;
		}
	}

	/**
	 * 从第几个位置开始查
	 */
	public Integer getOffset() {//
		return (this.pageNo - 1) * this.pageSize;
	}

	/**
	 * 查询多少条
	 */
	public Integer getLimit() {
		return this.getPageSize();
	}

	/**
	 * 导航页起始值
	 */
	public int getNavItemStart() {
		int cnt = this.navItemCount / 2;
		int start = this.pageNo - cnt;
		if (start < 1) {
			start = 1;
		}
		return start;
	}

	/**
	 * 导航页结束值
	 */
	public int getNavItemEnd() {
		int end = getNavItemStart() + navItemCount - 1;
		if (end > this.pages) {
			end = this.pages;
		}
		return end;
	}

	/**
	 * 计算
	 */
	public Integer getTotalPages() {
		return pages;
	}

}
