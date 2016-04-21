package askim.eratactics.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import askim.eratactics.R;
import askim.eratactics.adapters.MemberAdapter;
import askim.eratactics.views.MemberView;

/**
 * Created by addykim on 4/12/16.
 */
public class MembersActivity extends AppCompatActivity {

    private static final String TAG = "Members";
    private List<MemberView> memberList;
    private RecyclerView mRecyclerView;
    private MemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        memberList = new ArrayList<>();

        adapter = new MemberAdapter(MembersActivity.this, memberList);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}

