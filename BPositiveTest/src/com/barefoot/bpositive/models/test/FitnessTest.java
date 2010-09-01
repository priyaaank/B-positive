package com.barefoot.bpositive.models.test;

import com.barefoot.bpositive.models.Fitness;

import junit.framework.TestCase;

public class FitnessTest extends TestCase {
	
	public void testGetterAndSettersForFitness() {
		Fitness fitness = new Fitness();
		assertEquals(-1, fitness.getWeight());
		fitness.setWeight(30);
		assertEquals(30, fitness.getWeight());
		assertEquals("30 KGs", fitness.getWeightWithUnit());

		assertEquals("-1", fitness.getBloodPressure());
		fitness.setBloodPressure("120/80");
		assertEquals("120/80", fitness.getBloodPressure());
		assertEquals("120/80 mmHg", fitness.getBloodPressureWithUnit());
	}

}
