package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG = "DINO-RAINBOWS";


    final static int LANE_HEIGHT = 200;

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;


    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------
    int lanes = 4;
    int laneTop;
    int laneBottom;

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    Rect laneRectangle;
    Player dinosour;
    Item candy;
//    Player rainbow;
//    Player garbage;
    int dinoLane;
    // represent the TOP LEFT CORNER OF THE GRAPHIC

    // ----------------------------
    // ## GAME STATS
    // ----------------------------
    int score = 0;
    int lives = 3;

    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        //spawn the player image
        spawnPlayer();

    }


    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        Log.d(TAG, "spawnPlayer: Marks the beginning of SpawnPlayer");
        //@TODO: Start the player at the RIGHT side of screen
        //Initializing the player at 85% width as x and middle of screen as Y
        dinosour = new Player(this.getContext(), (int) (screenWidth * .85), (int) (screenHeight * .5) - 200);

        candy = new Item(this.getContext(), (int) (screenWidth * 0.5), (int) (screenHeight * .5) - 550);

//        rainbow = new Player(this.getContext(), (int) (screenWidth * 0.15), (int) (screenHeight * 0.5) - 500);

    }

    private void drawLaneOne() {
        Log.d(TAG, "drawLaneFirst: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop = LANE_HEIGHT - 12;
        this.laneBottom = LANE_HEIGHT + 12;
        Log.d(TAG, "drawLaneFirst: LaneTOP: " + laneTop);
        Log.d(TAG, "drawLaneFirst: LaneBOTTOM: " + laneBottom);
        this.laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle, paintbrush);
    }

    private void drawLaneTwo() {
        Log.d(TAG, "drawLaneSecond: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop = ((LANE_HEIGHT) * 2) - 12;
        this.laneBottom = ((LANE_HEIGHT) * 2) + 12;
        Log.d(TAG, "drawLaneSecond: LaneTOP:" + laneTop);
        Log.d(TAG, "drawLaneSecond: LaneBOTTOM:" + laneBottom);
        this.laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle, paintbrush);

    }

    private void drawLaneThree() {
        Log.d(TAG, "drawLaneThird: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop = ((LANE_HEIGHT) * 3) - 12;
        this.laneBottom = ((LANE_HEIGHT) * 3) + 12;
        Log.d(TAG, "drawLaneThird: LaneTOP:" + laneTop);
        Log.d(TAG, "drawLaneThird: LaneBOTTOM:" + laneBottom);

        laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle, paintbrush);
    }

    private void drawLaneFour() {
        Log.d(TAG, "drawLaneFourth: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop = ((LANE_HEIGHT) * 4) - 12;
        this.laneBottom = ((LANE_HEIGHT) * 4) + 12;
        Log.d(TAG, "drawLaneFourth: LaneTOP:" + laneTop);
        Log.d(TAG, "drawLaneFourth: LaneBOTTOM:" + laneBottom);

        laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle, paintbrush);
    }


    private void spawnRandomObjects() {
        Random random = new Random();
        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    //using switch case for movement
    public void updatePositions() {

        switch (dinoLane) {
            case 1:
                this.dinosour.setyPosition(22);
                break;
            case 2:
                this.dinosour.setyPosition(222);
                break;
            case 3:
                this.dinosour.setyPosition((450));
                break;
            case 4:
                this.dinosour.setyPosition(620);
                break;
        }

        //Reposition the HitBox as per dinosaur positions
        this.dinosour.getHitbox().left = this.dinosour.getxPosition();
        this.dinosour.getHitbox().top = this.dinosour.getyPosition();
        this.dinosour.getHitbox().right = this.dinosour.getxPosition() + this.dinosour.getImage().getWidth();
        this.dinosour.getHitbox().bottom = this.dinosour.getyPosition() + this.dinosour.getImage().getHeight();


    }

    public void redrawSprites()
    {
        Log.d(TAG, "redrawSprites: Marks the beginning of RedrawSprites");
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255, 255, 255, 255));
            paintbrush.setColor(Color.WHITE);


            //Draw the lanes
            drawLaneOne();
            drawLaneTwo();
            drawLaneThree();
            drawLaneFour();

            //draw the dinosaur
            this.canvas.drawBitmap(dinosour.getImage(), dinosour.getxPosition(), dinosour.getyPosition(), paintbrush);

            //draw the candy
            this.canvas.drawBitmap(candy.getImage(), candy.getxPosition(), candy.getyPosition(), paintbrush);

            //draw the rainbow
//            this.canvas.drawBitmap(rainbow.getImage(), rainbow.getxPosition(), rainbow.getyPosition(), paintbrush);

            //dra


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);
            this.canvas.drawRect(dinosour.getHitbox(), paintbrush);
            this.canvas.drawRect(candy.getHitbox(), paintbrush);


            //life label
            paintbrush.setTextSize(50);
            canvas.drawText("lives:- " + this.lives, 1500, 50, paintbrush);

            //score label
            paintbrush.setTextSize(50);
            canvas.drawText("score:- " + this.score, 1200, 50, paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        } catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int middleOfScreen = (this.screenHeight / 2) - 100;
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {


            if (event.getY() > middleOfScreen) {
                fingerAction = "down";
                switch (dinoLane) {
                    case 1:
                        dinoLane = dinoLane + 2;
                        break;
                    case 2:
                        dinoLane++;
                        break;
                    case 3:
                        dinoLane++;
                        break;
                    case 4:
                        dinoLane--;
                        break;
                }
            } else if (event.getY() < middleOfScreen) {
                Log.d(TAG, "onTouchEvent: UP" + event.getY());
                fingerAction = "up";
                switch (dinoLane) {
                    case 1:
                        dinoLane++;
                        break;
                    case 2:
                        dinoLane--;
                        break;
                    case 3:
                        dinoLane--;
                        break;
                    case 4:
                        dinoLane = dinoLane - 2;
                        break;
                }

            } else if (userAction == MotionEvent.ACTION_UP) {
//
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                this.fingerAction = "up";

            }
            return true;
        }


        return false;
    }
}