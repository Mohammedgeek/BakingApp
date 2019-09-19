package com.example.geek.bakingapp.ui.recipe.ingredients;

import android.os.Bundle;

import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Recipe;
import com.example.geek.bakingapp.databinding.IngredientActivityBinding;
import com.example.geek.bakingapp.ui.recipe.RecipeActivity;

import org.parceler.Parcels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


public class IngredientsActivity extends AppCompatActivity {

    private IngredientActivityBinding binding;
    private IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ingredient_activity);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(RecipeActivity.RECIPE_EXTRA));

        binding.ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        binding.ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ingredientsAdapter.submitList(recipe.getIngredients());
    }
}
