package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianavklt on 21/06/2018.
 */

public class Recipe implements Parcelable {

  public static final String TAG = Recipe.class.getSimpleName();
  public static final Parcelable.Creator<Recipe> CREATOR =
      new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
          return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
          return new Recipe[size];
        }
      };
  private String name;
  private int id;
  private List<Step> steps;
  private List<Ingredient> ingredients;

  public Recipe() {
  }

  private Recipe(Parcel source) {
    name = source.readString();
    id = source.readInt();
    steps = new ArrayList<>();
    source.readTypedList(steps,Step.CREATOR);

  }

  public static String toBase64String(Recipe recipe) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0);
    } catch (JsonProcessingException e) {
      Log.e(TAG, e.getMessage());
    }
    return null;
  }

  public static Recipe fromBase64(String base64String) {
    if (!("".equals(base64String))) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        return mapper.readValue(Base64.decode(base64String, 0), Recipe.class);
      } catch (IOException e) {
        Log.e(TAG, e.getMessage());
      }
    }
    return null;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(name);
    parcel.writeInt(id);
    parcel.writeTypedList(steps);

  }
}
