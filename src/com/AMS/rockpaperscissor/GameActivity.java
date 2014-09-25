package com.AMS.rockpaperscissor;

import java.util.Random;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity implements 
GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	DBAdapter myDb;
	static String userName;
	
	private static final String DEBUG_TAG = "Gestures";
	private GestureDetectorCompat mDetector; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.GameContainer);
		relativeLayout.setBackgroundColor(Color.parseColor("#fff6df"));
		relativeLayout.invalidate();
		Intent intent = getIntent();
		userName = intent.getStringExtra(MainActivity.NAME).toString();
		openDB();
		mDetector = new GestureDetectorCompat(this,this);
		mDetector.setOnDoubleTapListener(this);
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
	public boolean onTouchEvent(MotionEvent event){ 
	this.mDetector.onTouchEvent(event);
	// Be sure to call the superclass implementation
	return super.onTouchEvent(event);
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
	
	/*public void onClickRock(View view) 
	{
		String result = game(1);
		//displayResult(result);
	}
	
	public void onClickPaper(View view) 
	{
		String result = game(2);
		//displayResult(result);
	}
	
	public void onClickScissors(View view) 
	{
		String result = game(3);
		//displayResult(result);
	}*/
	
	public String game(int userSelection){
		String resultMessage="";
		Random rand = new Random();
		int randomNum = rand.nextInt(3) + 1;
		String status;
		displayUserImage(userSelection);
		displayComputerImage(randomNum);
		if(userSelection == 1){
			//resultMessage += userName + ": Rock \t ";
			if(randomNum == 2)
			{
				resultMessage += "YOU LOSE! TRY AGAIN!";
				status ="loss";
			}
			else if(randomNum == 1)
			{
				resultMessage += "DRAW! TRY AGAIN!";
				status = "draw";
			}
			else 
			{
				resultMessage += "YOU WIN! CONGRATULATIONS!";
				status = "win";
			}		
		}
		else if(userSelection == 2){
			//resultMessage += userName + ": Paper \t ";
			if(randomNum == 1) 
			{
				resultMessage += "YOU WIN! CONGRATULATIONS!";
				status = "win";
			}
			else if(randomNum == 2)
			{
				resultMessage += "DRAW! TRY AGAIN!";
				status = "draw";
			}
			else 
			{
				resultMessage += "YOU LOSE! TRY AGAIN!";
				status ="loss";
			}
		}
		else {
			//resultMessage += userName + ": Scissors \t ";
			if(randomNum == 2)
			{
				resultMessage += "YOU WIN! CONGRATULATIONS!";
				status = "win";
			}
			else if(randomNum == 3) 
			{
				resultMessage += "DRAW! TRY AGAIN!";
				status = "draw";
			}
			else 
			{
				resultMessage += "YOU LOSE! TRY AGAIN!";
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
		final Dialog dialog = new Dialog(this);
		dialog.setTitle(resultMessage);
		dialog.show();
				
		return resultMessage;
	}
	
	private void displayUserImage(int m) {
        TextView textView = (TextView) findViewById(R.id.textUser);
        textView.setText(userName);
		if(m==1){
		ImageView userImageView = (ImageView) findViewById(R.id.imageUser);
		Drawable d = getResources().getDrawable(R.drawable.rock);
		userImageView.setImageDrawable(d);
		}
		else if(m==2){
			ImageView userImageView = (ImageView) findViewById(R.id.imageUser);
			Drawable d = getResources().getDrawable(R.drawable.paper);
			userImageView.setImageDrawable(d);
		}
		else if(m==3){
			ImageView userImageView = (ImageView) findViewById(R.id.imageUser);
			Drawable d = getResources().getDrawable(R.drawable.scissors);
			userImageView.setImageDrawable(d);
		}
	}
	
	private void displayComputerImage(int m) {
        TextView textView = (TextView) findViewById(R.id.textComputer);
        textView.setText("Computer: ");
		if(m==1){
		ImageView userImageView = (ImageView) findViewById(R.id.imageComputer);
		Drawable d = getResources().getDrawable(R.drawable.rock);
		userImageView.setImageDrawable(d);
		}
		else if(m==2){
			ImageView userImageView = (ImageView) findViewById(R.id.imageComputer);
			Drawable d = getResources().getDrawable(R.drawable.paper);
			userImageView.setImageDrawable(d);
		}
		else if(m==3){
			ImageView userImageView = (ImageView) findViewById(R.id.imageComputer);
			Drawable d = getResources().getDrawable(R.drawable.scissors);
			userImageView.setImageDrawable(d);
		}
	}
	
	@Override
	public boolean onDown(MotionEvent event) { 
	//Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
	return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		String result = game(3);
		//displayResult(result);
		displayFinalResults();
	return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
	//Log.d(DEBUG_TAG, "onLongPress: " + event.toString()); 
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	    float distanceY) {
	//Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
	return true;
	}

	@Override
	public void onShowPress(MotionEvent event) {
	//Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		
	return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
		String result = game(2);
		//displayResult(result);
		displayFinalResults();
	return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
	//Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
	return true;
	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		String result = game(1);
		//displayResult(result);
		displayFinalResults();
	return true;
	}
	
	public void displayFinalResults(){
		Cursor cursor = myDb.getByName(userName);
		int wins = cursor.getInt(DBAdapter.COL_WIN);
		int loss = cursor.getInt(DBAdapter.COL_LOSS);
		int draws = cursor.getInt(DBAdapter.COL_DRAW);
		TextView textViewWins = (TextView) findViewById(R.id.textWins);
		textViewWins.setTextColor(Color.GREEN);
        textViewWins.setText("Wins \n"+wins);
        TextView textViewLoss = (TextView) findViewById(R.id.textLoss);
        textViewLoss.setTextColor(Color.RED);
        textViewLoss.setText("Loss \n"+loss);
        TextView textViewDraws = (TextView) findViewById(R.id.textDraws);
        textViewDraws.setTextColor(Color.BLUE);
        textViewDraws.setText("Draws \n"+draws);
	}
	
	public void onExit(View view)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
}
