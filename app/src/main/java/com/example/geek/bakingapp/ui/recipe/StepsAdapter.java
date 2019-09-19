package com.example.geek.bakingapp.ui.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geek.bakingapp.R;
import com.example.geek.bakingapp.data.Step;
import com.example.geek.bakingapp.databinding.StepListItemBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StepsAdapter extends ListAdapter<Step, StepsAdapter.StepViewHolder> {

    private StepClickListener stepClickListener;

    protected StepsAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StepListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from
                (parent.getContext()), R.layout.step_list_item, parent, false);
        return new StepViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        private StepListItemBinding binding;

        StepViewHolder(@NonNull View itemView, StepListItemBinding binding) {
            super(itemView);
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepClickListener != null) {
                        stepClickListener.onClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }

        void bind(Step step) {
            binding.setStep(step);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<Step> diffCallback = new DiffUtil.ItemCallback<Step>() {
        @Override
        public boolean areItemsTheSame(@NonNull Step oldItem, @NonNull Step newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Step oldItem, @NonNull Step newItem) {
            return oldItem.getId() == newItem.getId();
        }
    };

    public void setStepClickListener(StepClickListener stepClickListener) {
        this.stepClickListener = stepClickListener;
    }

    public interface StepClickListener {
        void onClick(Step step);
    }
}
