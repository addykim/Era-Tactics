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

import askim.eratactics.R;
import askim.eratactics.gamelogic.Board;

/**
 * Created by addykim on 3/24/16.
 */
public class BoardView extends View {

    private static final String TAG = "BoardView";

    private Board boardLogic;
    private Paint mPaint;
    private Bitmap piecesBitmaps[][];

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
        piecesBitmaps = new Bitmap[6][3];
        // DEBUG
        for (int row=0; row<6; row++) {
            for (int col=0; col<3; col++) {
                piecesBitmaps[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.apprentice_normal);
            }
        }
    }

    public void setGame(Board boardLogic) {
        this.boardLogic = boardLogic;
    }

    /* Draws the lines on the board */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Determine the width and height of the View
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
        drawCircle(canvas, cellWidth, cellHeight);
    }

    /* Draws character bitmaps */
    public void drawChar(Canvas canvas, int cellWidth, int cellHeight) {
        for (int row=0; row<6;row++) {
            for (int col=0; col<3; col++) {
                if (piecesBitmaps[row][col] != null) {
                    Log.d(TAG, "Drawing bitmap at row " + (row+1) + ",col " + (col+1));
                    canvas.drawBitmap(piecesBitmaps[row][col], null,
                            new Rect(row*cellWidth, row*cellHeight, (row+1)*cellWidth, (col+1)*cellHeight),
                            null);
                }
            }
        }
    }

    /* Draws circles around characters when aiming to use a skill */
    public void drawCircle(Canvas canvas, int width, int height) {
        for (int row=0; row<6; row++) {
            for (int col=0; col<3; col++) {
                canvas.drawOval(new RectF(width*row, height*col, (width+1)*row, (height+1)*col),
                        mPaint);
            }
        }
    }

    /* Get board cell width using view's width */
    public int getBoardCellWidth() { return getWidth() / 3; }

    /* Get board cell height using view's height */
    public int getBoardCellHeight() { return getHeight() / 6; }
}
