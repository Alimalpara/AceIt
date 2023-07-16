package com.android.aceit.Models;

public class ChecklistItem {
    private long id;
    private String text;
    private boolean isChecked;

    public ChecklistItem(long id, String text, boolean isChecked) {
        this.id = id;
        this.text = text;
        this.isChecked = isChecked;
    }

    // Getter and setter methods
    // ...

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
