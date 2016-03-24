package askim.eratactics.gamelogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Board extends View {

    private Paint mPaint;

    /* Used to determine stroke width of board lines */
    private static final int GRID_LINE_WIDTH = 6;

    /* Constructor code based on Spring 2016 CS 371M tutorial code */
    public Board(Context context) {
        super(context);
        initialize();
    }

    public Board(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Determine the width and height of the View
        int boardWidth = getWidth();
        int boardHeight = getHeight();
        // TODO change board UI/colors
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(GRID_LINE_WIDTH);
        // Draw the vertical and horizontal board lines
        // Vertical lines
        for (int i = 1; i < 3; i++) {
            int yLine = getBoardCellWidth()*i;
            canvas.drawLine(yLine, 0, yLine, boardHeight, mPaint);
        }
        // Horizontal lines
        for (int i = 1; i < 6; i++) {
            // Change line color in the middle of board
            //TODO change this color based on UI
            if (i == 3)
                mPaint.setColor(Color.BLACK);

            int xLine = getBoardCellHeight() * i;
            canvas.drawLine(0, xLine, boardWidth, xLine, mPaint);
            // Changing back to old line color
            // TODO change board UI/color
            if (i == 3)
                mPaint.setColor((Color.LTGRAY));
        }
    }

    /* Get board cell width using view's width */
    public int getBoardCellWidth() {
        return getWidth() / 3;
    }

    /* Get board cell height using view's height */
    public int getBoardCellHeight() {
        return getHeight() / 6;
    }
}
