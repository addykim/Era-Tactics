package askim.eratactics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private Bitmap piecesBitmaps[][] = new Bitmap[6][3];

    private int selectedChar;
    private ArrayList<Integer> targets;

    /* Used to determine stroke width of board lines */
    private static final int GRID_LINE_WIDTH = 6;

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
        for (int i = 1; i < 6; i++) {
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
        mPaint.setStyle(Paint.Style.STROKE);
        drawCircle(canvas, cellWidth, cellHeight);
    }

    /* Draws character bitmaps */
    public void drawChar(Canvas canvas, int cellWidth, int cellHeight) {
        Bitmap image;
        Piece occupant;
        int row, col;
        for (int i=0; i<18; i++) {
            row = i/3;
            col = i%3;
            Log.d(TAG, "Drawing character in grid " + row + ", " + col);
//            try {
                occupant = boardLogic.getBoardOccupant(row, col);
//            } catch (Exception e) {
//                Log.d(TAG, e.getMessage());
//            }
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
                    canvas.drawBitmap(image, null,
                            new Rect(
                                    (col * cellWidth),
                                    (row * cellHeight),
                                    (col + 1) * cellWidth,
                                    (row + 1) * cellHeight),
                            null);
                }
            }
        }
    }



    /* Draws circles around characters when aiming to use a skill */
    public void drawCircle(Canvas canvas, int cellWidth, int cellHeight) {
        int row, col, index;
        for (int i=0; i<18; i++) {
            row = i/3;
            col = i%3;
            index = row*3+col;
            if (index == selectedChar && selectedChar > -1) {
                mPaint.setColor((Color.BLUE));
                canvas.drawOval(new RectF(
                                (col * cellWidth),
                                (row * cellHeight),
                                (col + 1) * cellWidth,
                                (row + 1) * cellHeight),
                        mPaint);
            }
            /* Potential targets */
            if (targets != null && targets.contains(index) && index > -1) {
                mPaint.setColor((Color.RED));
                canvas.drawOval(new RectF(
                                (col * cellWidth),
                                (row * cellHeight),
                                (col + 1) * cellWidth,
                                (row + 1) * cellHeight),
                        mPaint);
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

//    public void moveBitmapImage(int srcRow, int srcCol, int destRow, int destCol) {
//        piecesBitmaps[destRow][destCol] = piecesBitmaps[srcRow][srcCol];
//        piecesBitmaps[srcRow][srcCol] = null;
//    }

    /* Redraws the hp bar when necessary */
    public void drawHP() {
        // Future TODO add fancy animation of hp bar dropping

    }

    /* Call this method whenever a character(enemy or player) is hit
        TODO
     */
    public void gotAttacked() {

    }

}
