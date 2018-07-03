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

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public String getIngredientDescription() {
    return ingredientDescription;
  }

  public void setIngredientDescription(String ingredientDescription) {
    this.ingredientDescription = ingredientDescription;
  }
}
