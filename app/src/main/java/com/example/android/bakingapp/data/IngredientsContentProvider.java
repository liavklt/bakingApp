package com.example.android.bakingapp.data;

import static com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry.COLUMN_RECIPE_ID;
import static com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by lianavklt on 03/07/2018.
 */

public class IngredientsContentProvider extends ContentProvider {

  public static final int INGREDIENTS = 100;
  public static final int INGREDIENT_WITH_ID = 101;
  private static final UriMatcher uriMatcher = buildUriMatcher();
  private IngredientsDbHelper ingredientsDbHelper;

  public static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher
        .addURI(IngredientsContract.AUTHORITY, IngredientsContract.PATH_INGREDIENTS, INGREDIENTS);
    uriMatcher.addURI(IngredientsContract.AUTHORITY, IngredientsContract.PATH_INGREDIENTS + "/#",
        INGREDIENT_WITH_ID);

    return uriMatcher;
  }

  @Override
  public boolean onCreate() {
    Context context = getContext();
    ingredientsDbHelper = new IngredientsDbHelper(context);
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    final SQLiteDatabase db = ingredientsDbHelper.getReadableDatabase();
    int match = uriMatcher.match(uri);
    Cursor retCursor;

    switch (match) {
      case INGREDIENTS:
        retCursor = db.query(TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    retCursor.setNotificationUri(getContext().getContentResolver(), uri);

    // Return the desired Cursor
    return retCursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = ingredientsDbHelper.getWritableDatabase();
    int match = uriMatcher.match(uri);
    Uri returnUri;
    String recipeId = values != null ? values.get(COLUMN_RECIPE_ID).toString() : null;
    Cursor queryCursor = db
        .query(TABLE_NAME, null, COLUMN_RECIPE_ID + "=?", new String[]{recipeId}, null, null, null);
    if (queryCursor.getCount() != 0) {
      queryCursor.close();
      return null;
    }
    switch (match) {
      case INGREDIENTS:
        long id = db.insert(TABLE_NAME, null, values);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(IngredientsContract.IngredientsEntry.CONTENT_URI, id);
        } else {
          throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);

    return returnUri;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
