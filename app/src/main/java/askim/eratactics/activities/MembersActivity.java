package askim.eratactics.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import askim.eratactics.R;
import askim.eratactics.adapters.MemberAdapter;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.EnumFile;
import askim.eratactics.gamelogic.Equipment;
import askim.eratactics.gamelogic.PlayerAdventurers;

/**
 * Created by addykim on 4/12/16.
 */
public class MembersActivity extends AppCompatActivity {

    private static final String TAG = "Members";
//    private List<String> memberList;
    private List<Adventurer> memberList;
    private RecyclerView mRecyclerView;
    private MemberAdapter adapter;

    // Recycler view https://developer.android.com/training/material/lists-cards.html#RecyclerView
    // Recycler view http://javatechig.com/android/android-recyclerview-example

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.member_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        memberList = new ArrayList<Adventurer>();

        memberList = Adventurer.listAll(Adventurer.class);

        adapter = new MemberAdapter(MembersActivity.this, memberList);
        mRecyclerView.setAdapter(adapter);
    }
}

