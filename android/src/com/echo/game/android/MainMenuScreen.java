package com.echo.game.android;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



//This is the main menu, which will be displayed when the user starts the game
public class MainMenuScreen implements Screen {
    final Simon game;
    final TextButton start;
    final TextButton options;
    final TextButton about;
    private Texture logo;
    private SpriteBatch batch;
    final Dialog dialog;



    public MainMenuScreen(final Simon simon) {
        this.game = simon;

        batch = new SpriteBatch();
        logo = new Texture("Echo.png");
        start = new TextButton("Start", game.getSkin());
        options = new TextButton("Options", game.getSkin());
        dialog = new Dialog("", game.getSkin());

        dialog.text("Created by\nChristen Ward\n2015");
        dialog.setWidth(1200);
        dialog.setHeight(600);
        dialog.setPosition(500, 1800);


        about = new TextButton("About", game.getSkin());
        about.setWidth(1000);
        about.setHeight(200);
        about.setPosition(230, 430);

        options.setWidth(1000);
        options.setHeight(200);
        options.setPosition(230, 680);

        start.setWidth(1000);
        start.setHeight(200);
        start.setPosition(230, 930);
        about.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getStage().addActor(dialog);
                dialog.show(game.getStage());
                dialog.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        dialog.hide();
                        dialog.remove();
                    }
                });


            }
        });        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SimonGame(game));
            }
        });


    }
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(logo, 230, 1800);
        batch.end();

    }

    //unused methods so far
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

        game.getStage().addActor(start);
        game.getStage().addActor(options);
        game.getStage().addActor(about);
    }

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