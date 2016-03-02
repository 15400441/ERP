package cn.itcast.erp.auth.dep.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
//@Transactional内部工作原理是AOP
//AOP两个核心部件：通知类(框架自己完成),切入点表达式
//@Transactional出现在某个类或接口上时，表明：该类/接口中的所有方法添加事务（读写）
//execute(cn.itcast.erp.auth.dep.business.ebi.DepEbi.*(..))
public interface DepEbi extends BaseEbi<DepModel> {
	
}
