package askim.eratactics.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.EnumFile;
import askim.eratactics.gamelogic.Equipment;
import askim.eratactics.gamelogic.Team;
import askim.eratactics.views.TeamListView;
import askim.eratactics.views.TeamView;

/**
 * Created by addykim on 4/12/16.
 */
public class TeamsActivity extends AppCompatActivity {
    private final String TAG = "TeamsActivity";

    private TeamView teamView;
    private TeamListView teamListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teams);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        Team alphaTeam = Team.findById(Team.class, 1);
        alphaTeam.setTeamMembers();
        teamView = (TeamView) findViewById(R.id.preSetTeam);
        teamView.setTeam(alphaTeam);
        Toast.makeText(this, ""+alphaTeam.getTeamMembers().size(), Toast.LENGTH_SHORT).show();
        for (Adventurer member: alphaTeam.getTeamMembers()) {
            Log.d(TAG, member.getAdventurerName());
        }

        teamListView = (TeamListView) findViewById(R.id.preSetTeamList);
        teamListView.setTeamMembers(alphaTeam.getTeamMembers());
    }

    // TODO method to save changes to team
}
