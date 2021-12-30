package site.service;

import java.util.List;

import site.dao.SiteDAO;
import site.model.Site;
import site.model.SiteSearchBean;
import site.utli.PaginateInfo;

/**
 * 服务类
 */
public class SiteService {
	private SiteDAO dao = new SiteDAO();

	/**
	 * 查询所有信息
	 */
	public List<Site> findAll(SiteSearchBean ssb, PaginateInfo p) {
		return dao.findAll(ssb, p);
	}

	/**
	 * 根据编号批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteByIds(Integer[] ids) {
		return dao.deleteByIds(ids);
	}

	/**
	 * 根据编号进行唯一查询
	 * 
	 * @param id
	 * @return
	 */
	public Site findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * 保存商品信息
	 * 
	 * @return
	 */
	public boolean save(Site site) {
		return dao.save(site);
	}

	/**
	 * 修改商品信息
	 * 
	 * @return
	 */
	public boolean update(Site site) {
		return dao.update(site);
	}
}
