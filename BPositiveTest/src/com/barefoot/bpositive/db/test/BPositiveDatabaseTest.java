package com.barefoot.bpositive.db.test;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.barefoot.bpositive.Dashboard;
import com.barefoot.bpositive.db.BPositiveDatabase;
import com.barefoot.bpositive.exceptions.DonorExistsException;
import com.barefoot.bpositive.models.Donor;

public class BPositiveDatabaseTest extends ActivityInstrumentationTestCase2<Dashboard> {

	private BPositiveDatabase testDBInstance;
	private SQLiteDatabase db;
	
	public BPositiveDatabaseTest() {
		super("com.barefoot.bpositive", Dashboard.class);
	}
	
	public void setUp() throws Exception {
		testDBInstance = new BPositiveDatabase(getActivity(), "BPOSITIVE_TEST");
		db = testDBInstance.getWritableDatabase(); 
	}
	
	public void testDatabaseCreatedSuccessfully() {
		assertNotNull(db);
	}
	
	public void testCreationOfDonorProfile() {
		Donor newDonor = new Donor(-1, "John","Cusak","24-07-1982","O+");
		try {
			newDonor = testDBInstance.createNewDonor(newDonor);
		} catch (DonorExistsException dee) {
			fail();
		}
		assertNotSame(-1, newDonor.getId());
	}
	
	public void testFailureForDuplicateProfile() {
		Donor duplicateDonor = new Donor(-1, "John","Cusak","24-07-1982","O+");
		try {
			testDBInstance.createNewDonor(duplicateDonor);
		} catch(DonorExistsException dee) {
			//Do Nothing
		}
		
		duplicateDonor = new Donor(-1, "John","Cusak","24-07-1982","O+");
		try {
			testDBInstance.createNewDonor(duplicateDonor);
			fail();
		} catch(DonorExistsException dee) {
			//If exception is thrown then this spec is successful
		}
	}
	
	public void tearDown() throws Exception {
		dbCleanup("DELETE FROM DONORS;");
		
		try {
			//to compress the unused space and clean pages left after cleaning tables
			db.execSQL("VACUUM;");
		} catch(SQLException sqle) {
			fail();
			sqle.printStackTrace();
		} finally {
			testDBInstance.close();
		}
		
		getActivity().finish();
		super.tearDown();
	}
	
	//Since android doesn't provide db transaction methods, we'll clean up db ourselves
	//This is dependent on real code which it shouldn't be but for now I think it's ok.
	private void dbCleanup(final String sqlStatement) {
		db.beginTransaction();
		try {
			db.execSQL(sqlStatement);
			db.setTransactionSuccessful();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			fail();
		}finally {
			db.endTransaction();
		}
	}
}