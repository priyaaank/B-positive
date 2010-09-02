package com.barefoot.bpositive.models.test;

import junit.framework.TestCase;

import com.barefoot.bpositive.models.Fitness;

public class FitnessTest extends TestCase {
	
	public void testGetterAndSettersForFitness() {
		Fitness fitness = new Fitness(-1, "120/80","mmHg",80,"Kgs",12);
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

		assertEquals(11, fitness.getId());
		assertEquals(112, fitness.getDonorId());
		assertEquals(30, fitness.getWeight());
		assertEquals("30 Kgs", fitness.getWeightWithUnit());
		assertEquals("122/81", fitness.getBloodPressure());
		assertEquals("122/81 mmHg", fitness.getBloodPressureWithUnit());
	}

}
