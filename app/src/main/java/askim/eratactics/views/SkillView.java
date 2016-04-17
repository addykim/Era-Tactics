package askim.eratactics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import askim.eratactics.R;
import askim.eratactics.activities.TacticsGame;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.gamelogic.EnumFile;

/**
 * Created by addykim on 4/4/16.
 */
public class SkillView extends View {

    private static final String TAG = "SkillView";



    public static final int SKILL_DIFF = 15;

    private int height;

    private int selectedChar;
    private ArrayList<EnumFile.SkillsEnum> skillList;
    private EnumFile.SkillsEnum selectedSkill;

    private Board boardLogic;
    private TacticsGame game;
    private Paint mPaint;
    private Paint tintPaint;
    private ColorFilter filter;

    /* Constructor code based on Spring 2016 CS 371M tutorial code */
    public SkillView(Context context) {
        super(context);
        initialize();
    }

    public SkillView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public SkillView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tintPaint = new Paint(Color.GRAY);
        filter = new LightingColorFilter(Color.GRAY, Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(R.color.mainBackground));
        mPaint.setStrokeWidth(SKILL_DIFF);

        int width = getSkillListWidth();
        height = (getSkillListHeight()-6*SKILL_DIFF)/6;

        Bitmap image;
        int top, left, right, bottom, xLine;
        for (int i=0; i<6; i++) {
            // Draw the line
            xLine = (height+SKILL_DIFF)*(i+1);
            canvas.drawLine(0, xLine, width, xLine, mPaint);

            // Draw the bitmap
            left = 0;
            top = i*(height+SKILL_DIFF);
            bottom = top+height;
            right = width;
            Log.d(TAG, "Drawing skill at " + left + ", " + top + ", " + right + ", " + bottom);
            // TODO method to select appropriate skill image
            // TODO make sure skillIst is set to null when finshed resolving
            if (skillList != null && i < skillList.size()) {
                image = getImageBitmap(skillList.get(i));
                /* Highlights the skill if it is the selected skill */
                if (skillList.get(i) == selectedSkill) {
                    Log.d(TAG, "Tinting skill " + selectedSkill.toString());
                    canvas.drawBitmap(image, null,
                            new Rect(left, top, right, bottom),
                            tintPaint);
                } else {
                    canvas.drawBitmap(image, null,
                            new Rect(left, top, right, bottom),
                            null);
                }
            }
            // TODO will I need to actually draw a null bitmap?
            // else draw nothing

        }
    }

    private Bitmap getImageBitmap(EnumFile.SkillsEnum skill) {
        Bitmap image;
        switch (skill) {
            case MOVE:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.move);
                break;
            case FIREBALL:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
                break;
            case HEAL:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.first_aid);
                break;
            case LIGHTNING:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.lightning);
                break;
            case BLOCK:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.shield);
                break;
            case PUNCH:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.punch);
                break;
            case STRIKE:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.melee);
                break;
            // TODO potion
//                image = BitmapFactory.decodeResource(getResources(), R.drawable.potion);
//                break;
            default:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.wizard_hit);
        }
        Log.d(TAG, "Returning skill " + skill.toString());
        return image;
    }


    /* Assigns boardlogic object to this skillView */
    public void setGame(Board logic) { logic = boardLogic; }

    /* Sets the skill and redraws the */
    public void setSkill(EnumFile.SkillsEnum skill) {
        selectedSkill = skill;
        invalidate();
    }

    /* Sets the skill list and displays the skills */
    public void setSkillList(ArrayList<EnumFile.SkillsEnum> list) {
        skillList = list;
        invalidate();
    }

    // TODO not sure if this class is needed quite yet
    public void setCharacter(int character) { selectedChar = character; }

    public int getSkillListHeight() { return getHeight(); }
    public int getSkillListWidth() { return getWidth(); }
    public int getSkillCellHeight() { return height; }

}