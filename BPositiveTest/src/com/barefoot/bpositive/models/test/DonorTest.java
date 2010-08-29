package com.barefoot.bpositive.models.test;

import junit.framework.TestCase;

import com.barefoot.bpositive.models.Donor;

public class DonorTest extends TestCase {
	
	private Donor testDonor;
	
	public void setUp() throws Exception {
		
	}
	
	public void testDonorObjectAttributesGetters() {
		testDonor = new Donor(-1, "First", "Last", "24/07/1982", "O+");
		assertNotNull(testDonor);
		assertEquals("First", testDonor.getFirstName());
		assertEquals("Last", testDonor.getLastName());
		assertEquals("24/07/1982", testDonor.getBirthDate());
		assertEquals("O+", testDonor.getBloodGroup());
		assertEquals("0", testDonor.getAge());
	}
	
	public void tearDown() throws Exception {
		
	}

}
