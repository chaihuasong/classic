package com.example.elvin.htzclassic;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    private  SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view,menu);
        setSearchView(menu);

        return  true;
    }

    private  void setSearchView(Menu menu){
        MenuItem item =  menu.findItem(R.id.searchview);
        mSearchView = new SearchView(SearchActivity.this);
        mSearchView.onActionViewExpanded();
        mSearchView.setQueryHint("搜索原文、翻译");
        mSearchView.setSubmitButtonEnabled(true);

        TextView textView = (TextView)mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextColor(Color.GRAY);
        item.setActionView(mSearchView);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this,query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
 }
