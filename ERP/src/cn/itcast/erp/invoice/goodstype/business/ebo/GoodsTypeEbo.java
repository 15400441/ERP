package cn.itcast.erp.invoice.goodstype.business.ebo;

import java.util.List;

import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsTypeEbo implements GoodsTypeEbi{
	private GoodsTypeDao goodsTypeDao;
	public void setGoodsTypeDao(GoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
	}
	
	public void save(GoodsTypeModel gm) {
		goodsTypeDao.save(gm);
	}

	public List<GoodsTypeModel> getAll() {
		return goodsTypeDao.getAll();
	}

	public GoodsTypeModel get(Long uuid) {
		return goodsTypeDao.get(uuid);
	}

	public void update(GoodsTypeModel gm) {
		goodsTypeDao.update(gm);
	}

	public void delete(GoodsTypeModel gm) {
		goodsTypeDao.delete(gm);
	}

	public List<GoodsTypeModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return goodsTypeDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return goodsTypeDao.getCount(qm);
	}

	public List<GoodsTypeModel> getAllBySm(Long uuid) {
		return goodsTypeDao.getAllBySmUuid(uuid);
	}

	public List<GoodsTypeModel> getAllUnionBySm(Long uuid) {
		return goodsTypeDao.getAllJoinGmBySmUuid(uuid);
	}

	public List<GoodsTypeModel> getAllUnionBySm(Long supplierUuid, Long[] uuids) {
		return goodsTypeDao.getAllUnionBySm(supplierUuid,uuids);
	}

}