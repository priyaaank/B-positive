package com.barefoot.bpositive.services;

import java.util.List;

import android.content.Context;

import com.barefoot.bpositive.db.BPositiveDatabase;
import com.barefoot.bpositive.db.DonationTable;
import com.barefoot.bpositive.db.DonorTable;
import com.barefoot.bpositive.db.FitnessTable;
import com.barefoot.bpositive.models.Donation;
import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class ProfileService {
	
	private BPositiveDatabase database;
	private DonorTable donorTable;
	private DonationTable donationTable;
	private FitnessTable fitnessTable;
	
	public ProfileService(Context context) {
		database = new BPositiveDatabase(context, null);
		donorTable = new DonorTable(database);
		donationTable = new DonationTable(database);
		fitnessTable = new FitnessTable(database);
	}
	
	public Donor getPrimaryDonorProfile() {
		Donor primaryDonor = null;
		List<Fitness> fitnessRecords = null;
		List<Donation> donationRecords = null;
		primaryDonor = donorTable.findPrimary();
		if(primaryDonor != null) {
			fitnessRecords = fitnessTable.findAllByDonorId(primaryDonor.getId());
			donationRecords = donationTable.findAllByDonorId(primaryDonor.getId());
			primaryDonor.setFitnesses(fitnessRecords);
			primaryDonor.setDonations(donationRecords);
		}
		
		return primaryDonor;
	}

}
