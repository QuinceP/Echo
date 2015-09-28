package com.echo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;


public class SimonGame implements Screen {
    final Simon game;

    //set up the camera and sprite batches for the game
    protected OrthographicCamera camera;
    private SpriteBatch batch;

    //a long to track when the last sound was played, to clip sounds
    //might get rid of this and just use shorter sounds so there is no need for clipping
    protected long lastSoundTime;

    //an array list to store the pattern sequence that needs to be repeated
    protected ArrayList<String> sequence = new ArrayList<String>();

    //the current note that needs to be pressed
    private String currentNote;

    //time sent last note press
    protected int lastPress = 0;

    private int score = 0;

    protected boolean playersTurn = false;

    //Button textures, pressed and unpressed
    protected Texture redButton;
    protected Texture redButtonPressed;
    protected Texture orangeButton;
    protected Texture orangeButtonPressed;
    protected Texture yellowButton;
    protected Texture yellowButtonPressed;
    protected Texture greenButton;
    protected Texture greenButtonPressed;
    protected Texture blueButton;
    protected Texture blueButtonPressed;
    protected Texture purpleButton;
    protected Texture purpleButtonPressed;
    protected Texture blackButton;
    protected Texture blackButtonPressed;

    //sounds for each button
    protected Sound redSound;
    protected Sound orangeSound;
    protected Sound yellowSound;
    protected Sound greenSound;
    protected Sound blueSound;
    protected Sound purpleSound;
    protected Sound blackSound;


    //rectangles to store the button textures
    protected Rectangle red;
    protected Rectangle orange;
    protected Rectangle yellow;
    protected Rectangle green;
    protected Rectangle blue;
    protected Rectangle purple;
    protected Rectangle black;

    //private Music Music;


    public SimonGame(final Simon simon) {
        this.game = simon;
        //camera setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1440, 2560);

        //sprite batch
        batch = new SpriteBatch();

        //load images
        redButton = new Texture(Gdx.files.internal("red.png"));
        redButtonPressed = new Texture(Gdx.files.internal("redbright.png"));
        orangeButton = new Texture(Gdx.files.internal("orange.png"));
        orangeButtonPressed = new Texture(Gdx.files.internal("orangebright.png"));
        yellowButton = new Texture(Gdx.files.internal("yellow.png"));
        yellowButtonPressed = new Texture(Gdx.files.internal("yellowbright.png"));
        greenButton = new Texture(Gdx.files.internal("green.png"));
        greenButtonPressed = new Texture(Gdx.files.internal("greenbright.png"));
        blueButton = new Texture(Gdx.files.internal("blue.png"));
        blueButtonPressed = new Texture(Gdx.files.internal("bluebright.png"));
        purpleButton = new Texture(Gdx.files.internal("purple.png"));
        purpleButtonPressed = new Texture(Gdx.files.internal("purplebright.png"));
        blackButton = new Texture(Gdx.files.internal("black.png"));
        blackButtonPressed = new Texture(Gdx.files.internal("blackbright.png"));

        //load sounds
        redSound = Gdx.audio.newSound(Gdx.files.internal("c.wav"));
        orangeSound = Gdx.audio.newSound(Gdx.files.internal("d.wav"));
        yellowSound = Gdx.audio.newSound(Gdx.files.internal("e.wav"));
        greenSound = Gdx.audio.newSound(Gdx.files.internal("f.wav"));
        blueSound = Gdx.audio.newSound(Gdx.files.internal("g.wav"));
        purpleSound = Gdx.audio.newSound(Gdx.files.internal("a.wav"));
        blackSound = Gdx.audio.newSound(Gdx.files.internal("b.wav"));

        //rectangles to contain the buttons
        red = new Rectangle();
        red.x = 120;
        red.y = 1800;
        red.width = 256;
        red.height = 256;

        orange = new Rectangle();
        orange.x = 570;
        orange.y = 1800;
        orange.width = 256;
        orange.height = 256;

        yellow = new Rectangle();
        yellow.x = 1020;
        yellow.y = 1800;
        yellow.width = 256;
        yellow.height = 256;

        green = new Rectangle();
        green.x = 120;
        green.y = 1500;
        green.width = 256;
        green.height = 256;

        blue = new Rectangle();
        blue.x = 570;
        blue.y = 1500;
        blue.width = 256;
        blue.height = 256;

        purple = new Rectangle();
        purple.x = 1020;
        purple.y = 1500;
        purple.width = 256;
        purple.height = 256;

        black = new Rectangle();
        black.x = 570;
        black.y = 1200;
        black.width = 256;
        black.height = 256;


    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    //draw images to screen
    public void render(float delta) {
        //last time a sound was played (might remove this and just put a shorter sound in so i dont have to clip it.)
        lastSoundTime = TimeUtils.nanoTime();

        //color of the screen
        Gdx.gl.glClearColor(0, 0, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update the camera
        camera.update();

        //draw all the buttons
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(redButton, red.x, red.y);
        game.batch.draw(orangeButton, orange.x, orange.y);
        game.batch.draw(yellowButton, yellow.x, yellow.y);
        game.batch.draw(greenButton, green.x, green.y);
        game.batch.draw(blueButton, blue.x, blue.y);
        game.batch.draw(purpleButton, purple.x, purple.y);
        game.batch.draw(blackButton, black.x, black.y);

        game.batch.end();


        //play sounds if buttons are touched
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (red.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(redButtonPressed, red.x, red.y);
                game.batch.end();
                redSound.play();
                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    //stop the sound after a certain time (half second)
                    redSound.stop();
                }
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (orange.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(orangeButtonPressed, orange.x, orange.y);
                game.batch.end();
                orangeSound.play();
                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    orangeSound.stop();
                }
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (yellow.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(yellowButtonPressed, yellow.x, yellow.y);
                game.batch.end();
                yellowSound.play();
                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    yellowSound.stop();
                }
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (green.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(greenButtonPressed, green.x, green.y);
                game.batch.end();
                greenSound.play();
                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    greenSound.stop();
                }
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (blue.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(blueButtonPressed, blue.x, blue.y);
                game.batch.end();
                blueSound.play();
                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    blueSound.stop();
                }
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (purple.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(purpleButtonPressed, purple.x, purple.y);
                game.batch.end();
                purpleSound.play();
                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    purpleSound.stop();
                }
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (black.contains(touchPos.x, touchPos.y)) {
                game.batch.begin();
                game.batch.draw(blackButtonPressed, black.x, black.y);
                game.batch.end();

                blackSound.play();

                if (TimeUtils.nanoTime() - lastSoundTime > 500000000) {
                    blackSound.stop();
                }
            }
        }

        //unused, game logic will go here in the render method
/*		if (playersTurn == false){
            addSequence();
			playSequence();
			playersTurn = true;
		}*/

    }

    //generate a random color to add to the sequence
    public String RandomColor() {
        String color = "null";
        String[] colors = new String[7];
        colors[0] = "red";
        colors[1] = "orange";
        colors[2] = "yellow";
        colors[3] = "green";
        colors[4] = "blue";
        colors[5] = "purple";
        colors[6] = "black";

        Random random = new Random();
        int num = random.nextInt(7);

        color = colors[num];
        return color;
    }


    @Override
    public void show() {
        // music

    }

    @Override
    public void hide() {
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //add a color to the sequence. unused right now
    public void addSequence() {
        sequence.add(RandomColor());
    }

    //play all notes in the sequence. unused right now.
    public void playSequence() {
        float delay = 1 / 2; // seconds


        for (String color : sequence) {
            if (color.equals("red")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(redButtonPressed, red.x, red.y);
                    }
                }, delay);

                game.batch.end();
                redSound.play();
            }

            if (color.equals("orange")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(orangeButtonPressed, orange.x, orange.y);
                    }
                }, delay);
                game.batch.end();
                redSound.play();
            }

            if (color.equals("yellow")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(yellowButtonPressed, yellow.x, yellow.y);
                    }
                }, delay);
                game.batch.end();
                yellowSound.play();
            }

            if (color.equals("green")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(greenButtonPressed, green.x, green.y);
                    }
                }, delay);
                game.batch.end();
                greenSound.play();
            }

            if (color.equals("blue")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(blueButtonPressed, blue.x, blue.y);
                    }
                }, delay);
                game.batch.end();
                blueSound.play();
            }

            if (color.equals("purple")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(purpleButtonPressed, purple.x, purple.y);
                    }
                }, delay);
                game.batch.end();
                purpleSound.play();
            }

            if (color.equals("black")) {
                game.batch.begin();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.batch.draw(blackButtonPressed, black.x, black.y);
                    }
                }, delay);
                game.batch.end();
                blackSound.play();
            }

        }


    }
}
