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
import android.view.View;

import java.util.ArrayList;

import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.Board;

/**
 * Created by nunuloop on 4/30/16.
 */
public class TeamListView extends View {
    private static final String TAG = "TeamListView";

    private Paint mPaint;
    private ArrayList<Adventurer> teamMembers;

    /* Used to determine stroke width of board lines */
    private static final int GRID_LINE_WIDTH = 6;

    /* Constructor code based on Spring 2016 CS 371M tutorial code */
    public TeamListView(Context context) {
        super(context);
        initialize();
    }

    public TeamListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public TeamListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int listWidth = getWidth();
        int listHeight = getHeight();
        int cellSize = listHeight;

        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_LINE_WIDTH);

        // Vertical lines
        for (int i = 0; i < 6; i++) {
            int yLine = cellSize * i;
            canvas.drawLine(yLine, 0, yLine, listHeight, mPaint);
        }
        for (int i = 0; i < 2; i++) {
            int xLine = cellSize * i;
            canvas.drawLine(0, xLine, listWidth, xLine, mPaint);
        }

        drawChar(canvas, cellSize);
    }

    private void drawChar(Canvas canvas, int cellSize) {
        Bitmap image;
        Adventurer occupant;
        int left, top, bottom, right;
        int row, col;
        for (int i=0; i<teamMembers.size(); i++) {
            left = i * cellSize;
            top = 0;
            right = (i + 1) * cellSize;
            bottom = cellSize;
//            Log.d(TAG, "Drawing character in grid " + row + ", " + col);
            occupant = teamMembers.get(i);
            if (occupant != null) {
                image = BitmapFactory.decodeResource(getResources(), Resources.getImageId(occupant.getAdventurerClass()));
                if (image != null) {
                    canvas.drawBitmap(image, null,
                            new Rect(left, top, right, bottom),
                            null);

                }
            }
        }
    }

    public void setTeamMembers(ArrayList<Adventurer> members) {
        teamMembers = members;
    }
}