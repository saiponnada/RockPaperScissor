package com.AMS.rockpaperscissor;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBAdapter {


	private static final String TAG = "DBAdapter";
	public static final String FILE_DIR ="comrps";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final int SCORE =0;

	public static final String KEY_NAME = "username";
	public static final String KEY_AGE = "age";
	public static final String KEY_SEX = "sex";
	public static final String KEY_WIN = "win";
	public static final String KEY_LOSS = "loss";
	public static final String KEY_DRAW = "draw";
	
	public static final int COL_NAME = 1;
	public static final int COL_AGE = 2;
	public static final int COL_SEX = 3;
	public static final int COL_WIN = 4;
	public static final int COL_LOSS = 5;
	public static final int COL_DRAW = 6;
	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_AGE, KEY_SEX, KEY_WIN, KEY_LOSS, KEY_DRAW};
	public static final String DATABASE_NAME = "MyDb";
	//Environment.getExternalStorageDirectory()+ File.separator + FILE_DIR + File.separator +
	public static final String DATABASE_TABLE = "mainTable";
	public static final int DATABASE_VERSION = 1;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 		
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_NAME + " text not null, "
			+ KEY_AGE + " integer not null, "
			+ KEY_SEX + " text not null,"
			+ KEY_WIN + " integer not null, "
			+ KEY_LOSS + " integer not null, "
			+ KEY_DRAW + " integer not null "
			+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(String name, int age, String sex) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_AGE, age);
		initialValues.put(KEY_SEX, sex);
		initialValues.put(KEY_WIN, SCORE);
		initialValues.put(KEY_LOSS, SCORE);
		initialValues.put(KEY_DRAW, SCORE);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get a specific row (by Name)
		public Cursor getByName(String name) {
			String where = KEY_NAME + "='"+ name +"'";
			Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String name, int age, String sex) {
		String where = KEY_ROWID + "=" + rowId;
		
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_AGE, age);
		newValues.put(KEY_SEX, sex);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	public boolean updateScore(String name, String status) {
		String where = KEY_NAME + "='"+ name +"'";
		ContentValues newValues = new ContentValues();
		Cursor c = getByName(name);
		
		int winCount = c.getInt(COL_WIN);
		int lossCount = c.getInt(COL_LOSS);
		int drawCount = c.getInt(COL_DRAW);
		
		if(status =="win")
			winCount = winCount+1;
		else if(status =="loss")
			lossCount = lossCount+1;
		else if(status =="draw")
			drawCount = drawCount+1;
		
		newValues.put(KEY_WIN, winCount);
		newValues.put(KEY_LOSS, lossCount);
		newValues.put(KEY_DRAW, drawCount);

		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
