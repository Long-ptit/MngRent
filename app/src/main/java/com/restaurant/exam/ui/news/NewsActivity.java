package com.restaurant.exam.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.restaurant.exam.data.model.News;

import restaurant.exam.R;


public class NewsActivity extends AppCompatActivity {
    private WebView webView;
    private News oneNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        webView = findViewById(R.id.webView);


        Intent intent = getIntent();
        oneNews = (News) intent.getSerializableExtra("oneNews");
        String link = oneNews.getLink();


        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());

        News news = new News();

        news.setImg(oneNews.getImg());
        news.setLink(oneNews.getLink());
        news.setTitle(oneNews.getTitle());

    }
}