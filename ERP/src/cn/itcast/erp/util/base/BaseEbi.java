package cn.itcast.erp.util.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface BaseEbi<T> {
	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);

	public List<T> getAll();
	
	public T get(Long uuid);
	
	public List<T> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount);

	public Integer getCount(BaseQueryModel qm);
}
