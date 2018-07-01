package com.example.android.bakingapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.android.bakingapp.model.Step;
import java.util.List;

/**
 * Created by lianavklt on 21/06/2018.
 */

public class MasterListAdapter extends BaseAdapter {

  private Context mContext;
  private List<Step> mSteps;


  public MasterListAdapter(Context context, List<Step> steps) {
    mContext = context;
    mSteps = steps;
  }

  /**
   * Returns the number of items the adapter will display
   */
  @Override
  public int getCount() {
    return mSteps.size();
  }

  @Override
  public Object getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }


  public View getView(final int position, View convertView, ViewGroup parent) {
    TextView textView;
    if (convertView == null) {
      textView = new TextView(mContext);
      textView.setPadding(8, 8, 8, 8);
    } else {
      textView = (TextView) convertView;
    }

    textView.setText(mSteps.get(position).getShortDescription());
    return textView;


  }
}
