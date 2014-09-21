package com.AMS.rockpaperscissor;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private RadioGroup radioSexGroup;
	private RadioButton radioSexButton;
	private Button btnSubmit;
	DBAdapter myDb;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		openDB();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeDB();
	}
	
	private void openDB()
	{
		myDb = new DBAdapter(this);
		myDb.open();
		displayText("DB Opened!!");
	}
	
	private void closeDB() {
		// TODO Auto-generated method stub
		myDb.close();
	}
	
	private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);
	}
	
	public void onSubmit(View view) 
	{
		
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		// get selected radio button from radioGroup
		int selectedId = radioSexGroup.getCheckedRadioButtonId();
		// find the radio button by returned id
	    radioSexButton = (RadioButton) findViewById(selectedId);
	    try
	    {
	    	long newid = myDb.insertRow("Sai",24 ,"male");
	   		//Toast.makeText(MainActivity.this,"!!", Toast.LENGTH_SHORT).show();

	   		Cursor cursor = myDb.getAllRows();
			displayRecordSet(cursor);
	    } catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	 
	}
	
	private void displayRecordSet(Cursor cursor) {
		String message = "";
		// populate the message from the cursor
		
		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				int id = cursor.getInt(DBAdapter.COL_ROWID);
				String name = cursor.getString(DBAdapter.COL_NAME);
				int age = cursor.getInt(DBAdapter.COL_AGE);
				String sex = cursor.getString(DBAdapter.COL_SEX);
				
				// Append data to the message:
				message += "id=" + id
						   +", name=" + name
						   +", age=" + age
						   +", sex=" + sex
						   +"\n";
			} while(cursor.moveToNext());
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
		
		displayText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
