package com.example.geek.bakingapp.ui.recipe.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geek.bakingapp.data.Ingredient;
import com.example.geek.bakingapp.databinding.IngredientListItemBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends ListAdapter<Ingredient, IngredientsAdapter.ItemViewHolder> {


    protected IngredientsAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientListItemBinding binding = IngredientListItemBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private IngredientListItemBinding binding;

        ItemViewHolder(IngredientListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Ingredient ingredient){
            String text = ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient();
            binding.ingredientItemTextView.setText(text);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<Ingredient> diffCallback = new DiffUtil.ItemCallback<Ingredient>() {
        @Override
        public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.getIngredient().equals(newItem.getIngredient());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return true;
        }
    };
}
