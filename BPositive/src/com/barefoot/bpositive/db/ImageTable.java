package com.barefoot.bpositive.db;

import java.util.List;

import com.barefoot.bpositive.exceptions.RecordExistsException;
import com.barefoot.bpositive.models.Image;

public class ImageTable implements Table<Image>{

	@Override
	public Image create(Image newElement) throws RecordExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Image element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Image> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
