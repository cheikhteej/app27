package com.example.myapplication;


import static com.google.android.material.animation.ArgbEvaluatorCompat.instance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "blog.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ARTICLES = "articles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createArticlesTable = "CREATE TABLE " + TABLE_ARTICLES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_DATE + " TEXT)";
        db.execSQL(createArticlesTable);
    }
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    public long insertArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, article.getTitle());
        values.put(COLUMN_CONTENT, article.getContent());
        values.put(COLUMN_AUTHOR, article.getAuthor());
        values.put(COLUMN_DATE, article.getDate());
        long id = db.insert(TABLE_ARTICLES, null, values);
        db.close();
        return id;
    }

    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ARTICLES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                Article article = new Article(id, title, content, author, date);
                articles.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return articles;
    }

    public Article getArticleById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLES, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_AUTHOR, COLUMN_DATE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        @SuppressLint("Range") Article article = new Article(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
        cursor.close();
        return article;
    }
}