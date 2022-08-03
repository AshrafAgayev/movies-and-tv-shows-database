package com.example.moviesmvvm.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.moviesmvvm.dao.TVShowDao;
import com.example.moviesmvvm.models.TVShow;

import io.reactivex.Completable;

@Database(entities = TVShow.class, version = 1, exportSchema = false)
public abstract class TVShowDatabase extends RoomDatabase {

    private static TVShowDatabase tvShowDatabase;


    public static synchronized TVShowDatabase getTvShowDatabase(Context context){
        if(tvShowDatabase == null){
            tvShowDatabase = Room.databaseBuilder(
                    context,
                    TVShowDatabase.class,
                    "tv_shows_db"
            ).build();
        }
        return tvShowDatabase;
    }

    public abstract TVShowDao tvShowDao();


}
