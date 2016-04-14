package askim.eratactics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import askim.eratactics.R;
import askim.eratactics.activities.TacticsGame;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.gamelogic.EnumFile;

/**
 * Created by addykim on 4/4/16.
 */
public class SkillView extends View {

    private static final String TAG = "SkillView";

    private static final int SKILL_DIFF = 15;

    private int selectedChar;

    private Board boardLogic;
    private TacticsGame game;
    private Paint mPaint;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(R.color.mainBackground));
        mPaint.setStrokeWidth(SKILL_DIFF);

        int width = getSkillListWidth();

        int height = (getSkillListHeight()-6*SKILL_DIFF)/6;
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
            Log.d(TAG, "Drawing skill at " + left + ", " + top + ", " + ", " + right + ", " + bottom);
            // TODO method to select appropriate skill image
            image = BitmapFactory.decodeResource(getResources(), R.drawable.move);
            canvas.drawBitmap(image, null,
                    new Rect(left, top, right, bottom),
                    null);
        }
    }

    /* Send in a value 0 through 2, and boolean if we want to highlight or unhighlight */
    private void highlightSkill(int skillNumber) {
        /* Unhighlight everything */
        for (int index = 0; index < 3; index++) {
//            firstSkillButton.setAlpha(OPAQUE);
//            secondSkillButton.setAlpha(OPAQUE);
//            thirdSkillButton.setAlpha(OPAQUE);
//            fourthSkillButton.setAlpha(OPAQUE);
        }
//        if (skillNumber == 0)
//            firstSkillButton.setAlpha(HIGHLIGHT);
//        else if (skillNumber == 1)
//            secondSkillButton.setAlpha(HIGHLIGHT);
//        else if (skillNumber == 2)
//            thirdSkillButton.setAlpha(HIGHLIGHT);
//        else if (skillNumber == 3)
//            fourthSkillButton.setAlpha(HIGHLIGHT);
//        else
//            Log.d(TAG, "Un-highlighting all skill");
    }

    public void setGame(Board logic) { logic = boardLogic; }

    public void setCharacter(int character) { selectedChar = character; }

    public int getSkillListHeight() { return getHeight(); }
    public int getSkillListWidth() { return getWidth(); }

}
