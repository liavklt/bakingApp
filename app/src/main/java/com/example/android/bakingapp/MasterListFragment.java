package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianavklt on 21/06/2018.
 */

// This fragment displays all of the recipe cards in one large list
// The list appears as a grid of views
public class MasterListFragment extends Fragment {

  OnTextClickListener mCallback;
  GridView gridView;
  Recipe recipe;


  public interface OnTextClickListener {

    void onTextSelected(int position);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    // This makes sure that the host activity has implemented the callback interface
    // If not, it throws an exception
    try {
      mCallback = (OnTextClickListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()
          + " must implement OnTextClickListener");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    if (savedInstanceState != null) {
      recipe = savedInstanceState.getParcelable("recipe");
    } else if (getActivity().getIntent().getExtras() != null) {
      recipe = getActivity().getIntent().getExtras().getParcelable("recipe");
    }

    final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

    // Get a reference to the GridView in the fragment_master_list xml layout file
    gridView = rootView.findViewById(R.id.images_grid_view);

    // Create the adapter
    List<Step> steps = new ArrayList<>();
    if (recipe != null) {
      steps = recipe.getSteps();
    }

    MasterListAdapter mAdapter = new MasterListAdapter(getContext(), steps);

    // Set the adapter on the GridView
    gridView.setAdapter(mAdapter);

    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        // Trigger the callback method and pass in the position that was clicked
        mCallback.onTextSelected(position);
      }
    });

    return rootView;
  }

//  @Override
//  public void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    outState.putParcelable("recipe", recipe);
//  }

//  @Override
//  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//    super.onViewStateRestored(savedInstanceState);
//    if (savedInstanceState != null) {
//      System.out.println("inside onviewstaterestored");
//    }
//  }

}
