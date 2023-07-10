package com.android.aceit.Models;

public class Subject {
    private String name;
    private String key;

    public String getKey() {
        return key;
    }

    private boolean hasSubcategories;

    public Subject(String name, String key, boolean hasSubcategories) {
        this.name = name;
        this.key = key;
        this.hasSubcategories = hasSubcategories;
    }



    public String getName() {
        return name;
    }

    public boolean hasSubcategories() {
        return hasSubcategories;
    }
}
