package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView articleRecyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList;
    private Button addArticleButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration de la RecyclerView
        articleRecyclerView = findViewById(R.id.articleRecyclerView);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleList = DatabaseHelper.getInstance(this).articleDao().getAllArticles();
        articleAdapter = new ArticleAdapter(articleList);
        articleRecyclerView.setAdapter(articleAdapter);

        // Configuration du bouton d'ajout d'article
        addArticleButton = findViewById(R.id.addArticleButton);
        addArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddArticleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mettre à jour la liste des articles lorsque l'utilisateur revient sur la page principale
        articleList = ArticleDatabase.getInstance(this).articleDao().getAllArticles();
        articleAdapter.setArticleList(articleList);
        articleAdapter.notifyDataSetChanged();
    }
}
