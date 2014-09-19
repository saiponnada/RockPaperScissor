package com.AMS.rockpaperscissor;

//import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private RadioGroup radioSexGroup;
	private RadioButton radioSexButton;
	private Button btnSubmit;
	SQLiteDatabase db;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		 
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
			    // get selected radio button from radioGroup
				int selectedId = radioSexGroup.getCheckedRadioButtonId();
				// find the radio button by returned id
			    radioSexButton = (RadioButton) findViewById(selectedId);
				try
				{
					db = SQLiteDatabase.openOrCreateDatabase("/storage/emulated/0/RPS/myRPSDb", null);
					db.beginTransaction();
					try
					{
						db.execSQL("create table tblRps ("
								+ "ID integer PRIMARY KEY autoincrement,"
								+ "username text,"
								+ "age integer,"
								+ "sex text); ");
						db.setTransactionSuccessful();
					}
					catch(Exception ex)
					{
						System.out.println("Exception at inner try" +ex);
					}
					Toast.makeText(MainActivity.this,"successful!!", Toast.LENGTH_SHORT).show();
				}
				catch(Exception ex)
				{
					System.out.println("Exception at outer try" +ex);
				}
			}
	 
		});
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
