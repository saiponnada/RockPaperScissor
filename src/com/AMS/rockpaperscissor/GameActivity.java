package com.AMS.rockpaperscissor;

import java.util.Random;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity {
	
	DBAdapter myDb;
	String userName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Intent intent = getIntent();
		userName = intent.getStringExtra(MainActivity.NAME).toString();
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
	}
	
	private void closeDB() {
		// TODO Auto-generated method stub
		myDb.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
	
	public void onClickRock(View view) 
	{
		String result = game(1);
		displayResult(result);
	}
	
	public void onClickPaper(View view) 
	{
		String result = game(2);
		displayResult(result);
	}
	
	public void onClickScissors(View view) 
	{
		String result = game(3);
		displayResult(result);
	}
	
	public String game(int userSelection){
		String resultMessage="";
		Random rand = new Random();
		int randomNum = rand.nextInt(3) + 1;
		String status;
		if(userSelection == 1){
			resultMessage += "Your Selection: Rock | ";
			if(randomNum == 2)
			{
				resultMessage += "Computer Selection: Paper | YOU LOSE! TRY AGAIN!";
				status ="loss";
			}
			else if(randomNum == 1)
			{
				resultMessage += "Computer Selection: Rock | DRAW! TRY AGAIN!";
				status = "win";
			}
			else 
			{
				resultMessage += "Computer Selection: Scissors | YOU WIN! CONGRATULATIONS!";
				status = "draw";
			}		
		}
		else if(userSelection == 2){
			resultMessage += "Your Selection: Paper | ";
			if(randomNum == 3) 
			{
				resultMessage += "Computer Selection: Rock | YOU WIN! CONGRATULATIONS!";
				status = "win";
			}
			else if(randomNum == 2)
			{
				resultMessage += "Computer Selection: Paper | DRAW! TRY AGAIN!";
				status = "draw";
			}
			else 
			{
				resultMessage += "Computer Selection: Scissors | YOU LOSE! TRY AGAIN!";
				status ="loss";
			}
		}
		else {
			resultMessage += "Your Selection: Scissors | ";
			if(randomNum == 1)
			{
				resultMessage += "Computer Selection: Paper | YOU WIN! CONGRATULATIONS!";
				status = "win";
			}
			else if(randomNum == 3) 
			{
				resultMessage += "Computer Selection: Scissors | DRAW! TRY AGAIN!";
				status = "draw";
			}
			else 
			{
				resultMessage += "Computer Selection: Rock | YOU LOSE! TRY AGAIN!";
				status ="loss";
			}
		}
		try
		{
			myDb.updateScore(userName, status);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultMessage;
	}
	
	private void displayResult(String message) {
        TextView textView = (TextView) findViewById(R.id.textResult);
        textView.setText(message);
	}
	
}
