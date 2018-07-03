package com.example.android.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONException;
import utils.JsonUtils;

/**
 * Created by lianavklt on 23/06/2018.
 */

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.recipes_grid_view)
  GridView gridView;
  private List<Recipe> recipes;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    recipes = new ArrayList<>();
    try {
      InputStream inputStream = this.getResources().openRawResource(R.raw.recipes);
      String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
      recipes = JsonUtils.getStringsFromJson(jsonString);
      for (Recipe recipe : recipes) {
        addIngredients(recipe);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RecipeListAdapter mAdapter = new RecipeListAdapter(this, recipes);

    // Set the adapter on the GridView
    //TODO change to RecyclerView
    gridView.setAdapter(mAdapter);
    gridView.setNumColumns(1);

    gridView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        launchMasterListActivity(i);
      }
    });

  }

  private void addIngredients(Recipe recipe) {
    List<Ingredient> ingredients = recipe.getIngredients();
    List<String> ingredientDescriptions = new ArrayList<>();
    for (Ingredient ingredient : ingredients) {
      ingredientDescriptions.add(ingredient.toString());
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put(IngredientsEntry.COLUMN_RECIPE_NAME, recipe.getName());
    contentValues.put(IngredientsEntry.COLUMN_RECIPE_ID, recipe.getId());
    contentValues
        .put(IngredientsEntry.COLUMN_INGREDIENT, TextUtils.join(",", ingredientDescriptions));
    Uri uri = getContentResolver().insert(IngredientsEntry.CONTENT_URI, contentValues);
    if (uri != null) {
      Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(getBaseContext(), "Already exists!", Toast.LENGTH_SHORT).show();
    }


  }

  private void launchMasterListActivity(int i) {
    Intent intent = new Intent(this, MasterListActivity.class);
    intent.putExtra(MasterListActivity.EXTRA_POSITION, i);
    intent.putExtra("recipe", recipes.get(i));
    startActivity(intent);
  }


}
