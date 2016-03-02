package cn.itcast.erp.invoice.storedetail.dao.dao;

import cn.itcast.erp.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.erp.util.base.BaseDao;

public interface StoreDetailDao extends BaseDao<StoreDetailModel>{
	/**
	 * 获取指定仓库指定商品的存储明细信息
	 * @param storeUuid 仓库uuid
	 * @param gmUuid 商品uuid;
	 * @return
	 */
	public StoreDetailModel getBySmAndGm(Long storeUuid, Long gmUuid);

}
