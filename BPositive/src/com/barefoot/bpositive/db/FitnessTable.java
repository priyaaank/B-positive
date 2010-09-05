package com.barefoot.bpositive.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.barefoot.bpositive.exceptions.RecordExistsException;
import com.barefoot.bpositive.models.Fitness;

public class FitnessTable implements Table<Fitness> {

	private static final String FITNESS_TABLE = "FITNESS";
	private static final String LOG_TAG = "FITNESS_TABLE";
	private SQLiteOpenHelper database; 
	
	public FitnessTable(SQLiteOpenHelper database) {
		this.database = database;
	}
	
	private static class FitnessCursor extends SQLiteCursor {

		private static final String ID_QUERY = "SELECT id, weight, weight_unit, blood_pressure, blood_pressure_unit, donor_id, creation_date FROM fitness WHERE id = ? ORDER BY creation_date desc";
		private static final String DONOR_QUERY = "SELECT id, weight, weight_unit, blood_pressure, blood_pressure_unit, donor_id, creation_date FROM fitness WHERE donor_id = ? ORDER BY creation_date desc";
		private static final String ALL_QUERY = "SELECT id, weight, weight_unit, blood_pressure, blood_pressure_unit, donor_id, creation_date FROM fitness ORDER BY creation_date desc";

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
	public Fitness create(Fitness newFitnessRecord) throws RecordExistsException {
		if (newFitnessRecord != null) {
			ContentValues dbValues = new ContentValues();
			dbValues.put("weight", newFitnessRecord.getWeight());
			dbValues.put("weight_unit", newFitnessRecord.getWeightUnit());
			dbValues.put("blood_pressure", newFitnessRecord.getBloodPressure());
			dbValues.put("blood_pressure_unit", newFitnessRecord.getBloodPressureUnit());
			dbValues.put("donor_id", newFitnessRecord.getDonorId());
			
			database.getWritableDatabase().beginTransaction();
			try {
				newFitnessRecord.setId(database.getWritableDatabase().insert(FITNESS_TABLE, "creation_date", dbValues));
				database.getWritableDatabase().setTransactionSuccessful();
			} catch (SQLException sqle) {
				Log.e(LOG_TAG, "Tried creating a new fitness record, could not create it for donor id "+newFitnessRecord.getDonorId()+". Error is :" + sqle.getMessage());
			} finally {
				database.getWritableDatabase().endTransaction();
			}
		}
		
		return newFitnessRecord;
	}

	@Override
	public boolean exists(Fitness element) {
		return false;
	}

	@Override
	public List<Fitness> findAll() {
		return find(FitnessCursor.ALL_QUERY, null);
	}

	@Override
	public Fitness findById(long id) {
		String fitnessIdString = Long.toString(id);
		return (find(FitnessCursor.ID_QUERY, new String[] {fitnessIdString})).get(0);
	}

	public List<Fitness> findAllByDonorId(long donorId) {
		String donorIdString = Long.toString(donorId);
		return find(FitnessCursor.DONOR_QUERY, new String[] {donorIdString});
	}
	
	@Override
	public String getTableName() {
		return FITNESS_TABLE;
	}
	
	protected List<Fitness> find(String query, String[] params) {
		List<Fitness> fitnessRecordsList = new ArrayList<Fitness>();
		FitnessCursor c = null;
		
		try {
			c = (FitnessCursor)database.getReadableDatabase().rawQueryWithFactory(new FitnessCursor.Factory(), query, params, null);
			if(c != null && c.moveToFirst()) {
				do {
					fitnessRecordsList.add(c.getFitness());
				} while(c.moveToNext());
			}
			
		} catch (SQLException sqle) {
			Log.e(LOG_TAG,"Tried fetching list of fitness records for query [" + query + "] and params ["+ params.toString() +"]. Error is "+ sqle.getMessage());
		}
		
		return fitnessRecordsList;
	}

}
