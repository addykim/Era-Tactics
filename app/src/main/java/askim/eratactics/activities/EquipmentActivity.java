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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_equipment);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        Bundle bundle = getIntent().getExtras();
        Adventurer adv = bundle.getParcelable("adventurer");

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


//        adv = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.MAGICIAN),
//                                             new Equipment(EnumFile.Equipments.BASIC_WAND),
//                                             new Equipment(EnumFile.Equipments.BASIC_POTION),
//                                             new Equipment(EnumFile.Equipments.BASIC_ARMOR),
//                                             new Equipment(EnumFile.Equipments.BASIC_HELMET)}, "Bob");

        for (int i = 0; i < 4; i++) {
            switch (adv.equipments[i].enumName) {
                case BASIC_ARMOR:
                    mImageView[i].setImageResource(R.drawable.suits);
                    break;
                case BASIC_HELMET:
                    if (adv.adventurerClass.className == EnumFile.ClassEnum.MAGICIAN)
                        mImageView[i].setImageResource(R.drawable.wizard_hat);
                    else
                        mImageView[i].setImageResource(R.drawable.helmet);
                    break;
                case BASIC_POTION:
                    mImageView[i].setImageResource(R.drawable.potion);
                    break;
                case BASIC_WAND:
                    mImageView[i].setImageResource(R.drawable.wand);
                    break;
                case BASIC_SHIELD:
                    mImageView[i].setImageResource(R.drawable.shield);
                    break;
                case BASIC_SWORD:
                    mImageView[i].setImageResource(R.drawable.melee);
                    break;
                case BASIC_ARROW:
                    mImageView[i].setImageResource(R.drawable.arrow);
                    break;
            }
        }

        // Set adventurer's name
        mTextView[6].setText(adv.getAdventurerName());
        characterView.setImageResource(Resources.getImageId(adv.getAdventurerClass()));

        for (int i = 0; i < 4; i++) {
            mImageView[i].setOnClickListener(new EquipmentClickListener(i));
            // TODO
//            mImageView[i].setImageResource(Resources.getImageId(ad))
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
            adv.setLeaderEquipment(equipmentPos);
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
