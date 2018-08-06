package utils;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lianavklt on 23/06/2018.
 */

public class JsonUtils {

  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String STEPS = "steps";
  private static final String INGREDIENTS = "ingredients";
  private static final String SHORT_DESCRIPTION = "shortDescription";
  private static final String DESCRIPTION = "description";
  private static final String VIDEO_URL = "videoURL";
  private static final String QUANTITY = "quantity";
  private static final String MEASURE = "measure";
  private static final String INGREDIENT_DESCRIPTION = "ingredient";

  private static String json;

  public static List<Recipe> getStringsFromJson(String jsonString) throws JSONException {
    json = jsonString;
    List<Recipe> recipes = new ArrayList<>();

    JSONArray results = new JSONArray(json);

    for (int i = 0; i < results.length(); i++) {
      JSONObject recipeInfo = results.getJSONObject(i);
      Recipe recipe = new Recipe();
      Integer recipeId = recipeInfo.getInt(ID);
      String recipeName = recipeInfo.getString(NAME);
      recipe.setId(recipeId);
      recipe.setName(recipeName);
      recipe.setSteps(populateRecipeStepsFromJson(i, json));
      recipe.setIngredients(populateIngredientsFromJson(i));
      recipes.add(recipe);
    }

    return recipes;
  }

  public static List<Ingredient> populateIngredientsFromJson(int position)
      throws JSONException {
    List<Ingredient> ingredientList = new ArrayList<>();
    JSONArray results = new JSONArray(json);
    JSONObject recipeInfo = results.getJSONObject(position);

    JSONArray ingredients = recipeInfo.getJSONArray(INGREDIENTS);
    for (int i = 0; i < ingredients.length(); i++) {
      JSONObject ingredientInfo = ingredients.getJSONObject(i);
      Ingredient ingredient = new Ingredient();
      double quantity = ingredientInfo.getDouble(QUANTITY);
      String measure = ingredientInfo.getString(MEASURE);
      String description = ingredientInfo.getString(INGREDIENT_DESCRIPTION);
      ingredient.setIngredientDescription(description);
      ingredient.setMeasure(measure);
      ingredient.setQuantity(quantity);
      ingredientList.add(ingredient);
    }
    return ingredientList;
  }

  public static List<Step> populateRecipeStepsFromJson(int position, String json)
      throws JSONException {
    List<Step> stepList = new ArrayList<>();
    JSONArray results = new JSONArray(json);
    JSONObject recipeInfo = results.getJSONObject(position);
    JSONArray steps = recipeInfo.getJSONArray(STEPS);
    for (int i = 0; i < steps.length(); i++) {
      JSONObject stepInfo = steps.getJSONObject(i);
      Step step = new Step();
      Integer stepId = stepInfo.getInt(ID);
      String shortDescription = stepInfo.getString(SHORT_DESCRIPTION);
      String description = stepInfo.getString(DESCRIPTION);
      String videoUrl = stepInfo.getString(VIDEO_URL);
      step.setDescription(description);
      step.setId(stepId);
      step.setShortDescription(shortDescription);
      step.setVideoUrl(videoUrl);

      stepList.add(step);
    }

    return stepList;

  }

  public static Step populateSpecificStepFromJson(int recipePosition, int position)
      throws JSONException {
    Step step = new Step();
    JSONArray results = new JSONArray(json);
    JSONObject recipeInfo = results.getJSONObject(recipePosition);
    JSONArray steps = recipeInfo.getJSONArray(STEPS);

    JSONObject stepInfo = steps.getJSONObject(position);
    Integer stepId = stepInfo.getInt(ID);
    String shortDescription = stepInfo.getString(SHORT_DESCRIPTION);
    String description = stepInfo.getString(DESCRIPTION);
    String videoUrl = stepInfo.getString(VIDEO_URL);
    step.setDescription(description);
    step.setId(stepId);
    step.setShortDescription(shortDescription);
    step.setVideoUrl(videoUrl);

    return step;

  }
}
