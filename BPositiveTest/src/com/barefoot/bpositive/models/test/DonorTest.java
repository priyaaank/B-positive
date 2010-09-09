package com.barefoot.bpositive.models.test;

import junit.framework.TestCase;

import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class DonorTest extends TestCase {
	
	private Donor testDonor;
	
	public void testDonorObjectAttributesGettersSetters() {
		testDonor = new Donor(-1, "First", "Last", "24/07/1982", "O+", 0);
		assertNotNull(testDonor);
		assertEquals("First", testDonor.getFirstName());
		assertEquals("Last", testDonor.getLastName());
		assertEquals("24/07/1982", testDonor.getBirthDate());
		assertEquals("O+", testDonor.getBloodGroup());
		assertEquals(0, testDonor.getAge());
		assertEquals(false, testDonor.isPrimary());
		testDonor.setId(12);
		assertEquals(12, testDonor.getId());
		testDonor.setPrimary(true);
		assertEquals(true, testDonor.isPrimary());
		testDonor.setPrimary(0);
		assertEquals(false, testDonor.isPrimary());
	}
	
	public void testEqualizationOfDonor() {
		Donor donor = new Donor(12, "Jon", "Doe", "12/12/2010", "B+", 1);
		Donor duplicateDonor = new Donor(12, "Jon", "Doe", "12/12/2010", "B+", 1);
		assertFalse(donor.equals(new Fitness(12, "120/18", "mmHg", 20, "KG", 1)));
		assertTrue(donor.equals(duplicateDonor));
		assertTrue(donor.equals(donor));
		duplicateDonor.setId(122);
		assertFalse(donor.equals(duplicateDonor));
	}

}
