package com.barefoot.bpositive.db;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import com.barefoot.bpositive.db.DonorTable.DonorCursor;
import com.barefoot.bpositive.exceptions.RecordExistsException;
import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class FitnessTable implements Table<Fitness> {

	private SQLiteOpenHelper database; 
	
	public FitnessTable(SQLiteOpenHelper database) {
		this.database = database;
	}
	
	private static class FitnessCursor extends SQLiteCursor {

		private static final String ID_QUERY = "SELECT id, weight, weight_unit, blood_pressure, blood_pressure_unit, donor_id, creation_date FROM fitness WHERE id = ? ORDER BY creation_date desc";
		private static final String DONOR_QUERY = "SELECT id, weight, weight_unit, blood_pressure, blood_pressure_unit, donor_id, creation_date FROM fitness WHERE donor_id = ? ORDER BY creation_date desc";

		public FitnessCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new FitnessCursor(db, driver, editTable, query);
			}
		}

		private long getFitnessId() {
			return getLong(getColumnIndexOrThrow("id"));
		}

		private int getWeight() {
			return getInt(getColumnIndexOrThrow("weight"));
		}

		private String getWeightUnit() {
			return getString(getColumnIndexOrThrow("weight_unit"));
		}

		private String getBloodPressure() {
			return getString(getColumnIndexOrThrow("blood_pressure"));
		}

		private String getBloodPressureUnit() {
			return getString(getColumnIndexOrThrow("blood_pressure_unit"));
		}

		private long getDonorId() {
			return getLong(getColumnIndexOrThrow("donor_id"));
		}

		public Fitness getFitness() {
			return new Fitness(getFitnessId(), getBloodPressure(), getBloodPressureUnit(),
					getWeight(), getWeightUnit(), getDonorId());
		}
	}
	
	@Override
	public Fitness create(Fitness newElement) throws RecordExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Fitness element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Fitness> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fitness findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
