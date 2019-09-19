package com.example.geek.bakingapp;

import android.content.Intent;

import com.example.geek.bakingapp.ui.main.MainActivity;
import com.example.geek.bakingapp.ui.recipe.RecipeActivity;
import com.example.geek.bakingapp.ui.recipe.step.StepDetailsActivity;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BakingTest {
    private IdlingResource mIdlingResource;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public static void selectRecipe(int recipePosition) {
        onView(withId(R.id.recipesRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void selectRecipeStep(int recipeStep) {
        onView(withId(R.id.stepsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // Register Idling Resources
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void checkIntentExtra() {
        Intents.init();
        selectRecipe(0);
        Matcher<Intent> intentExtraMatcher = IntentMatchers.hasExtraWithKey(RecipeActivity.RECIPE_EXTRA);
        Intents.intended(intentExtraMatcher);
        Intents.release();
    }

    @Test
    public void clickOnRecyclerView() {
        selectRecipe(0);
        onView(withId(R.id.ingredientsTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.stepsRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepRecyclerView() {
        selectRecipe(0);
        boolean mTwoPane = RecipeActivity.mTwoPane;
        if (mTwoPane) {
            selectRecipeStep(0);
            onView(withId(R.id.exoPlayerView)).check(matches(isDisplayed()));
        } else {
            Intents.init();
            selectRecipeStep(0);
            Matcher<Intent> intentExtraMatcher = IntentMatchers.hasExtraWithKey(StepDetailsActivity.STEP_EXTRA);
            Intents.intended(intentExtraMatcher);
            Intents.release();
        }
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
