package askim.eratactics.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import askim.eratactics.gamelogic.Board;

/**
 * Created by nunuloop on 4/30/16.
 */
public class TeamListView extends View {
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
        tintPaint = new Paint(Color.GRAY);
        filter = new LightingColorFilter(Color.GRAY, Color.BLUE);
        // TODO change color of filter
        tintPaint.setColorFilter(filter);
        selectedChar = -1;
        targets = new ArrayList<Integer>();
    }
}
