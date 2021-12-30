package site.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import site.global.Global;
import site.model.Site;
import site.model.SiteSearchBean;
import site.utli.DateRange;
import site.utli.JdbcUtlis;
import site.utli.PaginateInfo;

public class SiteDAO {
	/**
	 * 结果集
	 */
	private BeanListHandler<Site> handler = new BeanListHandler<>(Site.class);

	/**
	 * 查询所有信息
	 * 
	 * @param p
	 * @return
	 */
	public List<Site> findAll(SiteSearchBean ssb, PaginateInfo p) {
		Connection conn = Global.getConnection();
		QueryRunner qr = new QueryRunner();
		// 查询总页数
		String sql = "select count(id) from tb_warehouse ";

		String codition = "where 1=1";// 组装查询条件
		List<Object> args = new ArrayList<>();
		if (ssb.getId() != null) {
			codition += " and id = ? ";
			args.add(ssb.getId());
		}
		if (ssb.getGoodsId() != null && ssb.getGoodsId().trim().length() != 0) {
			codition += " and goods_id = ? ";
			args.add(ssb.getGoodsId());
		}
		if (ssb.getNames() != null && ssb.getNames().trim().length() != 0) {
			codition += " and names like ? ";
			args.add("%" + ssb.getNames() + "%");
		}
		if (ssb.getPhone() != null && ssb.getPhone().trim().length() != 0) {
			codition += " and phone = ? ";
			args.add(ssb.getPhone());
		}
		if (ssb.getStartRange() != null) {
			DateRange dr = ssb.getStartRange();
			if (dr.getFrom() != null) {// 起始时间不为空 //自定义的
				codition += " and start >= ? ";
				args.add(dr.getFrom());
			}
			if (dr.getTo() != null) {// 结束时间不为空
				codition += " and end < ? ";
				args.add(dr.getTo());
			}
		}

		Integer count = null;
		try {
			System.out.println(sql + codition);
			count = qr.query(conn, sql + codition, new ResultSetHandler<Integer>() {
				@Override
				public Integer handle(ResultSet rs) throws SQLException {
					rs.next();
					return rs.getInt(1);
				}
			}, args.toArray());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		p.setCount(count);
		// System.out.println(sql + codition);
		// System.out.println(args.toString());
		// System.out.println(count.toString());

		// 查询数据
		sql = "select id,name,numb, goods_id as goodsId,start,end,names,phone,yy from tb_warehouse " + codition
				+ "  limit ?,?";

		// System.out.println(sql);
		List<Site> sites = null;
		try {
			args.add(p.getOffset());
			args.add(p.getLimit());
			sites = qr.query(conn, sql, handler, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询商品信息失败");
		} finally {
			JdbcUtlis.closeConnection(conn);
		}
		return sites;
	}

	/**
	 * 根据编号唯一查询
	 * 
	 * @param id
	 * @return
	 */
	public Site findById(Integer id) {
		Connection conn = Global.getConnection();
		QueryRunner qr = new QueryRunner();

		String sql = "select id,name,numb, goods_id as goodsId,start,end,names,phone,yy from tb_warehouse  where id=?";
		List<Site> sites = null;
		try {
			sites = qr.query(conn, sql, handler, id);
			if (sites == null || sites.size() == 0) {
				throw new RuntimeException("未查询到商品信息");
			} else {
				return sites.get(0);// 返回唯一一个数值
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询商品信息失败");
		} finally {
			JdbcUtlis.closeConnection(conn);
		}
	}

	/**
	 * 删除一个商品
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteByIds(Integer[] ids) {
		if (ids == null || ids.length == 0) {
			return -1;
		}
		// 获取数据库连接
		Connection conn = Global.getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "";
		for (int i = 0; i < ids.length; i++) {
			sql += "?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql = "delete from tb_warehouse where id in (" + sql + ")";
		try {
			int rows = qr.update(conn, sql, (Object[]) ids);
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("删除学生信息时异常");
		}

	}

	/**
	 * 添加一个商品
	 * 
	 * @return
	 */
	public boolean save(Site site) {
		if (site == null) {
			throw new RuntimeException("保存的商品信息为空");
		}
		// 获取数据库连接
		Connection conn = Global.getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "insert into tb_warehouse (goods_id, phone, name , names, numb ,start , end ,yy ) values(?,?,?,?,?,?,?,?)";
		try {
			int rows = qr.update(conn, sql, site.getGoodsId(), site.getPhone(), site.getName(), site.getNames(),
					site.getNumb(), site.getStart(), site.getEnd(), site.getYy());
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("插入学生信息时异常");
		}
	}

	/**
	 * dao修改数据
	 * 
	 * @param site
	 * @return
	 */
	public boolean update(Site site) {
		if (site == null) {
			throw new RuntimeException("要修改的商品信息为空");
		}
		// 获取数据库连接
		Connection conn = Global.getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "update tb_warehouse set goods_id=?, phone=?, name=? , names=?, numb=?,start=? , end=? ,yy=? where id=?";
		try {
			int rows = qr.update(conn, sql, site.getGoodsId(), site.getPhone(), site.getName(), site.getNames(),
					site.getNumb(), site.getStart(), site.getEnd(), site.getYy(), site.getId());
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("修改商品信息时异常");
		}
	}
}
