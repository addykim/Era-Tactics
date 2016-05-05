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
    private ImageView hatImage;
    private ImageView leftImage;
    private ImageView rightImage;
    private ImageView armorImage;
    private ImageView characterImage;

    private TextView atkText;
    private TextView defText;
    private TextView magText;
    private TextView resText;
    private TextView skillText;
    private TextView leaderText;
    private TextView characterName;

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

        Intent intent = getIntent();
        advId = intent.getLongExtra("advId", 1);

        characterImage = (ImageView) findViewById(R.id.equipment_character);
        // This is here purely for debugging purposes.

        hatImage = (ImageView)findViewById(R.id.equipment_hat);
        leftImage = (ImageView)findViewById(R.id.equipment_left);
        rightImage = (ImageView)findViewById(R.id.equipment_right);
        armorImage = (ImageView)findViewById(R.id.equipment_armor);

        atkText = (TextView)findViewById(R.id.equipment_character_atk_value);
        defText = (TextView)findViewById(R.id.equipment_character_def_value);
        magText = (TextView)findViewById(R.id.equipment_character_mag_value);
        resText = (TextView)findViewById(R.id.equipment_character_res_value);
        skillText = (TextView)findViewById(R.id.equipment_skills_text);
        leaderText = (TextView)findViewById(R.id.equipment_leader_skills_text);
        characterName = (TextView) findViewById(R.id.equipment_character_name);

        // Set adventurer's information
        adv = Adventurer.findById(Adventurer.class, advId);
        characterName.setText(adv.getAdventurerName());
        characterImage.setImageResource(Resources.getImageId(adv.getAdventurerClassAsString()));

        characterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adv.getHeadEquipment() != null)
                    Log.d(TAG, "Head equipment: " + adv.getHeadEquipment().getName());
                if (adv.getLeftEquipment() != null)
                    Log.d(TAG, "Left equipment: " + adv.getLeftEquipment().getName());
                if (adv.getRightEquipment() != null)
                    Log.d(TAG, "Right equipment: " + adv.getRightEquipment().getName());
                if (adv.getBodyEquipment() != null)
                    Log.d(TAG, "Body equipment: " + adv.getBodyEquipment().getName());

            }
        });

        setImages();
        setStatText();
        setLeaderText();
    }


    public void setLeaderText() { leaderText.setText(adv.getLeaderSkillDescription()); }

    /* Set all the stats */
    public void setStatText() {
        boolean[] allFalse = new boolean[4];
        int atk = adv.getAtk(false, allFalse);
        int def = adv.getDef(false, allFalse);
        int mag = adv.getMag(false, allFalse);
        int res = adv.getRes(false, allFalse);
        atkText.setText(Integer.toString(atk));
        defText.setText(Integer.toString(def));
        magText.setText(Integer.toString(mag));
        resText.setText(Integer.toString(res));
    }

    /* Checks the imageID first before setting. When imageId == -1 that means that there is no
     * equipment attached
     */
    public void setImages() {
        // Set all images
        int imageId = -1;
        if (adv.getHeadEquipment() != null) {
            imageId = Resources.getEquipmentImageId(adv.getAdventureClassAsEnum(),
                    adv.getHeadEquipment().getEnumName());
            Log.d(TAG, "Head id: " + imageId);
            if (imageId != -1)
                hatImage.setImageResource(imageId);
        }
        if (adv.getLeftEquipment() != null) {
            imageId = Resources.getEquipmentImageId(adv.getAdventureClassAsEnum(),
                    adv.getLeftEquipment().getEnumName());
            Log.d(TAG, "Left id: " + imageId);
            if (imageId != -1)
                leftImage.setImageResource(imageId);
        }
        if (adv.getRightEquipment() != null) {
            imageId = Resources.getEquipmentImageId(adv.getAdventureClassAsEnum(),
                    adv.getRightEquipment().getEnumName());
            Log.d(TAG, "Right id: " + imageId);
            if (imageId != -1)
                rightImage.setImageResource(imageId);
        }
        if (adv.getBodyEquipment() != null) {
            imageId = Resources.getEquipmentImageId(adv.getAdventureClassAsEnum(),
                    adv.getBodyEquipment().getEnumName());
            Log.d(TAG, "Body id: " + imageId);
            if (imageId != -1)
                armorImage.setImageResource(imageId);
        }
    }

    private class EquipmentClickListener implements View.OnClickListener, View.OnLongClickListener {
        EnumFile.EquipmentPos equipmentPos;

        public EquipmentClickListener(EnumFile.EquipmentPos pos) { this.equipmentPos = pos; }

        // Call setLeaderEquipment(int pos) on the adventurer to toggle this
        // pos: 0 - head, 1 - lefthand, 2 - righthand, 3 - body
        @Override
        public void onClick(View view) { setLeaderText(); }

        // TODO: Click and hold the equipment buttons will bring up the inventory to pick a replacement
        // Call availableEquipment(int pos, Arraylist<Equipment> equip) on the adventurer to get
        // the list of inventory that matches the given position, need to provide the list of ALL
        // equipment that the player has (This is not currently created)
        // pos: 0 - head, 1 - lefthand, 2 - righthand, 3 - body
        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "Long click");
            // TODO does not work yet
            Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
            //TODO send in equipment details activity
            //TODO on return get the new updated information
            startActivity(intent);
            // TODO do someting on the way back
            return true;
        }

    }
}
