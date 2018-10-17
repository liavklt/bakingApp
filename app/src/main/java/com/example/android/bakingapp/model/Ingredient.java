package com.example.android.bakingapp.model;

/**
 * Created by lianavklt on 23/06/2018.
 */

public class Ingredient {

  private double quantity;
  private String measure;
  private String ingredientDescription;

  public Ingredient() {
  }

  @Override
  public String toString() {
    return quantity + " " + measure + " of " + ingredientDescription;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public void setIngredientDescription(String ingredientDescription) {
    this.ingredientDescription = ingredientDescription;
  }
}
