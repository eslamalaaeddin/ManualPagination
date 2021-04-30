package com.example.manualpagination;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    static class Item {
        @SerializedName("full_name")
        private String fullName;
        private String description;
        @SerializedName("svn_url")
        private String url;
        @SerializedName("stargazers_count")
        private int starsCount;
        private int forks;
        private String language;


        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStarsCount() {
            return starsCount;
        }

        public void setStarsCount(int starsCount) {
            this.starsCount = starsCount;
        }

        public int getForks() {
            return forks;
        }

        public void setForks(int forks) {
            this.forks = forks;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "fullName='" + fullName + '\'' +
                    '}';
        }
    }

    @SerializedName("items")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "items=" + items +
                '}';
    }

}
