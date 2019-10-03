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
    final static String TAG="DINO-RAINBOWS";

    //900 pixels visible on screen and 100 pixels for 4 road rectangles
    final static int LANE_HEIGHT= 200;

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
    int lanes =4;
    int laneTop;
    int laneBottom;

    // ----------------------------
    // ## SPRITES
    // ----------------------------

     Rect laneRectangle;
    Player dinosour;
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

    private void spawnPlayer()
    {
        Log.d(TAG, "spawnPlayer: Marks the beginning of SpawnPlayer");
        //@TODO: Start the player at the RIGHT side of screen
        //Initializing the player at 85% width as x and middle of screen as Y
        dinosour = new Player(this.getContext(), (int)(screenWidth*.85),(int)(screenHeight*.5)-200);
        //Initializing the 4 lanes

    }

    private void drawLaneOne()
    {
        Log.d(TAG, "drawLaneFirst: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop =LANE_HEIGHT-12;
        this.laneBottom = LANE_HEIGHT+12;
        Log.d(TAG, "drawLaneFirst: LaneTOP: "+laneTop);
        Log.d(TAG, "drawLaneFirst: LaneBOTTOM: "+laneBottom);
        this.laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle,paintbrush);
    }
    private void drawLaneTwo(){
        Log.d(TAG, "drawLaneSecond: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop =((LANE_HEIGHT)*2)-12;
        this.laneBottom = ((LANE_HEIGHT)*2)+12;
        Log.d(TAG, "drawLaneSecond: LaneTOP:"+laneTop);
        Log.d(TAG, "drawLaneSecond: LaneBOTTOM:"+laneBottom);
        this.laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle,paintbrush);

    }
    private void drawLaneThree(){
        Log.d(TAG, "drawLaneThird: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop =((LANE_HEIGHT)*3)-12;
        this.laneBottom = ((LANE_HEIGHT)*3)+12;
        Log.d(TAG, "drawLaneThird: LaneTOP:"+laneTop);
        Log.d(TAG, "drawLaneThird: LaneBOTTOM:"+laneBottom);

        laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle,paintbrush);
    }
    private void drawLaneFour(){
        Log.d(TAG, "drawLaneFourth: ");
        paintbrush.setColor(Color.RED);
        paintbrush.setStyle(Paint.Style.FILL);
        this.laneTop =((LANE_HEIGHT)*4)-12;
        this.laneBottom = ((LANE_HEIGHT)*4)+12;
        Log.d(TAG, "drawLaneFourth: LaneTOP:"+laneTop);
        Log.d(TAG, "drawLaneFourth: LaneBOTTOM:"+laneBottom);

        laneRectangle = new Rect(50,
                laneTop,
                1400,
                laneBottom);
        this.canvas.drawRect(laneRectangle,paintbrush);
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

    public void updatePositions() {
    }

    public void redrawSprites() {
        Log.d(TAG, "redrawSprites: Marks the beginning of RedrawSprites");
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            //Draw the lanes
            drawLaneOne();
            drawLaneTwo();
            drawLaneThree();
            drawLaneFour();

            //draw the dinosaur
            this.canvas.drawBitmap(dinosour.getImage(),dinosour.getxPosition(),dinosour.getyPosition(),paintbrush);
            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);
            this.canvas.drawRect(dinosour.getHitbox(),paintbrush);


            //life label
            paintbrush.setTextSize(50);
            canvas.drawText("lives:- " + this.lives, 1500, 50,paintbrush);

            //score label
            paintbrush.setTextSize(50);
            canvas.drawText("score:- " + this.score, 1200,50,paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {

        }
        else if (userAction == MotionEvent.ACTION_UP) {

        }

        return true;
    }
}
