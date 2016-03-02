package cn.itcast.erp.auth.dep.business.ebo;

import java.util.List;

import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.dao.dao.DepDao;
import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.dep.vo.DepQueryModel;
import cn.itcast.erp.util.base.BaseQueryModel;
import cn.itcast.erp.util.exception.AppException;

public class DepEbo implements DepEbi{
	private DepDao depDao;
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	
	public void save(DepModel dm) {
		//如果用户输入的部门名称为全部都是空格，认为有问题
		if(dm.getName()!=null && dm.getName().trim().length() == 0){
			//认定出问题
			throw new AppException("INFO_DEP_NAME_IS_NOT_EMPTY");
		}
		depDao.save(dm);
	}

	public List<DepModel> getAll() {
		return depDao.getAll();
	}

	public DepModel get(Long uuid) {
		return depDao.get(uuid);
	}

	public void update(DepModel dm) {
		depDao.update(dm);
	}

	public void delete(DepModel dm) {
//		if(true){
//			throw new AppException("悟空，你又调皮了！");
//		}
		depDao.delete(dm);
	}

	public List<DepModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return depDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return depDao.getCount(qm);
	}
	
}
