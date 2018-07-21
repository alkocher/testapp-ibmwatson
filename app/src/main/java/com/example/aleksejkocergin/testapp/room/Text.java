package com.example.aleksejkocergin.testapp.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity()
public class Text {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "language_marker")
    private String languageMarker;

    @ColumnInfo(name = "user_text")
    private String userText;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getUserText() {
        return userText;
    }

    public void setLanguageMarker(String languageMarker) {
        this.languageMarker = languageMarker;
    }

    public String getLanguageMarker() {
        return languageMarker;
    }
}
