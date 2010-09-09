package com.barefoot.bpositive;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.barefoot.bpositive.models.Donor;
import com.barefoot.bpositive.services.ProfileService;

public class Dashboard extends Activity {
	
	private ProfileService profileService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileService = new ProfileService(this);
        
        setContentView(R.layout.profile);
        updatePageLayout();
        displaySelectedDonorInfo(profileService.getPrimaryDonorProfile());
    }

	private void updatePageLayout() {
		TextView titleHeading = (TextView)findViewById(R.id.fitness_history).findViewById(R.id.title_bar_heading);
		titleHeading.setText("Fitness History");
		titleHeading = (TextView)findViewById(R.id.donation_history).findViewById(R.id.title_bar_heading);
		titleHeading.setText("Donation History");
	}
	
	private void displaySelectedDonorInfo(Donor donor) {
		if(donor != null) {
			TextView donorName = (TextView) findViewById(R.id.donor_name);
			TextView donorBirthDate = (TextView) findViewById(R.id.donor_birth_date);
			TextView donorBloodGroup = (TextView) findViewById(R.id.donor_blood_group);
			
			donorName.setText(donor.getFullName());
			donorBirthDate.setText(donor.getBirthDate());
			donorBloodGroup.setText(donor.getBloodGroup());
		}
	}
    
}