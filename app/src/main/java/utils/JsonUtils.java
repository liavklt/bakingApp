package utils;

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

  private static final String RESULTS = "results";
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String STEPS = "steps";
  private static final String SHORT_DESCRIPTION = "shortDescription";
  private static final String DESCRIPTION = "description";
  private static final String VIDEO_URL = "videoURL";

  public static List<Recipe> getStringsFromJson(String jsonString) throws JSONException {
    List<Recipe> recipes = new ArrayList<>();
    JSONObject recipeJson = new JSONObject(jsonString);
    JSONArray results = recipeJson.getJSONArray(RESULTS);
    for (int i = 0; i < results.length(); i++) {
      JSONObject recipeInfo = results.getJSONObject(i);
      Recipe recipe = new Recipe();
      Integer recipeId = recipeInfo.getInt(ID);
      String recipeName = recipeInfo.getString(NAME);
      recipe.setId(recipeId);
      recipe.setName(recipeName);
      recipe.setSteps(populateRecipeStepsFromJson(recipeInfo));
      recipes.add(recipe);
    }
    return recipes;
  }

  private static List<Step> populateRecipeStepsFromJson(JSONObject recipeInfo)
      throws JSONException {
    List<Step> stepList = new ArrayList<>();
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
}
