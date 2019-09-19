package com.example.geek.bakingapp.ui.recipe.step;

import android.os.Bundle;

import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Step;
import com.example.geek.bakingapp.databinding.StepDetailsActivityBinding;

import org.parceler.Parcels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

public class StepDetailsActivity extends AppCompatActivity {

    private StepDetailsActivityBinding binding;
    private Step step;
    public static final String STEP_EXTRA = "step";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.step_details_activity);

        step = Parcels.unwrap(getIntent().getParcelableExtra(STEP_EXTRA));
        StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(step);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.stepDetailsContainer, stepDetailsFragment).commit();
    }
}
