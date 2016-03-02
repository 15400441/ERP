package cn.itcast.erp.invoice.goods.business.ebo;

import java.util.List;

import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsEbo implements GoodsEbi{
	private GoodsDao goodsDao;
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	
	public void save(GoodsModel gm) {
		//设置使用次数默认值为0
		gm.setUseNum(0);
		//未添加对maxNum,minNum的设计
		goodsDao.save(gm);
	}

	public List<GoodsModel> getAll() {
		return goodsDao.getAll();
	}

	public GoodsModel get(Long uuid) {
		return goodsDao.get(uuid);
	}

	public void update(GoodsModel gm) {
		//useNum不能为null
		//改为快照更新
		//goodsDao.update(gm);
		
	}

	public void delete(GoodsModel gm) {
		goodsDao.delete(gm);
	}

	public List<GoodsModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return goodsDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return goodsDao.getCount(qm);
	}

	public List<GoodsModel> getAllByGtm(Long uuid) {
		return goodsDao.getAllByGtmUuid(uuid);
	}

	public List<GoodsModel> getAllByGtm(Long uuid, Long[] uuids) {
		return goodsDao.getAllByGtmAndUuidNotInUuids(uuid,uuids);
	}

	public void useNumUpdate() {
		goodsDao.useNumUpdate();
	}

	public List<Object[]> getWarnList() {
		return goodsDao.getWarnList();
	}

}