package com.example.geek.bakingapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.geek.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Recipe;
import com.example.geek.bakingapp.databinding.ActivityMainBinding;
import com.example.geek.bakingapp.ui.recipe.RecipeActivity;

import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.test.espresso.IdlingResource;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RecipesAdapter recipesAdapter = new RecipesAdapter();
    private ActivityMainBinding binding;
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initializeIdlingResource();
        setIdleState(false);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getLifecycle().addObserver(viewModel);

        setupRecyclerView();

        viewModel.getRecipesLive().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipesAdapter.submitList(recipes);
                setIdleState(true);
            }
        });

    }

    private void setupRecyclerView() {
        binding.recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recipesRecyclerView.setAdapter(recipesAdapter);

        recipesAdapter.setRecipeClickListener(new RecipesAdapter.RecipeClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                intent.putExtra(RecipeActivity.RECIPE_EXTRA, Parcels.wrap(recipe));
                startActivity(intent);
            }
        });
    }

    @VisibleForTesting
    @NonNull
    private IdlingResource initializeIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public void setIdleState(boolean state) {
        if (mIdlingResource != null)
            mIdlingResource.setIdleState(state);
    }

    @Nullable
    public SimpleIdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
