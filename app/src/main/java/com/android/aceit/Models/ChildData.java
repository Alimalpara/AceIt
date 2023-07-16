package com.android.aceit.Models;

import org.json.JSONObject;

public class ChildData {
    private String name;
    private boolean hasSubcategories;
    private JSONObject jsonObject;
    private String key;
    private  String qid;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ChildData(String name, String key,  boolean hasSubcategories, JSONObject jsonObject) {
        this.name = name;
        this.key = key;
        this.hasSubcategories = hasSubcategories;
        this.jsonObject = jsonObject;

    }

    public ChildData() {

    }

    public String getName() {
        return name;
    }

    public boolean hasSubcategories() {
        return hasSubcategories;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHasSubcategories(boolean hasSubcategories) {
        this.hasSubcategories = hasSubcategories;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
