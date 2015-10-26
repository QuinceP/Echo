package com.echo.game;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

import sun.rmi.runtime.Log;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable;


public class SimonGame implements Screen {
    final Simon game;
    private Button[] buttons;
    private Sound[] sounds;
    private Label[] labels;
    final Color BURNTORANGE = new Color(152 / 255f, 82 / 255f, 18 / 255f, 1);
    boolean right = true;

    //an array list to store the pattern sequence that needs to be repeated
    ArrayList<Integer> seq = new ArrayList();
    protected int length = 0;

    //the current note that needs to be pressed
    private int currentStep = 0;

    //sounds for each button
    protected Sound redSound;
    protected Sound orangeSound;
    protected Sound yellowSound;
    protected Sound greenSound;
    protected Sound blueSound;
    protected Sound purpleSound;
    protected Sound blackSound;

    //private Music Music;


    public SimonGame(Simon simon) {
        this.game = simon;

        buttons = new Button[7];
        sounds = new Sound[7];
        labels = new Label[10];

        TextButton redButton = new TextButton("", game.getSkin());
        TextButton orangeButton = new TextButton("", game.getSkin());
        TextButton yellowButton = new TextButton("", game.getSkin());
        TextButton greenButton = new TextButton("", game.getSkin());
        TextButton blueButton = new TextButton("", game.getSkin());
        TextButton purpleButton = new TextButton("", game.getSkin());
        TextButton blackButton = new TextButton("", game.getSkin());

        Label scoreLabel = new Label("Score: ", game.getSkin());
        Label scoreNumber = new Label("0", game.getSkin());
        Label highscoreLabel = new Label("High Score: ", game.getSkin());
        Label highscoreNumber = new Label("8", game.getSkin());
        Label.LabelStyle scoreLabelStyle = new Label.LabelStyle(game.getSkin().getFont("default-font"), Color.SKY);
        scoreLabel.setPosition(272, 2300);
        scoreNumber.setPosition(480, 2300);
        highscoreLabel.setPosition(700, 2300);
        highscoreNumber.setPosition(1058, 2300);

        scoreLabel.setStyle(scoreLabelStyle);
        highscoreLabel.setStyle(scoreLabelStyle);
        scoreNumber.setStyle(scoreLabelStyle);
        highscoreNumber.setStyle(scoreLabelStyle);
        labels[0] = scoreLabel;
        labels[1] = scoreNumber;
        labels[2] = highscoreLabel;
        labels[3] = highscoreNumber;

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

        blueButtonStyle.down = game.getSkin().newDrawable("blue", Color.CYAN);
        blueButtonStyle.up = game.getSkin().newDrawable("blue", Color.SKY);
        blueButtonStyle.font = game.getSkin().getFont("default-font");
        blueButton.setStyle(blueButtonStyle);

        purpleButtonStyle.down = game.getSkin().newDrawable("purple", Color.VIOLET);
        purpleButtonStyle.up = game.getSkin().newDrawable("purple", Color.PURPLE);
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
                tap(0);
            }
        }
        );
        buttons[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[1].play();
                tap(1);
            }
        });
        buttons[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[2].play();
                tap(2);
            }
        });
        buttons[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[3].play();
                tap(3);
            }
        });
        buttons[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[4].play();
                tap(4);
            }
        });
        buttons[5].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[5].play();
                tap(5);
            }
        });
        buttons[6].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[6].play();
                tap(6);
            }
        });

        addSequence();
        playSequence();
    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    //draw images to screen
    public void render(float delta) {
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

        for (int i = 0; i < 4; i++) {
            game.getStage().addActor(labels[i]);
        }



    }

    @Override
    public void hide() {
        for (int i = 0; i < 7; i++) {
            buttons[i].remove();
        }

        for (int i = 0; i < 4; i++) {
            labels[i].remove();
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

    public void addSequence()
    {
        seq.add( RandomColor());
        length++;
    }


    public void playSequence() {

        for (Button button: buttons){
            button.setTouchable(Touchable.disabled);
        }

        long delay = 3000; // milliseconds

        Button actor;


        for (int i = 0; i < length; i++)
            for (int j = 0; j < 7; j++) {
                int pos = seq.get(i);
                actor = buttons[pos];
                actor.addAction(sequence(alpha(0.5f, 0.65f), (alpha(1, 0.9f)), run(new Runnable() {
                    @Override
                    public void run() {

                    }

                })));
                for (Sound sound : sounds) {
                    sound.stop();
                }
                sounds[pos].play();
            }
        listenSequence();
    }

    public void tap(int color){
        if (color == seq.get(currentStep)){
            currentStep++;
            right = true;

            if (currentStep== length){
                labels[1].setText(Integer.toString(currentStep));
                currentStep = 0;
                addSequence();
                playSequence();
            }
        }
        else{
            right = false;
        }


    }

    public void listenSequence(){
        for (Button button: buttons){
            button.setTouchable(Touchable.enabled);
        }


        if (right == false){
            //go back to main menu?
        }


    }
}
