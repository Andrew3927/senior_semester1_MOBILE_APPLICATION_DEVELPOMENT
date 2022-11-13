package com.example.whackamole;

import static com.example.whackamole.GameActivity.MP1;
import static com.example.whackamole.GameActivity.MP2;
import static com.example.whackamole.GameActivity.MP4;
import static com.example.whackamole.GameActivity.MP3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SurfaceView: provides a dedicated drawing surface embedded inside of a view hierarchy
 */
public class GameView extends SurfaceView {

    public static final int FPS = 30;

    // Bitmap: one of the most important class for image processing -> (color transformation, crop,
    // rotation, zoom in/out, etc)
    public static Bitmap BACK_GROUND, BUTTOM_TOP, BUTTOM_MIDDLE, BUTTOM_BOTTOM, HEART;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    private static Paint _PAINT;
    private Mole mole = new Mole();
    private int score = 0;
    private int lives = 3;
    private int time = 30;
    private int timeCounter = 0;
    private Activity activity;

    /**
     * Initialize the GameView with Display Metrics, retrieve the image resources and assign them to
     * Bitmap object
     *
     * @param context
     * @param activity
     */
    public GameView(Context context, Activity activity) {
        super(context);
        _PAINT = new Paint();
        _PAINT.setTextSize(80);

        this.activity = activity;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getRealMetrics(displayMetrics);

        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;

        // retrieve the necessary image resources and assign them to Bitmap object
        Mole.MOLE_PNG = getScaledPNG(R.drawable.mole, .20, .13);
        BACK_GROUND = getScaledPNG(R.drawable.bg);
        BUTTOM_TOP = getScaledPNG(R.drawable.bg_top);
        BUTTOM_MIDDLE = getScaledPNG(R.drawable.bg_middle);
        BUTTOM_BOTTOM = getScaledPNG(R.drawable.bg_bottom);
        HEART = getScaledPNG(R.drawable.heart, .1, .05);
        setWillNotDraw(false); //Why is this default. Come on Google!

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mole.getAnimationFrame() < 10)
                    mole.setAnimationFrame(mole.getAnimationFrame() + 1);
                timeCounter++;
                if (timeCounter >= FPS) {
                    time--;
                    timeCounter = 0;
                }
                postInvalidate();
            }
        }, 0, 1000 / FPS);
    }

    /**
     * Give an image resources without specifying scale and return a Bitmap.
     *
     * @param id R.drawable.nameOfResources
     * @return Return a Bitmap
     */
    public Bitmap getScaledPNG(int id) {
        return getScaledPNG(id, 1, 1);
    }

    /**
     * Give an image resources with specific scale and return a Bitmap.
     *
     * @param id          R.drawable.nameOfResource
     * @param widthScale
     * @param heightScale
     * @return Return a Bitmap to process the image
     */
    public Bitmap getScaledPNG(int id, double widthScale, double heightScale) {
        // getResources(): Android resource system keeps track of all non-code assets
        //  associated with an application.
        Bitmap png = BitmapFactory.decodeResource(getResources(), id);
        return Bitmap.createScaledBitmap(png,
                (int) (SCREEN_WIDTH * widthScale),
                (int) (SCREEN_HEIGHT * heightScale), true);
    }

    /**
     * Override the View method.
     *  The onDraw() method is called whenever android thinks that your view should be redrawn.
     * @param canvas The Canvas class holds the "draw" calls.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // If the game is over:
        if (lives == 0 || time == 0) {
            // drawText(): Draw the text, with origin at (x, y), using the specified paint.
            canvas.drawText("Score: " + score, 500, 600, _PAINT);

            // Sending data back to the Main Activity before closing the activity
            activity.setResult(score);
            // finish(): Call this when your activity is done and should be closed.
            activity.finish();
        }

        // If the game is not done yet:
        canvas.drawBitmap(BACK_GROUND, 0, 0, _PAINT);
        if (mole.getHoleY() <= 0)
            mole.drawMole(canvas, _PAINT);
        canvas.drawBitmap(BUTTOM_TOP, 0, 0, _PAINT);
        if (mole.getHoleY() == 1)
            mole.drawMole(canvas, _PAINT);
        canvas.drawBitmap(BUTTOM_MIDDLE, 0, 0, _PAINT);
        if (mole.getHoleY() >= 2)
            mole.drawMole(canvas, _PAINT);
        canvas.drawBitmap(BUTTOM_BOTTOM, 0, 0, _PAINT);
        if (lives >= 1)
            canvas.drawBitmap(HEART, 950, 30, _PAINT);
        if (lives >= 2)
            canvas.drawBitmap(HEART, 900, 30, _PAINT);
        if (lives >= 3)
            canvas.drawBitmap(HEART, 850, 30, _PAINT);

//        _PAINT.setTextSize(80);
        canvas.drawText("Score: " + score, 50, 100, _PAINT);
        canvas.drawText("Time: " + time, 50, 200, _PAINT);
    }

    /**
     * Mainly to determine if the user correct lick the mole icon.
     * @param event The MotionEvent will get passed by the system.
     * @return Return true if user successfully click the mole icon, false, otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();  // or getRawX();
        int y = (int) event.getY();

        int moleX = mole.getMoleCoords().x;
        int moleY = mole.getMoleCoords().y;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (x >= moleX && x < (moleX + mole.getBitmap().getWidth())
                        && y >= moleY && y < (moleY + mole.getBitmap().getHeight())) {
                    mole.setHole(new Random().nextInt(3), new Random().nextInt(3));
                    score++;
                    mole.setAnimationFrame(0);
                    switch (new Random().nextInt(4)) {
                        case 1:
                            MP1.start();
                            break;
                        case 2:
                            MP2.start();
                            break;
                        case 3:
                            MP3.start();
                            break;
                        case 0:
                            MP4.start();
                            break;
                    }
                } else
                    lives--;
                postInvalidate();
                return true;

        }
        return false;
    }
}
