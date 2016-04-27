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
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.gamelogic.Piece;

/**
 * Created by addykim on 3/24/16.
 */
public class BoardView extends View {

    private static final String TAG = "BoardView";

    private Board boardLogic;
    private Paint mPaint;
    private Paint tintPaint;
    private ColorFilter filter;

    private int selectedChar;
    private ArrayList<Integer> targets;

    /* Used to determine stroke width of board lines */
    private static final int GRID_LINE_WIDTH = 6;
    private static final int HP_BAR_WIDTH = 10;

    /* Constructor code based on Spring 2016 CS 371M tutorial code */
    public BoardView(Context context) {
        super(context);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tintPaint = new Paint(Color.GRAY);
        filter = new LightingColorFilter(Color.GRAY, Color.BLUE);
        // TODO change color of filter
        tintPaint.setColorFilter(filter);
        selectedChar = -1;
        targets = new ArrayList<Integer>();
    }

    /* Draws the lines on the board */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Determine the width and height of the View
//        Log.d(TAG, "Drawing board");
        int boardWidth = getWidth();
        int boardHeight = getHeight();
        int cellWidth = getBoardCellWidth();
        int cellHeight = getBoardCellHeight();
        // TODO change board UI/colors
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_LINE_WIDTH);

        // Draw the vertical and horizontal board lines
        // Vertical lines
        for (int i = 1; i < 3; i++) {
            int yLine = cellWidth*i;
            canvas.drawLine(yLine, 0, yLine, boardHeight, mPaint);
        }
        // Horizontal lines
        for (int i = 1; i < 7; i++) {
            // Change line color in the middle of board
            //TODO change this color based on UI
            if (i == 3)
                mPaint.setColor(Color.BLACK);

            int xLine = cellHeight*i;
            canvas.drawLine(0, xLine, boardWidth, xLine, mPaint);
            // Changing back to old line color
            // TODO change board UI/color
            if (i == 3)
                mPaint.setColor((Color.LTGRAY));
        }

        /* Draw characters */
        drawChar(canvas, cellWidth, cellHeight);
        /* Draw circles on top of characters */
        drawCircle(canvas, cellWidth, cellHeight);
        drawHP(canvas, cellWidth, cellHeight);
    }

    /* Draws character bitmaps */
    public void drawChar(Canvas canvas, int cellWidth, int cellHeight) {
        Bitmap image;
        Piece occupant;
        int left, top, bottom, right;
        int row, col;
        for (int i=0; i<18; i++) {
            row = i/3;
            col = i%3;
            left = (col*cellWidth);
            top = (row*cellHeight);
            right = ((col+1)*cellWidth);
            bottom = ((row+1)*cellHeight);
//            Log.d(TAG, "Drawing character in grid " + row + ", " + col);
            occupant = boardLogic.getBoardOccupant(row, col);
            if (occupant != null) {

                switch (occupant.getPieceClass()) {
                    case VILLAGER:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.villager_normal);
                        break;
                    case MAGICIAN:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.wizard_normal);
                        break;
                    case KNIGHT:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.knight_normal);
                        break;
                    case ARCHER:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.archer_normal);
                        break;
                    case APPRENTICE:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.apprentice_normal);
                        break;
                    case FIGHTER:
//                        TODO change civilian to fighter
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.civilian_normal);
                        break;
                    case BADTEETH:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_badteeth);
                        break;
                    case HORNEDFROG:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_hornedfrog);
                        break;
                    default:
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.move);
                }
                if (image != null) {
                    if (occupant.isHasMoved()) {
                        canvas.drawBitmap(image, null,
                                new Rect(left, top, right, bottom),
                                tintPaint);
                    } else {
                        canvas.drawBitmap(image, null,
                                new Rect(left, top, right, bottom),
                                null);
                    }
                }
            }
        }
    }

    /* Draws circles around characters when aiming to use a skill */
    public void drawCircle(Canvas canvas, int cellWidth, int cellHeight) {
        mPaint.setStyle(Paint.Style.STROKE);
        int row, col, index;
        int left, top, right, bottom;
        for (int i=0; i<18; i++) {
            row = i/3;
            col = i%3;
            index = row*3+col;
            left = (col*cellWidth)+GRID_LINE_WIDTH/2;
            top = (row*cellHeight)+GRID_LINE_WIDTH/2;
            right = ((col+1)*cellWidth)-GRID_LINE_WIDTH/2;
            bottom = ((row+1)*cellHeight)-GRID_LINE_WIDTH/2;
            /* Draw a circle around potential characters */
            // TODO
            /* Draws a circle around your selected character */
            if (index == selectedChar && selectedChar > -1) {
                mPaint.setColor((Color.BLUE));
                canvas.drawOval(new RectF(left, top, right, bottom), mPaint);
            }
            /* Potential targets */
            if (targets != null && targets.contains(index) && index > -1) {
                mPaint.setColor((Color.RED));
                canvas.drawOval(new RectF(left, top, right, bottom), mPaint);
            }
//            TODO change color based on whehter it is enemy or your own character
        }
    }

    /* Get board cell width using view's width */
    public int getBoardCellWidth() { return getWidth() / 3; }

    /* Get board cell height using view's height */
    public int getBoardCellHeight() { return getHeight() / 6; }

    public void setCharacter(int character) { selectedChar = character; }

    public void setTargets(ArrayList<Integer> targets) {
        this.targets = targets;
    }

    public void setGame(Board logic) { boardLogic = logic; }

    /* Redraws the hp bar when necessary */
    public void drawHP(Canvas canvas, int cellWidth, int cellHeight) {
        // Future TODO add fancy animation of hp bar dropping
        mPaint.setStyle(Paint.Style.FILL);
        int row, col;
        float hp_left, hp_right, hp_bottom, hp_top, hp_max_right;
        for (int i=0; i<18; i++) {
            row = i / 3;
            col = i % 3;
            if (boardLogic.resolveGrid(row, col) != 0) {
                float percentHP = (float)boardLogic.getHp(row,col)/(float)boardLogic.getMaxHp(row, col);
                /* Draw remaining hp */
                mPaint.setColor(Color.GREEN);
                hp_left = (col*cellWidth)+GRID_LINE_WIDTH/2;
                hp_top = ((row+1)*cellHeight)-HP_BAR_WIDTH-GRID_LINE_WIDTH/2;
                hp_max_right = ((col+1)*cellWidth)-GRID_LINE_WIDTH/2;
                hp_bottom = hp_top+HP_BAR_WIDTH;
                if (percentHP == (float)1) {
                    canvas.drawRect(new RectF(
                                    (hp_left),
                                    (hp_top),
                                    (hp_max_right),
                                    (hp_bottom)),
                            mPaint);
                } else {
                    hp_right = hp_left+((hp_max_right-hp_left)*percentHP);
                    canvas.drawRect(new RectF(
                                    (hp_left),
                                    (hp_top),
                                    (hp_right),
                                    (hp_bottom)),
                            mPaint);
                /* Draw empty portion of HP */
                    mPaint.setColor(Color.GRAY);
                    canvas.drawRect(new RectF(
                                    (hp_right),
                                    (hp_top),
                                    (hp_max_right),
                                    (hp_bottom)),
                            mPaint);
                }
            }
        }
    }

    /* Dulls the skills to indicate that the user cannot click on anything */
    public void dullBoard(boolean dull) {

        if (dull) {
            setBackgroundColor(Color.GRAY);
            Log.d(TAG, "Dulling board");
        } else {
            setBackgroundColor(Color.WHITE);
            Log.d(TAG, "Board set back to normal");
        }
        invalidate();
    }

    /* Call this method whenever a character(enemy or player) is hit
        TODO
     */
    public void gotAttacked() {

    }

}
