package askim.eratactics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.Piece;
import askim.eratactics.gamelogic.Team;

/**
 * Created by nunuloop on 4/29/16.
 */
public class TeamView extends View {

    private Team mTeam;

    private static final String TAG = "TeamView";
    private Paint mPaint;

    private static final int GRID_LINE_WIDTH = 6;

    /* Constructor code based on Spring 2016 CS 371M tutorial code */
    public TeamView(Context context) {
        super(context);
        initialize();
    }

    public TeamView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public TeamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /* Draws the lines on the board */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Determine the width and height of the View
//        Log.d(TAG, "Drawing board");
        int boardWidth = getWidth();
        int boardHeight = getHeight();
        int cellWidth = getBoardCellsize();
        int cellHeight = getBoardCellsize();
        // TODO change board UI/colors
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_LINE_WIDTH);

        // Draw the vertical and horizontal board lines
        // Vertical lines
        for (int i = 0; i < 4; i++) {
            int yLine = cellWidth * i;
            canvas.drawLine(yLine, 0, yLine, boardHeight, mPaint);
        }
        // Horizontal lines
        for (int i = 0; i < 4; i++) {
            int xLine = cellHeight * i;
            canvas.drawLine(0, xLine, boardWidth, xLine, mPaint);
        }

        /* Draw characters */
        drawChar(canvas, cellWidth, cellHeight);
    }

    /* Get board cell size using view's width */
    private int getBoardCellsize() {
        return getWidth() / 3;
    }

    private void drawChar(Canvas canvas, int cellWidth, int cellHeight) {
        Bitmap image;
        Adventurer occupant;
        int left, top, bottom, right;
        int row, col;
        for (int i=0; i<9; i++) {
            row = i/3;
            col = i%3;
            left = (col*cellWidth);
            top = (row*cellHeight);
            right = ((col+1)*cellWidth);
            bottom = ((row+1)*cellHeight);
//            Log.d(TAG, "Drawing character in grid " + row + ", " + col);
            occupant = mTeam.getAdventurer(i);
            if (occupant != null) {
                image = BitmapFactory.decodeResource(getResources(), Resources.getImageId(occupant.getAdventurerName()));
                if (image != null) {
                        canvas.drawBitmap(image, null,
                                new Rect(left, top, right, bottom),
                                null);

                }
            }
        }
    }

    public void setTeam(Team t) {
        this.mTeam = t;
    }
}
