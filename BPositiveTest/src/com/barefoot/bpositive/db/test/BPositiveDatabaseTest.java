package com.barefoot.bpositive.db.test;

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
		getActivity().finish();
		super.tearDown();
	}

}