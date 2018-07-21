package com.example.aleksejkocergin.testapp.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TextDao {

    @Query("SELECT * FROM text")
    Flowable<List<Text>> getAll();

    @Insert
    void insert(Text languageIdentify);

    @Update
    void update(Text languageIdentify);

    @Delete
    void delete(Text languageIdentify);


}
