package com.barefoot.bpositive.db.test;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.barefoot.bpositive.Dashboard;
import com.barefoot.bpositive.db.BPositiveDatabase;
import com.barefoot.bpositive.db.DonationTable;
import com.barefoot.bpositive.db.DonorTable;
import com.barefoot.bpositive.db.FitnessTable;
import com.barefoot.bpositive.db.Table;
import com.barefoot.bpositive.exceptions.RecordExistsException;
import com.barefoot.bpositive.models.Donation;
import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class DonationTableTest extends ActivityInstrumentationTestCase2<Dashboard> {
	private BPositiveDatabase testDBInstance;
	private SQLiteDatabase db;
	private Table<Donation> donationTable;
	private Table<Donor> donorTable;
	private Table<Fitness> fitnessTable;
	private Donor testDonor;
	private Fitness testFitness;
	
	public DonationTableTest() {
		super("com.barefoot.bpositive", Dashboard.class);
	}
	
	@Override
	public void setUp() throws Exception {
		testDBInstance = new BPositiveDatabase(getActivity(), "BPOSITIVE_TEST");
		donationTable = new DonationTable(testDBInstance);
		donorTable = new DonorTable(testDBInstance);
		fitnessTable = new FitnessTable(testDBInstance);
		db = testDBInstance.getWritableDatabase();
		
		testDonor = donorTable.create(new Donor(-1, "Jimmy","Hendrix","24-07-1982","B+",0));
		testFitness = fitnessTable.create(new Fitness(-1, "120/80","mmHg",28,"KG", testDonor.getId()));
	}
	
	public void testCreationOfNewDonationRecord() {
		Donation donationRecord = new Donation(-1, "12/12/2010", "Berlin", "Blood Bank","Donation", testFitness.getId(), testDonor.getId()); 
		
		try {
			donationRecord = donationTable.create(donationRecord);
		} catch (RecordExistsException e) {
			fail();
		}
		
		assertNotSame(-1, donationRecord.getId());
		assertEquals("Blood Bank", donationRecord.getOrganiser());
		assertEquals("Donation", donationRecord.getType());
		assertEquals("Berlin", donationRecord.getPlace());
		assertEquals(testFitness.getId(), donationRecord.getFitnessId());
		assertEquals(testDonor.getId(), donationRecord.getDonorId());
		assertEquals("12/12/2010", donationRecord.getDate());
	}

	public void testLookupOfDonationsWithDonorId() {
		Donation donationRecordOne = new Donation(-1, "12/12/2008", "Chicago", "Chicago Blood Bank","Donation", testFitness.getId(), testDonor.getId());
		Donation donationRecordTwo = new Donation(-1, "12/12/2010", "Berlin", "Berlin Blood Bank","Donation", testFitness.getId(), testDonor.getId());
		
		try {
			donationRecordOne = donationTable.create(donationRecordOne);
			donationRecordTwo = donationTable.create(donationRecordTwo);
		} catch (RecordExistsException e) {
			fail();
		}
		
		List<Donation> donationRecords = ((DonationTable)donationTable).findAllByDonorId(testDonor.getId());
		
		assertEquals(2, donationRecords.size());
		assertEquals(donationRecordTwo, donationRecords.get(1));
		assertEquals(donationRecordOne, donationRecords.get(0));
	}
	
	public void testLookupOfAllDonationRecords() {
		Donation donationRecordOne = new Donation(-1, "12/12/2008", "Chicago", "Chicago Blood Bank","Donation", testFitness.getId(), testDonor.getId());
		Donation donationRecordTwo = new Donation(-1, "12/12/2010", "Berlin", "Berlin Blood Bank","Donation", testFitness.getId(), testDonor.getId());
		
		try {
			donationRecordOne = donationTable.create(donationRecordOne);
			donationRecordTwo = donationTable.create(donationRecordTwo);
		} catch (RecordExistsException e) {
			fail();
		}
		
		List<Donation> donationRecords = donationTable.findAll();
		
		assertEquals(2, donationRecords.size());
		assertEquals(donationRecordTwo, donationRecords.get(1));
		assertEquals(donationRecordOne, donationRecords.get(0));
	}

	public void testLookupOfDonationRecordById() {
		Donation donationRecord = new Donation(-1, "12/12/2008", "Chicago", "Chicago Blood Bank","Donation", testFitness.getId(), testDonor.getId());
		
		try {
			donationRecord = donationTable.create(donationRecord);
		} catch (RecordExistsException e) {
			fail();
		}
		
		Donation fetchedDonationRecord = donationTable.findById(donationRecord.getId());
		
		assertEquals(donationRecord, fetchedDonationRecord);
	}
		
	@Override
	public void tearDown() throws Exception {
		dbCleanup("DELETE FROM DONATIONS;");
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
