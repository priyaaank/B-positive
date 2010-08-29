package com.barefoot.bpositive.db.test;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.barefoot.bpositive.Dashboard;
import com.barefoot.bpositive.db.BPositiveDatabase;

public class BPositiveDatabaseTest extends ActivityInstrumentationTestCase2<Dashboard> {

	private BPositiveDatabase testDBInstance;

	public BPositiveDatabaseTest() {
		super("com.barefoot.bpositive", Dashboard.class);
	}
	
	public void setUp() throws Exception {
		testDBInstance = new BPositiveDatabase(getActivity(), "BPOSITIVE_TEST");
	}
	
	public void testDatabaseCreatedSuccessfully() {
		SQLiteDatabase db = testDBInstance.getReadableDatabase();
		assertNotNull(db);
	}
	
	public void tearDown() throws Exception {
		clearDatabase();
		getActivity().finish();
		super.tearDown();
	}
	
	//Since android doesn't provide db transaction methods, we'll clean up db ourselves
	//This is dependent on real code which it shouldn't be but for now I think it's ok.
	private void clearDatabase() {
		final String cleanup_sqls = "DROP TABLE IF EXISTS DONORS;";
		
		SQLiteDatabase db = testDBInstance.getWritableDatabase();
		db.beginTransaction();
		try {
			db.execSQL(cleanup_sqls);
			db.setTransactionSuccessful();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			fail();
		}finally {
			db.endTransaction();
			//to compress the unused space and clean pages left after dropping tables
			db.execSQL("VACUUM;");
			db.close();
		}
	}
}