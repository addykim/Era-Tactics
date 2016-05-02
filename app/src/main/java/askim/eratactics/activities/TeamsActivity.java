package askim.eratactics.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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
    private Team alphaTeam;

    // 0-4 if TeamList, 5-13 if on grid
    private int selected;

    // True Select; False Place;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        status = true;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teams);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        alphaTeam = Team.findById(Team.class, 1);
        teamView = (TeamView) findViewById(R.id.preSetTeam);
        teamView.setTeam(alphaTeam);

        teamListView = (TeamListView) findViewById(R.id.preSetTeamList);
        teamListView.setTeamMembers(alphaTeam.getTeamMembers());
        teamListView.setOnTouchListener(teamListTouchListener);
    }


    private View.OnTouchListener teamListTouchListener = new View.OnTouchListener() {
        /* Rows 1-3 are for the enemies, 4-6 are for the player */
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int col = (int) event.getX() / teamListView.getHeight();  // CellWidth = getHeight()
            String log = "Clicked team member on col " + (col+1);

            if (!status) {
                if (col < alphaTeam.getTeamMembers().size())
                    selected = col;
                else {
                    selected = -1;
                    status = !status;
                }
            } else if (col < alphaTeam.getTeamMembers().size()) {
                selected = col;
                status = !status;
            }

            teamListView.setSelected(selected);
            teamListView.invalidate();
            return false;
        }
    };
    // TODO method to save changes to team
}


