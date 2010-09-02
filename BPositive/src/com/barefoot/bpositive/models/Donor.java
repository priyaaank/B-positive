package com.barefoot.bpositive.models;


public class Donor {
	
	private String firstName;
	private String lastName;
	private String birthDate;
	private String bloodGroup;
	private long id;

	public Donor(long id, String firstName, String lastName, String birthDate, String bloodGroup) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.bloodGroup = bloodGroup;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public String getBirthDate() {
		return this.birthDate;
	}
	public String getBloodGroup() {
		return this.bloodGroup;
	}
	
	public int getAge()  {
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder donor = new StringBuilder("Donor First Name:");
		donor.append(getFirstName());
		donor.append(" ");
		donor.append("Last Name:");
		donor.append(getLastName());
		donor.append(" ");
		donor.append("Birth Date:");
		donor.append(getBirthDate());
		donor.append(" ");
		donor.append("Blood Group");
		donor.append(getBloodGroup());
		return donor.toString();
	}

	@Override
	public boolean equals(Object objectToCompare) {
		if(!(objectToCompare instanceof Donor)) {
			return false;
		}
		Donor object = (Donor)objectToCompare;
		return this.getBirthDate().equals(object.getBirthDate()) &&
			   this.getBloodGroup().equals(object.getBloodGroup()) &&
			   this.getFirstName().equals(getFirstName()) &&
			   this.getLastName().equals(object.getLastName()) &&
			   this.getAge() == object.getAge() &&
			   this.getId() == object.getId();
	}

}
