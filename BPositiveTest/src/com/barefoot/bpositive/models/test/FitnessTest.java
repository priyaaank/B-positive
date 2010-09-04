package com.barefoot.bpositive.models.test;

import junit.framework.TestCase;

import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.models.Fitness;

public class FitnessTest extends TestCase {
	
	public void testGetterAndSettersForFitness() {
		Fitness fitness = new Fitness(-1, "120/80","mmHg",80,"Kgs",12);
		Donor donor = new Donor(12, "Jon", "Lame", "24/07/1982", "B+");
		assertEquals(80, fitness.getWeight());
		assertEquals("80 Kgs", fitness.getWeightWithUnit());
		assertEquals("120/80", fitness.getBloodPressure());
		assertEquals("120/80 mmHg", fitness.getBloodPressureWithUnit());
		assertEquals(-1, fitness.getId());
		assertEquals(12, fitness.getDonorId());
		
		fitness.setWeight(30);
		fitness.setBloodPressure("122/81");
		fitness.setId(11);
		fitness.setDonorId(112);
		fitness.setDonor(donor);

		assertEquals(11, fitness.getId());
		assertEquals(112, fitness.getDonorId());
		assertEquals(30, fitness.getWeight());
		assertEquals("30 Kgs", fitness.getWeightWithUnit());
		assertEquals("122/81", fitness.getBloodPressure());
		assertEquals("122/81 mmHg", fitness.getBloodPressureWithUnit());
		assertEquals(donor, fitness.getDonor());
	}
	
	public void testEqualizationOfFitness() {
		Fitness fitness = new Fitness(12, "120/80", "mmHg",30, "KG", 1);
		Fitness duplicateFitness = new Fitness(12, "120/80", "mmHg",30, "KG", 1);
		assertFalse(fitness.equals(new Donor(12, "Jon", "Canary", "03/07/1977", "B+")));
		assertTrue(fitness.equals(duplicateFitness));
		assertTrue(fitness.equals(fitness));
		duplicateFitness.setBloodPressure("112/70");
		assertFalse(fitness.equals(duplicateFitness));
	}

}
