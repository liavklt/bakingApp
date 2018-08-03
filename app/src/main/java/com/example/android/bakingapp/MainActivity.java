package com.example.android.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.data.IngredientsContract.IngredientsEntry;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import utils.JsonUtils;
import utils.NetworkUtils;

/**
 * Created by lianavklt on 23/06/2018.
 */

public class MainActivity extends AppCompatActivity {

  private static final String LOG_TAG = MainActivity.class.getSimpleName();
  private static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
  @BindView(R.id.lv_recipes)
  ListView listView;
  RecipeListAdapter adapter;
  private List<Recipe> recipes;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    recipes = new ArrayList<>();
    URL recipeUrl;
    if (NetworkUtils.isConnected(this)) {
      recipeUrl = NetworkUtils.buildUrl(URL);
      adapter = new RecipeListAdapter(this);
      new FetchRecipesAsyncTask(this, new FetchRecipesTaskListener()).execute(recipeUrl);
      listView.setAdapter(adapter);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
          launchMasterListActivity(position);
        }
      });

    } else {
      Toast.makeText(this, R.string.connectionError, Toast.LENGTH_LONG).show();
      listView.setVisibility(View.INVISIBLE);
    }


  }

  private void addIngredientsIntoDb(Recipe recipe) {
    //TODO everytime try to add the ingredients? check it out...
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
//    if (uri != null) {
//      Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//    } else {
//      Toast.makeText(getBaseContext(), "Already exists!", Toast.LENGTH_SHORT).show();
//    }


  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);


  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {

    super.onSaveInstanceState(outState);

  }

  private void launchMasterListActivity(int i) {
    Intent intent = new Intent(this, MasterListActivity.class);
    intent.putExtra(MasterListActivity.EXTRA_POSITION, i);
    intent.putExtra("recipe", recipes.get(i));
    intent.putParcelableArrayListExtra("allRecipes", (ArrayList<? extends Parcelable>) recipes);
    startActivity(intent);
  }


  private class FetchRecipesTaskListener implements AsyncTaskListener<List<Recipe>> {

    @Override
    public void onTaskPreExecute() {

    }

    @Override
    public List<Recipe> onTaskGetResult(int position) {
      return null;
    }

    @Override
    public List<Recipe> onTaskGetResult(URL url) {
      try {
        String jsonString = NetworkUtils.getResponseFromHttpUrl(url);
        recipes = JsonUtils.getStringsFromJson(jsonString);
        for (Recipe recipe : recipes) {
          addIngredientsIntoDb(recipe);
        }
      } catch (IOException | JSONException e) {
        e.printStackTrace();
      }
      return recipes;
    }

    @Override
    public void onTaskComplete(List<Recipe> result) {

      if (result != null) {
        listView.setVisibility(View.VISIBLE);
        adapter.setRecipeData(result);
        adapter.notifyDataSetChanged();

      }
    }
  }
}
