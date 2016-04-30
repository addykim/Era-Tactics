package askim.eratactics.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

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

        /* Hard coded team composition */
        Team alphaTeam = new Team();
        Adventurer villager1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.VILLAGER)}, "Bob");
        Adventurer apprentice1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.APPRENTICE),
                new Equipment(EnumFile.Equipments.BASIC_POTION)}, "BOB!");
        Adventurer magician1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.MAGICIAN),
                new Equipment(EnumFile.Equipments.BASIC_WAND),
                new Equipment(EnumFile.Equipments.BASIC_POTION)}, "Still Bob");
        Adventurer archer1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.ARCHER),
                new Equipment(EnumFile.Equipments.BASIC_ARROW)}, "Uh.. Bob.");
        alphaTeam.addTeamMember(villager1);
        alphaTeam.putAdventurer(villager1, 2, false);
        alphaTeam.addTeamMember(apprentice1);
        alphaTeam.putAdventurer(apprentice1, 6, false);
        alphaTeam.addTeamMember(magician1);
        alphaTeam.putAdventurer(magician1, 3, false);
        alphaTeam.addTeamMember(archer1);
        alphaTeam.putAdventurer(archer1, 5, false);

        teamView = (TeamView) findViewById(R.id.preSetTeam);
        teamView.setTeam(alphaTeam);

        teamListView = (TeamListView) findViewById(R.id.preSetTeamList);
        teamListView.setTeamMembers(alphaTeam.teamMembers);

        // TODO tabbed view
    }

    // TODO method to save changes to team
}
