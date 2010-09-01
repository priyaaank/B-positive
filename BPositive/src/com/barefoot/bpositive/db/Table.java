package com.barefoot.bpositive.db;

import java.util.List;

import com.barefoot.bpositive.exceptions.RecordExistsException;

public interface Table<T> {
	
	public String getTableName();
	
	public T findById(long id);
	
	public List<T> findAll();
	
	public boolean exists(T element);

	public T create(T newElement) throws RecordExistsException;
	
}
