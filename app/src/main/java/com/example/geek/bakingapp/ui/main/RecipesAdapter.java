package com.example.geek.bakingapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Recipe;
import com.example.geek.bakingapp.databinding.RecipeListItemBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesAdapter extends ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder> {

    private RecipeClickListener recipeClickListener;

    protected RecipesAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeListItemBinding binding = DataBindingUtil.inflate
                (LayoutInflater.from(parent.getContext()), R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private RecipeListItemBinding binding;

        RecipeViewHolder(@NonNull View itemView, RecipeListItemBinding binding) {
            super(itemView);
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recipeClickListener != null) {
                        recipeClickListener.onClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }

        void bind(Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }
    }

    public void setRecipeClickListener(RecipeClickListener recipeClickListener) {
        this.recipeClickListener = recipeClickListener;
    }


    private static DiffUtil.ItemCallback<Recipe> diffCallback = new DiffUtil.ItemCallback<Recipe>() {
        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.equals(newItem);
        }
    };

    public interface RecipeClickListener {
        void onClick(Recipe recipe);
    }
}
