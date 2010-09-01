package com.barefoot.bpositive.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.barefoot.bpositive.R;
import com.barefoot.bpositive.exceptions.DonorExistsException;
import com.barefoot.bpositive.models.Donor;

public class BPositiveDatabase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "BPositive";
	private static final int DATABASE_VERSION = 1;
	private static final String LOG_TAG = null;
	private static final String DONOR_TABLE = "DONORS";
	private final Context mContext;
	
	public BPositiveDatabase(Context context, String name) {
		super(context, (name == null ? DATABASE_NAME : name), null, DATABASE_VERSION);
		mContext = context;
	}
	
	@Override
	public void finalize() {
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e(LOG_TAG, "Creating BPositive database for first time");
		String[] creationSqls = mContext.getString(R.string.create_sqls).split("\n");
		db.beginTransaction();
		try {
			executeManySqlStatements(db, creationSqls);
			db.setTransactionSuccessful();
		} catch(SQLException e) {
			Log.e(LOG_TAG, "Error occured while creating database. Error is " + e.getMessage());
		} finally {
			db.endTransaction();
		}
	}

	private void executeManySqlStatements(SQLiteDatabase db, String[] creationSqls) {
		for (String eachStatement : creationSqls) {
			if(eachStatement.trim().length() > 0)
				db.execSQL(eachStatement);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(LOG_TAG, "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		// recreate database from scratch once again.
		onCreate(db);
	}
	

	public Donor createNewDonor(Donor newDonor) throws DonorExistsException {
		if(newDonor != null) {
			if (donorExists(newDonor)) {
				throw new DonorExistsException("Donor ["+ newDonor.toString() + "] already exists in database");
			}
			
			try {
				ContentValues dbValues = new ContentValues();
				dbValues.put("first_name", newDonor.getFirstName());
				dbValues.put("last_name", newDonor.getLastName());
				dbValues.put("birth_date", newDonor.getBirthDate());
				dbValues.put("blood_group", newDonor.getBloodGroup());
				long id = getWritableDatabase().insertOrThrow(DONOR_TABLE, "creation_date", dbValues);
				newDonor.setId(id);
			} catch(SQLException sqle) {
				Log.e(LOG_TAG, "Could not create new donor. Exception is :" + sqle.getMessage());
			}
		}
		return newDonor;
	}

	private boolean donorExists(Donor newDonor) {
		if (newDonor != null) {
			Cursor c = null;
			String count_query = "Select count(*) from donors where first_name = ? and last_name = ? and blood_group = ? and birth_date = ?";
			try {
				c = getReadableDatabase().rawQuery(count_query, new String[]{newDonor.getFirstName(), 
																		 newDonor.getLastName(), 
																		 newDonor.getBloodGroup(),
																		 newDonor.getBirthDate()});
				if(c != null && c.moveToFirst() && c.getInt(0) > 0)
					return true;
			} catch(SQLException sqle) {
				Log.e(LOG_TAG, "Encountered error while fetching donor record existence. Error is :" + sqle.getMessage());
			} finally {
				if (c != null) {
					try {
						c.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		
		return false;
	}

	public static class DonorCursor extends SQLiteCursor {

		private static final String ALL_QUERY = "SELECT id, first_name, last_name, birth_date, blood_group, creation_date FROM donors ORDER BY creation_date desc";
		private static final String NAME_QUERY = "SELECT id, first_name, last_name, birth_date, blood_group, creation_date FROM donors WHERE first_name = ? and last_name = ? ORDER BY creation_date desc";
		private static final String ID_QUERY = "SELECT id, first_name, last_name, birth_date, blood_group, creation_date FROM donors WHERE id = ? ORDER BY creation_date desc";

		public DonorCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}
		
		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new DonorCursor(db, driver, editTable, query);
			}
		}
		
		private long getDonorId() {
			return getLong(getColumnIndexOrThrow("id"));
		}
		
		private String getFirstName() {
			return getString(getColumnIndexOrThrow("first_name"));
		}
		
		private String getLastName() {
			return getString(getColumnIndexOrThrow("last_name"));
		}
		
		private String getBirthDate() {
			return getString(getColumnIndexOrThrow("birth_date"));
		}
		
		private String getBloodGroup() {
			return getString(getColumnIndexOrThrow("blood_group"));
		}

		public Donor getDonor() {
			return new Donor( getDonorId(),
							  getFirstName(), 
							  getLastName(),
							  getBirthDate(),
							  getBloodGroup());
		}
	}

	public List<Donor> findDonorByName(String firstName, String lastName) {
		return findDonors(DonorCursor.NAME_QUERY, new String[] {firstName, lastName});
	}

	public List<Donor> findDonorById(long id) {
		String id_string = Long.toString(id);
		return findDonors(DonorCursor.ID_QUERY, new String[] {id_string});
	}

	public List<Donor> findAllDonors() {
		return findDonors(DonorCursor.ALL_QUERY, null);
	}
	
	protected List<Donor> findDonors(String query, String[] params) {
		Cursor donorCursor = null;
		List<Donor> donorList = new ArrayList<Donor>();
		try {
			donorCursor = getReadableDatabase().rawQueryWithFactory(new DonorCursor.Factory(), query, params, null);
			if(donorCursor != null && donorCursor.moveToFirst()) {
				do {
					donorList.add(((DonorCursor)donorCursor).getDonor());
				} while(donorCursor.moveToNext());
			}
		} catch(SQLException sqle) {
			Log.e(LOG_TAG, "Could not look up the donor with params "+ params +". The error is: "+ sqle.getMessage());
		}
		finally {
			if(!donorCursor.isClosed()) {
				donorCursor.close();
			}
		}
		return donorList;
	}
	
}
