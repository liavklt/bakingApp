package com.example.android.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;

/**
 * Created by lianavklt on 08/07/2018.
 */

class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.IngredientsViewHolder> {

  private Cursor mCursor;
  private Context mContext;


  public CustomCursorAdapter(Context context) {
    this.mContext = context;
  }

  @Override
  public CustomCursorAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
    mContext = parent.getContext();

    int layoutIdForItem = R.layout.recyclerview_item_ingredient;
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);
    return new IngredientsViewHolder(thisItemsView);
  }

  @Override
  public void onBindViewHolder(CustomCursorAdapter.IngredientsViewHolder holder,
      int position) {
    int ingredientsIndex = mCursor.getColumnIndex(IngredientsEntry.COLUMN_INGREDIENT);
    mCursor.moveToPosition(position);
    String ingredients = mCursor.getString(ingredientsIndex).replaceAll(",", "\n");

    holder.ingredientTextView.setText(ingredients);


  }

  @Override
  public int getItemCount() {
    if (mCursor == null) {
      return 0;
    }
    return mCursor.getCount();
  }

  public Cursor swapCursor(Cursor cursor) {
    if (mCursor == cursor) {
      return null;
    }
    Cursor temp = mCursor;
    this.mCursor = cursor;

    if (cursor != null) {
      this.notifyDataSetChanged();
    }
    return temp;
  }

  class IngredientsViewHolder extends ViewHolder {


    TextView ingredientTextView;

    public IngredientsViewHolder(View itemView) {
      super(itemView);
      ingredientTextView = itemView.findViewById(R.id.tv_ingredient_item);

    }
  }
}
