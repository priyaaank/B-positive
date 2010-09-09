package com.barefoot.bpositive.models.test;

import junit.framework.TestCase;

import com.barefoot.bpositive.models.Donation;
import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class DonationTest extends TestCase {
	
	public void testGetterSetterForDonations() {
		Fitness donationFitness = new Fitness(-1, "122/80", "mmHg", 80, "KG", 11);
		
		Donation donorDonation = new Donation(-1, "24/12/2010", "Bangalore", "Bludy Blood Bank", "Donation", 12, 11);
		assertEquals(donorDonation.getId(), -1);
		assertEquals(donorDonation.getDate(), "24/12/2010");
		assertEquals(donorDonation.getPlace(), "Bangalore");
		assertEquals(donorDonation.getOrganiser(), "Bludy Blood Bank");
		assertEquals(donorDonation.getType(), "Donation");
		assertEquals(donorDonation.getFitnessId(), 12);
		assertEquals(donorDonation.getDonorId(), 11);
		assertNull(donorDonation.getFitness());
		assertNull(donorDonation.getDonor());
		donorDonation.setId(12);
		donorDonation.setFitnessId(121);
		donorDonation.setDonorId(112);
		donorDonation.setDate("27/08/2010");
		donorDonation.setPlace("Pune");
		donorDonation.setType("Donation");
		donorDonation.setOrganiser("UnBludy Blood Bank");
		donorDonation.setFitness(donationFitness);
		assertEquals(donorDonation.getId(), 12);
		assertEquals(donorDonation.getDonorId(), 112);
		assertEquals(donorDonation.getFitnessId(), 121);
		assertEquals(donorDonation.getDate(), "27/08/2010");
		assertEquals(donorDonation.getPlace(), "Pune");
		assertEquals(donorDonation.getOrganiser(), "UnBludy Blood Bank");
		assertEquals(donorDonation.getType(), "Donation");
		assertEquals(donorDonation.getFitness(), donationFitness);
	}
	
	public void testEqualizationOfDonation() {
		Donation donation = new Donation(12, "12/12/2010", "Chicago", "Blood Bank", "Donation", 12, 11);
		Donation duplicateDonation = new Donation(12, "12/12/2010", "Chicago", "Blood Bank", "Donation", 12, 11);
		assertFalse(donation.equals(new Donor(12, "Jon", "Canary", "03/07/1977", "B+",0)));
		assertTrue(donation.equals(duplicateDonation));
		assertTrue(donation.equals(donation));
		duplicateDonation.setOrganiser("random");
		assertFalse(donation.equals(duplicateDonation));
	}
}
