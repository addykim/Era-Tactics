package askim.eratactics.activities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import askim.eratactics.R;
import askim.eratactics.adapters.LevelSelectAdapter;
import askim.eratactics.gamelogic.LevelGenerator;

public class LevelSelectActivity extends AppCompatActivity {

    private static final String TAG = "Level select";

    private LevelSelectAdapter adapter;
    private List<LevelGenerator> levelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        // TODO hook up buttons so they select the various levels

        // Initialize recycler view
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.level_select_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        levelList = LevelGenerator.listAll(LevelGenerator.class);

        adapter = new LevelSelectAdapter(LevelSelectActivity.this, levelList);
        mRecyclerView.setAdapter(adapter);
    }
}

