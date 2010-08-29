package com.barefoot.bpositive.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.barefoot.bpositive.R;

public class BPositiveDatabase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "BPositive";
	private static final int DATABASE_VERSION = 1;
	private static final String LOG_TAG = null;
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

}
