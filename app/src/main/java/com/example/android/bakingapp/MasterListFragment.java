package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.bakingapp.model.Step;
import java.util.List;
import org.json.JSONException;
import utils.JsonUtils;

/**
 * Created by lianavklt on 21/06/2018.
 */

// This fragment displays all of the recipe cards in one large list
// The list appears as a grid of views
public class MasterListFragment extends Fragment {


  private RecyclerView recyclerView;
  private LinearLayoutManager linearLayoutManager;
  private MasterListAdapter mAdapter;


  public void initializeRecyclerView(RecyclerView rv, int recipePosition) {
    recyclerView = rv;
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
        false);
    recyclerView.setLayoutManager(linearLayoutManager);
    mAdapter = new MasterListAdapter(getActivity());
    recyclerView.setAdapter(mAdapter);
    new FetchStepsAsyncTask(getContext(), new FetchStepsTaskListener()).execute(recipePosition);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_master_list, container, false);
  }

  public class FetchStepsTaskListener implements AsyncTaskListener<List<Step>> {
    @Override
    public void onTaskPreExecute() {

    }

    @Override
    public List<Step> onTaskGetResult(int position) {
      List<Step> steps;
      try {
        steps = JsonUtils.populateRecipeStepsFromJson(position);
      } catch (JSONException e) {
        e.printStackTrace();
        return null;
      }
      return steps;
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
