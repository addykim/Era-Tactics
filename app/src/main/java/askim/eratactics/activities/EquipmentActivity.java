package askim.eratactics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.EnumFile;
import askim.eratactics.gamelogic.Equipment;
import askim.eratactics.views.Resources;

/**
 * Created by addykim on 4/12/16.
 */
public class EquipmentActivity extends AppCompatActivity {

    private static final String TAG = "Equipment Activity";
    private ImageView[] mImageView;
    private ImageView characterView;

    private TextView[] mTextView;

    private Adventurer adv;

    private Long advId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_equipment);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        // TODO INTENT
        Intent intent = getIntent();
        advId = intent.getLongExtra("advId", 1);

        characterView = (ImageView) findViewById(R.id.equipment_character);
        mImageView = new ImageView[4];
        mImageView[0] = (ImageView)findViewById(R.id.equipment_hat);
        mImageView[1] = (ImageView)findViewById(R.id.equipment_left);
        mImageView[2] = (ImageView)findViewById(R.id.equipment_right);
        mImageView[3] = (ImageView)findViewById(R.id.equipment_armor);

        mTextView = new TextView[7];
        mTextView[0] = (TextView)findViewById(R.id.equipment_character_atk_value);
        mTextView[1] = (TextView)findViewById(R.id.equipment_character_def_value);
        mTextView[2] = (TextView)findViewById(R.id.equipment_character_mag_value);
        mTextView[3] = (TextView)findViewById(R.id.equipment_character_res_value);
        mTextView[4] = (TextView)findViewById(R.id.equipment_skills_text);
        mTextView[5] = (TextView)findViewById(R.id.equipment_leader_skills_text);
        mTextView[6] = (TextView) findViewById(R.id.equipment_character_name);


        adv = Adventurer.findById(Adventurer.class, advId);

        // Set adventurer's name
        mTextView[6].setText(adv.getAdventurerName());
        characterView.setImageResource(Resources.getImageId(adv.getAdventurerClassAsString()));

        for (int i = 0; i < 4; i++) {
            mImageView[i].setOnClickListener(new EquipmentClickListener(i));
        }
        // TODO set the equipment images
        boolean[] allFalse = new boolean[4];
        int atk = adv.getAtk(false, allFalse);
        int def = adv.getDef(false, allFalse);
        int mag = adv.getMag(false, allFalse);
        int res = adv.getRes(false, allFalse);
        mTextView[0].setText(Integer.toString(atk));
        mTextView[1].setText(Integer.toString(def));
        mTextView[2].setText(Integer.toString(mag));
        mTextView[3].setText(Integer.toString(res));

        if (adv.getEquipment(EnumFile.EquipmentPos.HEAD)!= null) {
            mImageView[0].setImageResource(Resources.getEquipmentImageId(
                    adv.getAdventureClassAsEnum(), adv.getEquipment(EnumFile.EquipmentPos.HEAD).getEnumName()));
        }
        if (adv.getEquipment(EnumFile.EquipmentPos.LEFT) != null) {
            mImageView[1].setImageResource(Resources.getEquipmentImageId(
                    adv.getAdventureClassAsEnum(), adv.getEquipment(EnumFile.EquipmentPos.LEFT).getEnumName()));
        }
        if (adv.getEquipment(EnumFile.EquipmentPos.RIGHT) != null) {
            mImageView[2].setImageResource(Resources.getEquipmentImageId(
                    adv.getAdventureClassAsEnum(), adv.getEquipment(EnumFile.EquipmentPos.RIGHT).getEnumName()));

        }
        if (adv.getEquipment(EnumFile.EquipmentPos.BODY) != null) {
            mImageView[3].setImageResource(Resources.getEquipmentImageId(
                    adv.getAdventureClassAsEnum(), adv.getEquipment(EnumFile.EquipmentPos.BODY).getEnumName()));

        }

        mTextView[5].setText(adv.getLeaderSkillDescription());
    }

    // TODO: OnClick the equipment buttons will toggle the leader skill of the clicked equipment



    // TODO: Click and hold the equipment buttons will bring up the inventory to pick a replacement
            // Call availableEquipment(int pos, Arraylist<Equipment> equip) on the adventurer to get
            // the list of inventory that matches the given position, need to provide the list of ALL
            // equipment that the player has (This is not currently created)
            // pos: 0 - head, 1 - lefthand, 2 - righthand, 3 - body

    private class EquipmentClickListener implements View.OnClickListener, View.OnLongClickListener {
        int equipmentPos;

        public EquipmentClickListener(int pos) {
            this.equipmentPos = pos;
        }

        // Call setLeaderEquipment(int pos) on the adventurer to toggle this
        // pos: 0 - head, 1 - lefthand, 2 - righthand, 3 - body
        @Override
        public void onClick(View view) {
//            adv.setLeaderEquipment(equipmentPos);
            mTextView[5].setText(adv.getLeaderSkillDescription());
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "Long click");
            // TODO does not work yet
            Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
            startActivity(intent);
            return true;
        }

    }
}
