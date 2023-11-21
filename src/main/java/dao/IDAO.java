/**
 * 
 */
package dao;

import java.util.List;

/**
 * 
 */
public interface IDAO<T> {
	public void create(T entity);
    
    public void delete(T entity);
    
    public T findById(Long id);
    
    public List<T> findAll();
}
