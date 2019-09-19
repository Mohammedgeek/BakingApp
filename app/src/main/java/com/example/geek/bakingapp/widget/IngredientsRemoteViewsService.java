package com.example.geek.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.geek.bakingapp.Prefs;
import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Ingredient;
import com.example.geek.bakingapp.data.Recipe;

import java.util.List;

public class IngredientsRemoteViewsService extends RemoteViewsService {

    public static void updateWidget(Context context, Recipe recipe) {
        Prefs.saveRecipe(context, recipe);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingProviderWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        BakingProviderWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new IngredientsRemoteViewsFactory(getApplicationContext());
    }
}

class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredientsList;
    private Recipe recipe;


    IngredientsRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = Prefs.loadRecipe(mContext);
        mIngredientsList = recipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredientsList == null || mIngredientsList.isEmpty())
            return 0;
        else
            return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Ingredient ingredient = mIngredientsList.get(position);
        remoteViews.setTextViewText(R.id.list_view_text_item,
                ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

