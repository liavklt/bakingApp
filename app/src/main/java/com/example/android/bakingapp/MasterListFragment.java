package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import java.net.URL;
import java.util.List;

/**
 * Created by lianavklt on 21/06/2018.
 */

// This fragment displays all of the recipe cards in one large list
// The list appears as a grid of views
public class MasterListFragment extends Fragment {


  private RecyclerView recyclerView;
  private MasterListAdapter mAdapter;
  private Recipe mRecipe;
  private Context mContext;


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
//    if (context instanceof MasterListActivity) {
//      MasterListActivity activity = (MasterListActivity) context;
//    }
    mContext = context;

  }

  public void initializeRecyclerView(RecyclerView rv, Recipe recipe) {
    this.mRecipe = recipe;
    recyclerView = rv;
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
        LinearLayoutManager.VERTICAL,
        false);
    recyclerView.setLayoutManager(linearLayoutManager);
    mAdapter = new MasterListAdapter(mContext);
    recyclerView.setAdapter(mAdapter);
    new FetchStepsAsyncTask(new FetchStepsTaskListener()).execute(mRecipe.getId());
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_master_list, container, false);
  }

  public class FetchStepsTaskListener implements AsyncTaskListener<List<Step>> {


    @Override
    public List<Step> onTaskGetResult(int position) {

      return mRecipe.getSteps();
    }

    @Override
    public List<Step> onTaskGetResult(URL url) {
      return null;
    }

    @Override
    public void onTaskComplete(List<Step> result) {
      if (result != null) {
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.setReviewData(result);
        mAdapter.notifyDataSetChanged();
      }
    }
  }


}
