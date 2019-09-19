package com.example.geek.bakingapp.ui.recipe;

import android.os.Bundle;

import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Recipe;
import com.example.geek.bakingapp.data.Step;
import com.example.geek.bakingapp.ui.recipe.step.StepDetailsFragment;
import com.example.geek.bakingapp.widget.IngredientsRemoteViewsService;

import org.parceler.Parcels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class RecipeActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA = "recipe";
    private Recipe recipe;
    public static boolean mTwoPane;
    public static boolean stepAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_EXTRA));
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeFragment recipeFragment = RecipeFragment.newInstance(recipe);
        fragmentManager.beginTransaction().add(R.id.recipeContainer, recipeFragment).commit();

        if (savedInstanceState == null) {
            IngredientsRemoteViewsService.updateWidget(this, recipe);
        }

        if (findViewById(R.id.twoPane) != null) {
            StepDetailsFragment stepDetailsFragment;
            mTwoPane = true;
            if (!stepAdded) {
                stepAdded = true;
                Step firstStep = recipe.getSteps().get(0);
                stepDetailsFragment = StepDetailsFragment.newInstance(firstStep);
                fragmentManager.beginTransaction().add(R.id.stepDetailsContainer, stepDetailsFragment).commit();
            }

        } else {
            mTwoPane = false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stepAdded = false;
    }


}
