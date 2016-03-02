package cn.itcast.erp.invoice.supplier.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface SupplierEbi extends BaseEbi<SupplierModel> {
	/**
	 * 获取具有商品类别数据的供应商数据集合
	 * @return
	 */
	public List<SupplierModel> getAllUnionGtm();
	/**
	 * 获取具有商品的商品类别信息对应的供应商信息
	 * @return
	 */
	public List<SupplierModel> getAllUnionTwo();

}