package com.android.aceit.Models;
public class ExpandableItem {
    private String title;
    private String content;

    public ExpandableItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
