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
import com.barefoot.bpositive.models.Donation;

public class DonationTable implements Table<Donation> {
	
	private static String TABLE_NAME = "DONATIONS";
	private SQLiteOpenHelper bPositiveDatabase;
	private static String LOG_TAG = "DonationTable";
	
	public DonationTable(SQLiteOpenHelper database) {
		bPositiveDatabase = database;
	}

	private static class DonationCursor extends SQLiteCursor {

		private static final String ALL_QUERY = "SELECT id, place, donation_date, type, organiser, donor_id, fitness_id, creation_date FROM donors ORDER BY creation_date desc";
		private static final String ID_QUERY = "SELECT id, place, donation_date, type, organiser, donor_id, fitness_id, creation_date FROM donations WHERE id = ? ORDER BY creation_date desc";
		private static final String DONOR_QUERY = "SELECT id, place, donation_date, type, organiser, donor_id, fitness_id, creation_date FROM donations WHERE donor_id = ? ORDER BY creation_date desc";

		public DonationCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new DonationCursor(db, driver, editTable, query);
			}
		}

		private long getId() {
			return getLong(getColumnIndexOrThrow("id"));
		}

		private String getPlace() {
			return getString(getColumnIndexOrThrow("place"));
		}

		private String getDate() {
			return getString(getColumnIndexOrThrow("donation_date"));
		}

		private String getOrganiser() {
			return getString(getColumnIndexOrThrow("organiser"));
		}

		private String getType() {
			return getString(getColumnIndexOrThrow("type"));
		}

		private long getDonorId() {
			return getLong(getColumnIndexOrThrow("donor_id"));
		}
		
		private long getFitnessId() {
			return getLong(getColumnIndexOrThrow("fitness_id"));
		}

		public Donation getDonation() {
			return new Donation(getId(), getDate(), getPlace(), getOrganiser(), getType(), getFitnessId(), getDonorId());
		}
	}
	
	protected List<Donation> findDonations(String query, String[] params) {
		Cursor donationCursor = null;
		List<Donation> donationsList = new ArrayList<Donation>();
		try {
			donationCursor = bPositiveDatabase.getReadableDatabase().rawQueryWithFactory(new DonationCursor.Factory(), query, params, null);
			if(donationCursor != null && donationCursor.moveToFirst()) {
				do {
					donationsList.add(((DonationCursor)donationCursor).getDonation());
				} while(donationCursor.moveToNext());
			}
		} catch(SQLException sqle) {
			Log.e(LOG_TAG, "Could not look up the donations with params "+ params +". The error is: "+ sqle.getMessage());
		}
		finally {
			if(!donationCursor.isClosed()) {
				donationCursor.close();
			}
		}
		return donationsList;
	}

	public List<Donation> findById(String firstName, String lastName) {
		return findDonations(DonationCursor.ID_QUERY, new String[] {firstName, lastName});
	}

	@Override
	public List<Donation> findAll() {
		return findDonations(DonationCursor.ALL_QUERY, null);
	}

	@Override
	public Donation findById(long id) {
		String id_string = Long.toString(id);
		List<Donation> donationList = findDonations(DonationCursor.ID_QUERY, new String[] {id_string});
		return (donationList == null || donationList.isEmpty()) ? null : donationList.get(0);
	}

	@Override
	public Donation create(Donation newDonation) throws RecordExistsException {
		if (newDonation != null) {
			ContentValues dbValues = new ContentValues();
			dbValues.put("place", newDonation.getPlace());
			dbValues.put("donation_date", newDonation.getDate());
			dbValues.put("type", newDonation.getType());
			dbValues.put("organiser", newDonation.getOrganiser());
			dbValues.put("donor_id", newDonation.getDonorId());
			dbValues.put("fitness_id", newDonation.getFitnessId());
			
			bPositiveDatabase.getWritableDatabase().beginTransaction();
			try {
				newDonation.setId(bPositiveDatabase.getWritableDatabase().insert(TABLE_NAME, "", dbValues));
				bPositiveDatabase.getWritableDatabase().setTransactionSuccessful();
			} catch (SQLException sqle) {
				Log.e(LOG_TAG, "Tried creating a new fitness record, could not create it for donor id "+newDonation.getDonorId()+". Error is :" + sqle.getMessage());
			} finally {
				bPositiveDatabase.getWritableDatabase().endTransaction();
			}
		}
		
		return newDonation;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean exists(Donation element) {
		return false;
	}

	public List<Donation> findAllByDonorId(long id) {
		String donorIdString = Long.toString(id);
		return findDonations(DonationCursor.DONOR_QUERY, new String[]{donorIdString});
	}
}
