package com.example.manualpagination;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {
    List<Repository.Item> items;

    public ReposAdapter(List<Repository.Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item_layout, parent, false);

        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        Repository.Item item = items.get(holder.getAdapterPosition());
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ReposViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView fullNameTextView;
        private final TextView descriptionTextView;
        private final TextView languageNameTextView;
        private final TextView starsCountTextView;

        public ReposViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTextView = itemView.findViewById(R.id.repoFullNameTextView);
            descriptionTextView = itemView.findViewById(R.id.repoDescriptionTextView);
            languageNameTextView = itemView.findViewById(R.id.repoLanguageTextView);
            starsCountTextView = itemView.findViewById(R.id.starsCountTextView);

            itemView.setOnClickListener(this);
        }

        void bind(Repository.Item item){
            fullNameTextView.setText(item.getFullName());
            descriptionTextView.setText(item.getDescription());
            languageNameTextView.setText(item.getLanguage());
            starsCountTextView.setText(String.valueOf(item.getStarsCount()));
        }

        @Override
        public void onClick(View v) {
            Repository.Item item = items.get(getAdapterPosition());
            Uri uri = Uri.parse(item.getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            itemView.getContext().startActivity(intent);
        }
    }
}
