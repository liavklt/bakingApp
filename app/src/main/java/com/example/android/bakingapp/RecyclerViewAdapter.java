package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.model.Recipe;
import java.util.List;

/**
 * Created by lianavklt on 05/07/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

  private Context context;
  private List<Recipe> recipes;

  public RecyclerViewAdapter(Context context) {
    this.context = context;
  }

  private static int getDrawableForRecipe(String name) {
    if (Cakes.BROWNIES.getCakeName().equals(name)) {
      return R.drawable.brownie;
    } else if (Cakes.CHEESECAKE.getCakeName().equals(name)) {
      return R.drawable.cheesecake;

    } else if (Cakes.NUTELLA_PIE.getCakeName().equals(name)) {
      return R.drawable.nutella_pie;
    } else {
      return R.drawable.yellow_cake;
    }
  }

  @NonNull
  @Override
  public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    Context context = parent.getContext();
    int layoutIdForItem = R.layout.recyclerview_item_recipe;
    LayoutInflater inflater = LayoutInflater.from(context);
    View thisItemsView = inflater.inflate(layoutIdForItem, parent, false);

    return new ViewHolder(thisItemsView);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
    String name = recipes.get(position).getName();
    holder.textView.setText(name);
    holder.textView.setTypeface(null, Typeface.BOLD);
    holder.textView.setGravity(Gravity.CENTER);
    holder.textView.setTextColor(Color.WHITE);
    holder.textView.setTextSize(32);
    holder.textView.setBackgroundResource(getDrawableForRecipe(name));
  }

  @Override
  public int getItemCount() {
    if (recipes == null) {
      return 0;
    }
    return recipes.size();
  }

  private enum Cakes {
    NUTELLA_PIE("Nutella Pie"), BROWNIES("Brownies"), YELLOW_CAKE("Yellow Cake"), CHEESECAKE(
        "Cheesecake");
    private String cakeName;

    Cakes(String cakeName) {
      this.cakeName = cakeName;

    }

    public String getCakeName() {
      return cakeName;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.tv_recipe_item);
      textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      Intent recipeStepsIntent = new Intent(v.getContext(), MasterListActivity.class);
      int clickPosition = getAdapterPosition();

      Recipe recipe = recipes.get(clickPosition);
      recipeStepsIntent.putExtra("recipe", recipe);
      v.getContext().startActivity(recipeStepsIntent);
    }
  }
}
