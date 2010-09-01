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
import com.barefoot.bpositive.models.Donor;

public class DonorTable implements Table<Donor> {
	
	private static String TABLE_NAME = "DONORS";
	private SQLiteOpenHelper bPositiveDatabase;
	private static String LOG_TAG = "DonorTable";
	
	public DonorTable(SQLiteOpenHelper database) {
		bPositiveDatabase = database;
	}

	private static class DonorCursor extends SQLiteCursor {

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
			return new Donor(getDonorId(), getFirstName(), getLastName(),
					getBirthDate(), getBloodGroup());
		}
	}

	@Override
	public boolean exists(Donor donor) {
		if (donor != null) {
			Cursor c = null;
			String count_query = "Select count(*) from donors where first_name = ? and last_name = ? and blood_group = ? and birth_date = ?";
			try {
				c = bPositiveDatabase.getReadableDatabase().rawQuery(
						count_query,
						new String[] { donor.getFirstName(),
								donor.getLastName(),
								donor.getBloodGroup(),
								donor.getBirthDate() });
				if (c != null && c.moveToFirst() && c.getInt(0) > 0)
					return true;
			} catch (SQLException sqle) {
				Log.e(LOG_TAG,
						"Encountered error while fetching donor record existence. Error is :"
								+ sqle.getMessage());
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
	
	protected List<Donor> findDonors(String query, String[] params) {
		Cursor donorCursor = null;
		List<Donor> donorList = new ArrayList<Donor>();
		try {
			donorCursor = bPositiveDatabase.getReadableDatabase().rawQueryWithFactory(new DonorCursor.Factory(), query, params, null);
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

	public List<Donor> findDonorByName(String firstName, String lastName) {
		return findDonors(DonorCursor.NAME_QUERY, new String[] {firstName, lastName});
	}

	@Override
	public List<Donor> findAll() {
		return findDonors(DonorCursor.ALL_QUERY, null);
	}

	@Override
	public Donor findById(long id) {
		String id_string = Long.toString(id);
		List<Donor> donorList = findDonors(DonorCursor.ID_QUERY, new String[] {id_string});
		return (donorList == null || donorList.isEmpty()) ? null : donorList.get(0);
	}

	@Override
	public Donor create(Donor newDonor) throws RecordExistsException {
		if (newDonor != null) {
			if (exists(newDonor)) {
				throw new RecordExistsException("Donor [" + newDonor.toString()
						+ "] already exists in database");
			}

			try {
				ContentValues dbValues = new ContentValues();
				dbValues.put("first_name", newDonor.getFirstName());
				dbValues.put("last_name", newDonor.getLastName());
				dbValues.put("birth_date", newDonor.getBirthDate());
				dbValues.put("blood_group", newDonor.getBloodGroup());
				long id = bPositiveDatabase.getWritableDatabase().insertOrThrow(getTableName(),
						"creation_date", dbValues);
				newDonor.setId(id);
			} catch (SQLException sqle) {
				Log.e(LOG_TAG, "Could not create new donor. Exception is :"
						+ sqle.getMessage());
			}
		}
		return newDonor;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

}
