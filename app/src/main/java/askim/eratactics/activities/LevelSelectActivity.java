package askim.eratactics.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import askim.eratactics.R;
import askim.eratactics.adapters.LevelSelectAdapter;
import askim.eratactics.views.LevelSelectView;

public class LevelSelectActivity extends AppCompatActivity {

    private static final String TAG = "Level select";


    private LevelSelectAdapter adapter;
    private ArrayList<String> levelList;
//    private ArrayList<LevelSelectView> levelList;
    // TODO replace with a level select button views
    private Button firstLevel;
    private Button secondLevel;
    private Button thirdLevel;
    private Button forthLevel;

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

        levelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            levelList.add(Integer.toString(i));
        }
        adapter = new LevelSelectAdapter(LevelSelectActivity.this, levelList);
        mRecyclerView.setAdapter(adapter);
    }
}

