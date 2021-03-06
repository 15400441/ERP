package cn.itcast.erp.invoice.supplier.business.ebo;

import java.util.List;

import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.dao.dao.SupplierDao;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class SupplierEbo implements SupplierEbi{
	private SupplierDao supplierDao;
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
	public void save(SupplierModel sm) {
		supplierDao.save(sm);
	}

	public List<SupplierModel> getAll() {
		return supplierDao.getAll();
	}

	public SupplierModel get(Long uuid) {
		return supplierDao.get(uuid);
	}

	public void update(SupplierModel sm) {
		supplierDao.update(sm);
	}

	public void delete(SupplierModel sm) {
		supplierDao.delete(sm);
	}

	public List<SupplierModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return supplierDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return supplierDao.getCount(qm);
	}

	public List<SupplierModel> getAllUnionGtm() {
		return supplierDao.getAllJoinGtm();
	}

	public List<SupplierModel> getAllUnionTwo() {
		return supplierDao.getAllJoinGtmJoinGm();
	}

}