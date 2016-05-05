package askim.eratactics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
    private Team alphaTeam;

    // 0-4 if TeamList, 5-13 if on grid
    private int selected;

    // True Select; False Place;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selected = -1;
        status = true;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teams);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        alphaTeam = Team.findById(Team.class, 1);
        alphaTeam.setTeamMembers();
        teamView = (TeamView) findViewById(R.id.preSetTeam);
        teamView.setTeam(alphaTeam);

        teamView.setOnTouchListener(teamTouchListener);

        teamListView = (TeamListView) findViewById(R.id.preSetTeamList);
        teamListView.setTeamMembers(alphaTeam.getTeamMembers());
        teamListView.setOnTouchListener(teamListTouchListener);

        ImageView tutorial = (ImageView) findViewById(R.id.gameTutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorialIntent = new Intent(getApplicationContext(), TutorialActivity.class);
                tutorialIntent.putExtra("tutorial", "teams");
                startActivity(tutorialIntent);
            }
        });
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
            teamView.setSelected(Math.max(-1, selected - 5));
            teamView.invalidate();
            return false;
        }
    };

    private View.OnTouchListener teamTouchListener = new View.OnTouchListener() {
        /* Rows 1-3 are for the enemies, 4-6 are for the player */
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int row = (int) event.getY() / teamView.getBoardCellsize();
            int col = (int) event.getX() / teamView.getBoardCellsize();
            int pos = row * 3 + col;
            String log = "Clicked team member on col " + (col+1);

            if (!status && selected < 5) {
                    alphaTeam.putAdventurer(alphaTeam.getTeamMembers().get(selected), pos, false);
                    selected = -1;
                    status = !status;
            } else if (!status) {
                alphaTeam.swapAdventurer(selected - 5, pos);
                selected = -1;
                status = !status;
            }
            else if (alphaTeam.getAdventurer(pos) != null) {
                selected = pos + 5;
                status = !status;
            }

            teamView.setSelected(Math.max(-1, selected - 5));
            teamView.setTeam(alphaTeam);
            teamListView.setSelected(selected);
            teamListView.invalidate();
            teamView.invalidate();
            return false;
        }
    };
    // TODO method to save changes to team
}


