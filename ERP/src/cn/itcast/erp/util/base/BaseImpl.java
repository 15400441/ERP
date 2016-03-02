package cn.itcast.erp.util.base;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.erp.util.exception.AppException;

public abstract class BaseImpl<T> extends HibernateDaoSupport{
	//此处需要获取泛型的类型T.class ,对成员变量entityClass进行初始化为泛型T的class就可以了
	private Class<T> entityClass;
	//后去泛型的类型Class
	//在对象初始化时，初始化entityClass的值，保障对象调用方法前已经初始化完毕
	public BaseImpl(){
		//获取类型
		Type type = getClass().getGenericSuperclass();
		ParameterizedType ptype = (ParameterizedType)type;
		Type[] types = ptype.getActualTypeArguments();
		entityClass = (Class<T>) types[0];
	}
	
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}
	
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}
	
	public T get(Long uuid) {
		return this.getHibernateTemplate().get(entityClass, uuid);
	}
	
	public List<T> getAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	public List<T> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		doQbc(dc,qm);
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*pageCount,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.setProjection(Projections.rowCount());
		doQbc(dc,qm);
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}

	public abstract void doQbc(DetachedCriteria dc,BaseQueryModel qm);
}
