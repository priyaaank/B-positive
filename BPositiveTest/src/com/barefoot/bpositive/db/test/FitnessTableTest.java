package com.barefoot.bpositive.db.test;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.barefoot.bpositive.Dashboard;
import com.barefoot.bpositive.db.BPositiveDatabase;
import com.barefoot.bpositive.db.DonorTable;
import com.barefoot.bpositive.db.FitnessTable;
import com.barefoot.bpositive.db.Table;
import com.barefoot.bpositive.exceptions.RecordExistsException;
import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class FitnessTableTest extends ActivityInstrumentationTestCase2<Dashboard> {
	private BPositiveDatabase testDBInstance;
	private SQLiteDatabase db;
	private Table<Fitness> fitnessTable;
	private Table<Donor> donorTable;
	private Donor testDonor;
	
	public FitnessTableTest() {
		super("com.barefoot.bpositive", Dashboard.class);
	}
	
	public void setUp() throws Exception {
		testDBInstance = new BPositiveDatabase(getActivity(), "BPOSITIVE_TEST");
		fitnessTable = new FitnessTable(testDBInstance);
		donorTable = new DonorTable(testDBInstance);
		db = testDBInstance.getWritableDatabase();
		
		testDonor = donorTable.create(new Donor(-1, "Jimmy","Hendrix","24-07-1982","B+"));
	}
	
	public void testCreationOfNewFitnessRecord() {
		Fitness fitnessRecord = new Fitness(-1, "120/80", "mmHg", 30, "KG", testDonor.getId());
		
		try {
			fitnessRecord = fitnessTable.create(fitnessRecord);
		} catch (RecordExistsException e) {
			fail();
		}
		
		assertNotSame(-1, fitnessRecord.getId());
		assertEquals("120/80 mmHg", fitnessRecord.getBloodPressureWithUnit());
		assertEquals("30 KG", fitnessRecord.getWeightWithUnit());
		assertEquals(testDonor.getId(), fitnessRecord.getDonorId());
	}

	public void testLookupOfFitnessWithDonorId() {
		Fitness fitnessRecordOne = new Fitness(-1, "120/80", "mmHg", 30, "KG", testDonor.getId());
		Fitness fitnessRecordTwo = new Fitness(-1, "122/82", "mmHg", 91, "Pounds", testDonor.getId());
		
		try {
			fitnessRecordOne = fitnessTable.create(fitnessRecordOne);
			fitnessRecordTwo = fitnessTable.create(fitnessRecordTwo);
		} catch (RecordExistsException e) {
			fail();
		}
		
		List<Fitness> fitnessRecords = ((FitnessTable)fitnessTable).findAllByDonorId(testDonor.getId());
		
		assertEquals(2, fitnessRecords.size());
		assertEquals(fitnessRecordTwo, fitnessRecords.get(1));
		assertEquals(fitnessRecordOne, fitnessRecords.get(0));
	}
	
	public void testLookupOfAllFitnessRecords() {
		Fitness fitnessRecordOne = new Fitness(-1, "120/80", "mmHg", 30, "KG", testDonor.getId());
		Fitness fitnessRecordTwo = new Fitness(-1, "122/82", "mmHg", 91, "Pounds", testDonor.getId());
		
		try {
			fitnessRecordOne = fitnessTable.create(fitnessRecordOne);
			fitnessRecordTwo = fitnessTable.create(fitnessRecordTwo);
		} catch (RecordExistsException e) {
			fail();
		}
		
		List<Fitness> fitnessRecords = fitnessTable.findAll();
		
		assertEquals(2, fitnessRecords.size());
		assertEquals(fitnessRecordTwo, fitnessRecords.get(1));
		assertEquals(fitnessRecordOne, fitnessRecords.get(0));
	}

	public void testLookupOfFitnessRecordById() {
		Fitness fitnessRecord = new Fitness(-1, "120/80", "mmHg", 30, "KG", testDonor.getId());
		
		try {
			fitnessRecord = fitnessTable.create(fitnessRecord);
		} catch (RecordExistsException e) {
			fail();
		}
		
		Fitness fetchedFitnessRecord = fitnessTable.findById(fitnessRecord.getId());
		
		assertEquals(fitnessRecord, fetchedFitnessRecord);
	}
		
	public void tearDown() throws Exception {
		dbCleanup("DELETE FROM FITNESS;");
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
