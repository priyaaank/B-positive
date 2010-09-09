package com.barefoot.bpositive;

import android.app.Activity;
import android.os.Bundle;

public class Dashboard extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
//        
//        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, 1);
    }
    
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK)
//        {
//            Uri chosenImageUri = data.getData();
//
//            Bitmap mBitmap = null;
//            try {
//				mBitmap = Media.getBitmap(this.getContentResolver(), chosenImageUri);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//    }
}