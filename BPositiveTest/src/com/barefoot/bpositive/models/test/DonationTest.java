package com.barefoot.bpositive.models.test;

import junit.framework.TestCase;

import com.barefoot.bpositive.models.Donation;
import com.barefoot.bpositive.models.Fitness;

public class DonationTest extends TestCase {
	
	public void testGetterSetterForDonations() {
		Fitness donationFitness = new Fitness(-1, "120/80", "mmHg", 30, "KG", 12);
		Fitness donationFitnessNew = new Fitness(-1, "122/80", "mmHg", 80, "KG", 11);
		
		Donation donorDonation = new Donation(-1, "24/12/2010", "Bangalore", "Bludy Blood Bank", "Donation", donationFitness);
		assertEquals(donorDonation.getId(), -1);
		assertEquals(donorDonation.getDate(), "24/12/2010");
		assertEquals(donorDonation.getPlace(), "Bangalore");
		assertEquals(donorDonation.getOrganiser(), "Bludy Blood Bank");
		assertEquals(donorDonation.getType(), "Donation");
		assertEquals(donorDonation.getFitness(), donationFitness);
		donorDonation.setId(12);
		donorDonation.setDate("27/08/2010");
		donorDonation.setPlace("Pune");
		donorDonation.setType("Donation");
		donorDonation.setOrganiser("UnBludy Blood Bank");
		donorDonation.setFitness(donationFitnessNew);
		assertEquals(donorDonation.getId(), 12);
		assertEquals(donorDonation.getDate(), "27/08/2010");
		assertEquals(donorDonation.getPlace(), "Pune");
		assertEquals(donorDonation.getOrganiser(), "UnBludy Blood Bank");
		assertEquals(donorDonation.getType(), "Donation");
		assertEquals(donorDonation.getFitness(), donationFitnessNew);
	}

}
