package site.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.model.Site;
import site.model.SiteSearchBean;
import site.service.SiteService;
import site.utli.DateRange;
import site.utli.DateUtils;
import site.utli.PaginateInfo;

@WebServlet("/ware/*")
public class SiteServelt extends HttpServlet {

	private static final long serialVersionUID = 3222829694462844852L;
	SiteService service = new SiteService();

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getPathInfo();
		switch (action) {
		case "/list":
			list(req, resp);
			break;
		case "/delete":
			System.out.println("1");
			delete(req, resp);
			break;
		case "/ajaxDelete":
			ajaxDelete(req, resp);
			break;
		case "/add":
			if (req.getMethod().toLowerCase().equals("get")) {
				gotoAdd(req, resp);// 跳转到添加页面
			} else {
				submitAdd(req, resp);// 提交表单
			}
			break;
		case "/edit":
			if (req.getMethod().toLowerCase().equals("get")) {
				gotoEdit(req, resp);// 跳转到修改页面
			} else {
				submitEdit(req, resp);// 提交表单
			}
		}
	}

	/**
	 * 查询
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		// 继承自Site类的实例，拥有Site类所有的方法
		SiteSearchBean ssb = new SiteSearchBean();

		String id = req.getParameter("id");
		String goodsId = req.getParameter("goodsId");
		String names = req.getParameter("names");
		String phone = req.getParameter("phone");
		String startRange = req.getParameter("startRange");// 日期范围 Site中没有时间段，所以在继承类中设置

		if (id != null && id.trim().length() != 0) {
			ssb.setId(Integer.valueOf(id));
		}
		if (goodsId != null && goodsId.trim().length() != 0) {
			ssb.setGoodsId(goodsId);
		}
		if (names != null && names.trim().length() != 0) {
			ssb.setNames(names);
		}
		if (phone != null && phone.trim().length() != 0) {
			ssb.setPhone(phone);
		}
		if (startRange != null && startRange.trim().length() != 0) {
			// 时间日期以 - 分隔 startRange是自己定义的工具类
			DateRange dr = DateRange.of(startRange, " - ");
			ssb.setStartRange(dr);
		}

		// 默认第一页显示3条数据
//		PaginateInfo pi = PaginateInfo.DEFAULT;
		Integer pageNo = 1;// 对上面的改进
		Integer pageSize = 10;

		// 获取前端传过来的参数
		String sPageNo = req.getParameter("pageNo");
		String sPageSize = req.getParameter("pageSize");

		// 如果前端传过来参数且不为空，就重新设置页码
		if (sPageNo != null && !sPageNo.trim().equals("")) {
			pageNo = Integer.valueOf(sPageNo);
		}
		if (sPageSize != null && !sPageSize.trim().equals("")) {
			pageSize = Integer.valueOf(sPageSize);
		}
		PaginateInfo pi = new PaginateInfo(pageNo, pageSize);

		// 设置页导航为4
		pi.setNavItemCount(4);
		// 取出总页数
		Integer pages = pi.getTotalPages();
		System.out.println();
		// 查询结果
		List<Site> sites = service.findAll(ssb, pi);

		// 把error从请求域中取出来放入对话域中,然后删除，不能一直存放在会话域中
		String error = (String) req.getSession().getAttribute("error");
		req.setAttribute("error", error);
		// 不能一直放在对话域中，会话域结束即删除此请求。
		req.getSession().removeAttribute("error");

		// 把继承对象放入域对象中，给查询回显用
		req.setAttribute("ssb", ssb);
		// pi放入域中
		req.setAttribute("pi", pi);
		// 结果放入域中
		req.setAttribute("sites", sites);
		// 当前页放入域中
		req.setAttribute("pageNo", pi.getPageNo());
		// 总页数放入域中
		req.setAttribute("pages", pages);

		// 登录校验
//		String password = req.getParameter("password");
//		String username = req.getParameter("username");
//		System.out.pri ntln(password);
//		System.out.println(username);

//		if (password.equals("xiaoli") && username.equals("123456")) {
//			
//		} else {
//			
//		}
		req.getRequestDispatcher("/WEB-INF/jsp/goods/site.jsp").forward(req, resp);

	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String deleteIds = req.getParameter("deleteIds");

		if (deleteIds == null || deleteIds.length() == 0) {

			// 网请求域中放入一个错位u提示，重定向以后用会话域把错误提示带到前端
			// 带到前端后立即删除此请求域，避免占用空间
			req.setAttribute("error", "未选中");
			// 转发到另一个请求
			req.getRequestDispatcher("/ware/list").forward(req, resp);
		} else {
			String[] arrIds = deleteIds.split(",");
			Integer[] intIds = new Integer[arrIds.length];
			for (int i = 0; i < intIds.length; i++) {
				// 把字符串数组转换成Int类型的数组
				intIds[i] = Integer.valueOf(arrIds[i]);
			}
			int rows = service.deleteByIds(intIds);

			// req.getRequestDispatcher("/ware/list").forward(req, resp);
			// 重定向
			resp.sendRedirect(req.getContextPath() + "/ware/list");

		}
	}

	/**
	 * ajax删除
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void ajaxDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		// 响应成json类型的数据
		resp.setContentType("application/json;charset=utf-8");
		PrintWriter pw = resp.getWriter();

		// 这里返回的是一个数组，上面返回的是一个字符串
		String[] strIds = req.getParameterValues("ids");// 根据前端json的键名取值
		// 校验前端是否传过来参数
		if (strIds == null || strIds.length == 0) {
			// write是String类型 从后台响应到前台需要转义字符
			// write响应进去的时json对象，前端反调时用。pw.write("{\"success\":true}");
			pw.write("{\"success\":false,\"error\":\"未提供要删除的学生编号\"}");
			pw.flush();
			pw.close();
		} else {
			Integer[] ids = new Integer[strIds.length];
			for (int i = 0; i < ids.length; i++) {
				ids[i] = Integer.valueOf(strIds[i]);// 转换成Integer类型数组
			}
			int rows = service.deleteByIds(ids);
			pw.write("{\"success\":true,\"rows\":\"" + rows + "\"}");
			pw.flush();
			pw.close();
		}

	}

	/**
	 * 跳转到添加页面
	 */
	private void gotoAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
	}

	/**
	 * 提交表单，添加学生信息
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void submitAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 在最上面统一编码格式，流传输会乱码
		req.setCharacterEncoding("utf-8");

		String goodsId = req.getParameter("goodsId");
		String name = req.getParameter("name");
		String numbs = req.getParameter("numb");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String names = req.getParameter("names");
		String phone = req.getParameter("phone");
		String yy = req.getParameter("yy");

		// 避免前端提交表单出错时刷新页面，在这里获取客户输入的内容，在下面放入域对象中返回给前端
		Map<String, String[]> params = req.getParameterMap();
		// map是一个集合
		// 校验前端是否传过来值
		if (goodsId == null || goodsId.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "商品编号不允许为空");
			req.setAttribute("params", params);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		if (start == null || start.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "入库日期不允许为空");
			req.setAttribute("params", params);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}
		if (end == null || end.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "出库日期不允许为空");
			req.setAttribute("params", params);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}
		if (names == null || names.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "联系人不允许为空");
			req.setAttribute("params", params);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		if (phone == null || phone.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "联系电话不允许为空");
			req.setAttribute("params", params);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		Site site = new Site();
		site.setGoodsId(goodsId);// 商品id
		site.setName(name);// 商品名称
		Integer numb = Integer.valueOf(numbs);
		site.setNumb(numb);// 商品数量
		site.setNames(names);// 联系人
		site.setPhone(phone);// 联系电话
		site.setStart(DateUtils.parseDate(start));// 开始日期需要格式转换
		site.setEnd(DateUtils.parseDate(end));// 结束日期
		site.setYy(yy);// 是否预约

		// 捕获异常，防止商品编号重复
		Boolean success = null;
		try {
			success = service.save(site);
		} catch (Exception e) {
			req.setAttribute("error", "您填入的商品编号已存在");
			req.setAttribute("params", params);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		if (success) {
			// 成功则返回列表界面
			req.getRequestDispatcher("/ware/list").forward(req, resp);
		} else {
			// 失败则弹出提示信息，并且留在添加商品页面
			req.setAttribute("error", "保存商品信息失败");
			req.getRequestDispatcher("/WEB-INF/jsp/goods/add.jsp").forward(req, resp);
		}

	}

	/**
	 * 跳转到修改页面 需要显示要修改的信息，需要到数据库查询
	 */
	private void gotoEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取前端传过来要修改的参数
		String id = req.getParameter("id");

		// 校验前端传过来的参数
		if (id == null || id.trim().length() == 0) {
			req.setAttribute("error", "要修改的编号不可为空");
			req.getRequestDispatcher("/WEB-INF/jsp/goods/site.jsp");// 转发到列表页面
			return;// 执行到这里下面的代码就不会执行。
		}
		// 根据编号到数据库查出数据
		Site site = service.findById(Integer.valueOf(id));
		// 判断数据库是否有根据id查询到的数据
		if (site == null) {
			req.setAttribute("error", "要修改的学生信息不存在");
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp");// 转发到当前页面
			return;// 执行到这里下面的代码就不会执行。
		}
		// 不为空则把学生数据设置到域中
		req.setAttribute("site", site);

		req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);

	}

	/**
	 * 提交修改表单
	 */
	private void submitEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 在最上面统一编码格式，流传输会乱码
		req.setCharacterEncoding("utf-8");
		String sid = req.getParameter("id");
		String goodsId = req.getParameter("goodsId");
		String name = req.getParameter("name");
		String numbs = req.getParameter("numb");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String names = req.getParameter("names");
		String phone = req.getParameter("phone");
		String yy = req.getParameter("yy");

		// 先组装数据
		Site site = new Site();
		Integer id = Integer.valueOf(sid);
		site.setId(Integer.valueOf(id));//
		site.setGoodsId(goodsId);// 商品编号
		site.setName(name);// 商品名称
		Integer numb = Integer.valueOf(numbs);
		site.setNumb(numb);// 商品数量
		site.setNames(names);// 联系人
		site.setPhone(phone);// 联系电话
		if (start != null && start.trim().length() != 0) {
			site.setStart(DateUtils.parseDate(start));// 开始日期需要格式转换
		}
		if (start != null && start.trim().length() != 0) {
			site.setEnd(DateUtils.parseDate(end));// 结束日期
		}
		site.setYy(yy);// 是否预约

		// 校验前端是否传过来值

		if (sid == null || sid.trim().length() == 0) {// 没有入库编号
			req.setAttribute("error", "入库编号不允许为空");
			resp.sendRedirect(req.getContextPath() + "/ware/edit");// 重定向
			return;// 表示后面不在继续执行
		}

		if (goodsId == null || goodsId.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "商品编号不允许为空");
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		if (start == null || start.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "入库日期不允许为空");
			req.setAttribute("site", site);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}
		if (end == null || end.trim().length() == 0) {// 没有商品号
			req.setAttribute("error", "出库日期不允许为空");
			req.setAttribute("site", site);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}
		if (names == null || names.trim().length() == 0) {// 没有联系人
			req.setAttribute("error", "联系人不允许为空");
			req.setAttribute("site", site);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		if (phone == null || phone.trim().length() == 0) {// 没有联系方式
			req.setAttribute("error", "联系电话不允许为空");
			req.setAttribute("site", site);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		// 判断商品编号是否存在

		// 捕获异常
		Boolean success;
		try {
			success = service.update(site);
		} catch (Exception e) {
			req.setAttribute("error", "修改的商品编号已存在，请重新输入");
			req.setAttribute("site", site);// 把客户输入的内容传给前端
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
			return;// 表示后面不在继续执行
		}

		if (success) {
			// 成功则返回列表界面
			// resp.sendRedirect("/ware/list")
			// resp.sendRedirect(req.getContextPath() + "/ware/list");
			req.getRequestDispatcher("/ware/list").forward(req, resp);
		} else {
			// 失败则弹出提示信息，并且留在添加商品页面
			req.setAttribute("error", "修改商品信息失败");
			req.getRequestDispatcher("/WEB-INF/jsp/goods/edit.jsp").forward(req, resp);
		}
	}
}
