package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.model.Step;
import java.util.List;

/**
 * Created by lianavklt on 21/06/2018.
 */

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> {

  OnTextClickListener mCallback;
  private Context mContext;
  private List<Step> mSteps;

  public MasterListAdapter(Context context) {
    mContext = context;
//    mCallback = (OnTextClickListener) context;
  }

  @Override
  public MasterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    int layoutIdForItem = R.layout.recyclerview_item_recipe;
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);
    return new ViewHolder(thisItemsView);

  }

  @Override
  public void onBindViewHolder(@NonNull MasterListAdapter.ViewHolder holder, final int position) {
    String shortDescription = mSteps.get(position).getShortDescription();
    holder.textView.setText(shortDescription);
    holder.textView.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        ((OnTextClickListener) mContext).onTextSelected(position);
      }
    });

//    holder.textView.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//
////        Intent intent = new Intent(mContext, ViewRecipeActivity.class);
////        Bundle bundle = new Bundle();
////        bundle.putParcelable("step", mSteps.get(position));
////        intent.putExtras(bundle);
////        mContext.startActivity(intent);
//      }
//    });

  }

  @Override
  public int getItemCount() {
    if (mSteps == null) {
      return 0;
    }
    return mSteps.size();
  }

  public void setReviewData(List<Step> steps) {
    this.mSteps = steps;
  }

  public interface OnTextClickListener {

    void onTextSelected(int position);
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView textView;


    public ViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.tv_recipe_item);
    }

  }
}
