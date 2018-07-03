package com.example.android.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;

/**
 * Created by lianavklt on 03/07/2018.
 */

public class IngredientsDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "ingredients.db";
  private static final int DATABASE_VERSION = 0;

  public IngredientsDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_INGREDIENTS_TABLE =
        "CREATE TABLE " + IngredientsEntry.TABLE_NAME + " (" +
            IngredientsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            IngredientsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
            IngredientsEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
            IngredientsEntry.COLUMN_QUANTITY + " INTEGER, " +
            IngredientsEntry.COLUMN_MEASURE + " TEXT, " +
            IngredientsEntry.COLUMN_INGREDIENT + " TEXT" + "); ";
    db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //TODO change to not drop it
    final String SQL_DROP_TABLE_IF_EXISTS="DROP TABLE IF EXISTS "+ IngredientsEntry.TABLE_NAME+";";
    db.execSQL(SQL_DROP_TABLE_IF_EXISTS);
    onCreate(db);

  }
}
