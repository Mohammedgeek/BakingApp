package com.example.geek.bakingapp.ui.main;

import android.util.Log;

import com.example.geek.bakingapp.data.Recipe;
import com.example.geek.bakingapp.data.rest.RetrofitClient;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel implements LifecycleObserver {
    private String TAG = MainViewModel.class.getSimpleName();

    private MutableLiveData<List<Recipe>> recipesLive = new MutableLiveData<>();

    public LiveData<List<Recipe>> getRecipesLive() {
        return recipesLive;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void loadRecipes(){
        RetrofitClient.getRecipesService().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    recipesLive.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}
