package com.barefoot.bpositive.db.test;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.barefoot.bpositive.Dashboard;
import com.barefoot.bpositive.db.BPositiveDatabase;
import com.barefoot.bpositive.db.DonorTable;
import com.barefoot.bpositive.db.Table;
import com.barefoot.bpositive.exceptions.RecordExistsException;
import com.barefoot.bpositive.models.Donor;

public class DonorTableTest extends ActivityInstrumentationTestCase2<Dashboard> {

	private BPositiveDatabase testDBInstance;
	private SQLiteDatabase db;
	private Table<Donor> donorTable;
	
	public DonorTableTest() {
		super("com.barefoot.bpositive", Dashboard.class);
	}
	
	public void setUp() throws Exception {
		testDBInstance = new BPositiveDatabase(getActivity(), "BPOSITIVE_TEST");
		donorTable = new DonorTable(testDBInstance);
		db = testDBInstance.getWritableDatabase();
	}
	
	public void testCreationOfDonorProfile() {
		Donor newDonor = new Donor(-1, "John","Cusak","24-07-1982","O+",0);
		try {
			newDonor = donorTable.create(newDonor);
		} catch (RecordExistsException dee) {
			fail();
		}
		assertNotSame(-1, newDonor.getId());
	}
	
	public void testFailureForDuplicateProfile() {
		Donor duplicateDonor = new Donor(-1, "John","Cusak","24-07-1982","O+",0);
		try {
			donorTable.create(duplicateDonor);
		} catch(RecordExistsException dee) {
			//Do Nothing
		}
		
		duplicateDonor = new Donor(-1, "John","Cusak","24-07-1982","O+",0);
		try {
			donorTable.create(duplicateDonor);
			fail();
		} catch(RecordExistsException dee) {
			//If exception is thrown then this spec is successful
		}
	}
	
	public void testSelectionOfDonorByName() {
		Donor newDonor = new Donor(-1, "John","Cusak","24-07-1982","O+",0);
		try {
			donorTable.create(newDonor);
		} catch(Exception e) {
			fail();
		}
		
		Donor fetchedDonor = (((DonorTable)donorTable).findDonorByName(newDonor.getFirstName(), newDonor.getLastName())).get(0);
		assertEquals(newDonor.getFirstName(), fetchedDonor.getFirstName());
		assertEquals(newDonor.getLastName(), fetchedDonor.getLastName());
		assertEquals(newDonor.getAge(), fetchedDonor.getAge());
		assertEquals(newDonor.getBloodGroup(), fetchedDonor.getBloodGroup());
		assertEquals(newDonor.getBirthDate(), fetchedDonor.getBirthDate());
	}

	public void testSelectionOfDonorById() {
		Donor newDonor = new Donor(-1, "John","Cusak","24-07-1982","O+",0);
		long id = -1;
		try {
			id = (donorTable.create(newDonor)).getId();
		} catch(Exception e) {
			fail();
		}
		
		Donor fetchedDonor = (donorTable.findById(id));
		assertEquals(newDonor.getFirstName(), fetchedDonor.getFirstName());
		assertEquals(newDonor.getLastName(), fetchedDonor.getLastName());
		assertEquals(newDonor.getAge(), fetchedDonor.getAge());
		assertEquals(newDonor.getBloodGroup(), fetchedDonor.getBloodGroup());
		assertEquals(newDonor.getBirthDate(), fetchedDonor.getBirthDate());
		assertEquals(id, fetchedDonor.getId());
	}

	public void testSelectionOfAllDonors() {
		Donor newDonorOne = new Donor(-1, "John","Cusak","24-07-1982","O+",0);
		Donor newDonorTwo = new Donor(-1, "John","Gosling","24-07-1981","B+",0);
		try {
			newDonorOne = donorTable.create(newDonorOne);
			newDonorTwo = donorTable.create(newDonorTwo);
		} catch(Exception e) {
			fail();
		}
		
		List<Donor> fetchedDonorList = donorTable.findAll();
		assertEquals(2, fetchedDonorList.size());
		assertEquals(newDonorOne, fetchedDonorList.get(0));
		assertEquals(newDonorTwo, fetchedDonorList.get(1));
	}
	
	public void testThatTwoPrimaryDonorsCannotBeCreated() {
		Donor realPrimaryDonor = new Donor(-1, "John","Cusak","24-07-1982","O+",1);
		Donor fakePrimaryDonor = new Donor(-1, "James","Dean","24-07-1981","B+",1);
		try {
			realPrimaryDonor = donorTable.create(realPrimaryDonor);
			fakePrimaryDonor = donorTable.create(fakePrimaryDonor);
			fail();
		} catch(RecordExistsException e) {
			//Pass
		}
		
		assertNotSame(-1, realPrimaryDonor.getId());
		assertEquals(-1, fakePrimaryDonor.getId());
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
