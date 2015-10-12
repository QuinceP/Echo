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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;


public class SimonGame implements Screen {
    final Simon game;
    private Button[] buttons;
    private Sound[] sounds;


    //set up the camera and sprite batches for the game
    protected OrthographicCamera camera;
    private SpriteBatch batch;

    //a long to track when the last sound was played, to clip sounds
    //might get rid of this and just use shorter sounds so there is no need for clipping
    protected long lastSoundTime;

    //an array list to store the pattern sequence that needs to be repeated
    protected int[] seq = new int[100];

    //the current note that needs to be pressed
    private String currentNote;

    //time sent last note press
    protected int lastPress = 0;

    private int score = 0;

    protected boolean playersTurn = false;

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


    public SimonGame(Simon simon) {
        this.game = simon;

        buttons = new Button[7];
        sounds = new Sound[7];

        TextButton redButton = new TextButton("", game.getSkin());
        TextButton orangeButton = new TextButton("", game.getSkin());
        TextButton yellowButton = new TextButton("", game.getSkin());
        TextButton greenButton = new TextButton("", game.getSkin());
        TextButton blueButton = new TextButton("", game.getSkin());
        TextButton purpleButton = new TextButton("", game.getSkin());
        TextButton blackButton = new TextButton("", game.getSkin());

        //load sounds
        redSound = Gdx.audio.newSound(Gdx.files.internal("c.wav"));
        orangeSound = Gdx.audio.newSound(Gdx.files.internal("d.wav"));
        yellowSound = Gdx.audio.newSound(Gdx.files.internal("e.wav"));
        greenSound = Gdx.audio.newSound(Gdx.files.internal("f.wav"));
        blueSound = Gdx.audio.newSound(Gdx.files.internal("g.wav"));
        purpleSound = Gdx.audio.newSound(Gdx.files.internal("a.wav"));
        blackSound = Gdx.audio.newSound(Gdx.files.internal("b.wav"));

        buttons[0] = redButton;
        buttons[1] = orangeButton;
        buttons[2] = yellowButton;
        buttons[3] = greenButton;
        buttons[4] = blueButton;
        buttons[5] = purpleButton;
        buttons[6] = blackButton;

        sounds[0] = redSound;
        sounds[1] = orangeSound;
        sounds[2] = yellowSound;
        sounds[3] = greenSound;
        sounds[4] = blueSound;
        sounds[5] = purpleSound;
        sounds[6] = blackSound;

        TextButton.TextButtonStyle redButtonStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle orangeButtonStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle yellowButtonStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle greenButtonStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle blueButtonStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle purpleButtonStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle blackButtonStyle = new TextButton.TextButtonStyle();

        redButton.setPosition(272, 1680);
        redButton.setWidth(384);
        redButton.setHeight(384);
        orangeButton.setPosition(784, 1680);
        orangeButton.setWidth(384);
        orangeButton.setHeight(384);
        yellowButton.setPosition(80, 1040);
        yellowButton.setWidth(384);
        yellowButton.setHeight(384);
        greenButton.setPosition(528, 1040);
        greenButton.setWidth(384);
        greenButton.setHeight(384);
        blueButton.setPosition(976, 1040);
        blueButton.setWidth(384);
        blueButton.setHeight(384);
        purpleButton.setPosition(272, 400);
        purpleButton.setWidth(384);
        purpleButton.setHeight(384);
        blackButton.setPosition(784, 400);
        blackButton.setWidth(384);
        blackButton.setHeight(384);

        Color BURNTORANGE = new Color(152 / 255f, 82 / 255f, 18 / 255f, 1);

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(384, 384, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        game.getSkin().add("red", new Texture(pixmap));
        game.getSkin().add("orange", new Texture(pixmap));
        game.getSkin().add("yellow", new Texture(pixmap));
        game.getSkin().add("green", new Texture(pixmap));
        game.getSkin().add("blue", new Texture(pixmap));
        game.getSkin().add("purple", new Texture(pixmap));
        game.getSkin().add("black", new Texture(pixmap));

        redButtonStyle.down = game.getSkin().newDrawable("red", Color.RED);
        redButtonStyle.up = game.getSkin().newDrawable("red", Color.FIREBRICK);
        redButtonStyle.font = game.getSkin().getFont("default-font");
        redButton.setStyle(redButtonStyle);

        orangeButtonStyle.down = game.getSkin().newDrawable("orange", Color.ORANGE);
        orangeButtonStyle.up = game.getSkin().newDrawable("orange", BURNTORANGE);
        orangeButtonStyle.font = game.getSkin().getFont("default-font");
        orangeButton.setStyle(orangeButtonStyle);

        yellowButtonStyle.down = game.getSkin().newDrawable("yellow", Color.YELLOW);
        yellowButtonStyle.up = game.getSkin().newDrawable("yellow", Color.GOLDENROD);
        yellowButtonStyle.font = game.getSkin().getFont("default-font");
        yellowButton.setStyle(yellowButtonStyle);

        greenButtonStyle.down = game.getSkin().newDrawable("green", Color.GREEN);
        greenButtonStyle.up = game.getSkin().newDrawable("green", Color.FOREST);
        greenButtonStyle.font = game.getSkin().getFont("default-font");
        greenButton.setStyle(greenButtonStyle);

        blueButtonStyle.down = game.getSkin().newDrawable("blue", Color.BLUE);
        blueButtonStyle.up = game.getSkin().newDrawable("blue", Color.NAVY);
        blueButtonStyle.font = game.getSkin().getFont("default-font");
        blueButton.setStyle(blueButtonStyle);

        purpleButtonStyle.down = game.getSkin().newDrawable("purple", Color.PURPLE);
        purpleButtonStyle.up = game.getSkin().newDrawable("purple", Color.VIOLET);
        purpleButtonStyle.font = game.getSkin().getFont("default-font");
        purpleButton.setStyle(purpleButtonStyle);

        blackButtonStyle.down = game.getSkin().newDrawable("black", Color.LIGHT_GRAY);
        blackButtonStyle.up = game.getSkin().newDrawable("black", Color.DARK_GRAY);
        blackButtonStyle.font = game.getSkin().getFont("default-font");
        blackButton.setStyle(blackButtonStyle);


        buttons[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[0].play();
            }
        });
        buttons[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[1].play();
            }
        });
        buttons[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[2].play();
            }
        });
        buttons[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[3].play();
            }
        });
        buttons[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[4].play();
            }
        });
        buttons[5].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[5].play();
            }
        });
        buttons[6].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[6].play();
            }
        });


    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    //draw images to screen
    public void render(float delta) {
        addSequence();
        playSequence();


    }

    //generate a random color to add to the sequence
    public int RandomColor() {
        int color;
        String[] colors = new String[7];
        colors[0] = "red";
        colors[1] = "orange";
        colors[2] = "yellow";
        colors[3] = "green";
        colors[4] = "blue";
        colors[5] = "purple";
        colors[6] = "black";


        Random random = new Random();
        color = random.nextInt(7);

        return color;
    }


    @Override
    public void show() {
        // music

        for (int i = 0; i < 7; i++) {
            game.getStage().addActor(buttons[i]);
        }
    }

    @Override
    public void hide() {
        for (int i = 0; i < 7; i++) {
            buttons[i].remove();
        }
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
        seq[seq.length + 1] = RandomColor();
    }

    //play all notes in the sequence. unused right now.
    public void playSequence() {
        long delay = 3000; // milliseconds

        Actor actor;
        for (int i = 0; i < seq.length; i++)
            for (int j = 0; j < 7; j++) {
                actor = buttons[seq[i]];
                actor.addAction(sequence(alpha(0.7f, 0.7f), alpha(1.0f, 0.7f), run(new Runnable() {
                    @Override
                    public void run() {

                    }
                })));


            }
    }
}
