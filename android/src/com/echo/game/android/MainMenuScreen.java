package com.echo.game.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//This is the main menu, which will be displayed when the user starts the game
public class MainMenuScreen implements Screen {
    final Echo game;
    final TextButton start;
    final TextButton options;
    final TextButton about;
    private Texture logo;
    private SpriteBatch batch;
    final Dialog dialog;


    public MainMenuScreen(final Echo echo) {
        this.game = echo;


        batch = new SpriteBatch();
        logo = new Texture("Echo.png"); //the logo displayed on the menu
        start = new TextButton("Start", game.getSkin()); //the "start" button
        options = new TextButton("Options", game.getSkin()); //the "options" button
        about = new TextButton("About", game.getSkin()); //the "about" button

        //a dialog to display credits when the "about" button is pressed
        dialog = new Dialog("", game.getSkin());

        //camera positioning
        game.getStage().getCamera().position.set(1440 / 2, 2560 / 2, 0f);
        game.getStage().getCamera().update();

        //set dialog text and placement
        dialog.text("Created by\nChristen Ward\n2015");
        dialog.setWidth(1200);
        dialog.setHeight(600);
        dialog.setPosition(500, 1800);


        //set button placement
        about.setWidth(1000);
        about.setHeight(200);
        about.setPosition(230, 430);

        options.setWidth(1000);
        options.setHeight(200);
        options.setPosition(230, 680);

        start.setWidth(1000);
        start.setHeight(200);
        start.setPosition(230, 930);

        //add input listeners for when the buttons are clicked.
        about.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getStage().addActor(dialog);

                //show dialog if "about" button is pressed
                dialog.show(game.getStage());
                dialog.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        dialog.hide();
                        dialog.remove();
                    }
                });


            }
        });

        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game)); //brings up the options menu if "options" is pressed
            }
        });

        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new EchoGame(game)); //begins a new game if "start" is pressed
            }
        });
    }

    //render the screen
    @Override
    public void render(float delta) {
        //update camera and stage
        game.getStage().getCamera().update();
        batch.setProjectionMatrix(game.getStage().getCamera().combined);


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw the pictures to the screen
        batch.begin();
        batch.draw(logo, 230, 1800);
        batch.end();
    }

    //used to resize for differnt resolutions
    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    //stage management, makes sure all actors are on stage
    @Override
    public void show() {
        //show the buttons (only actors on this screen)
        game.getStage().addActor(start);
        game.getStage().addActor(options);
        game.getStage().addActor(about);
    }

    //cleanup of stage
    @Override
    public void hide() {

        start.remove();
        options.remove();
        about.remove();
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
}