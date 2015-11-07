package com.echo.game.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


//This is the main menu, which will be displayed when the user starts the game
public class GameOverScreen implements Screen {
    final Simon game;
    final TextButton start;
    final TextButton menubutton;
    private Texture gameover;
    private SpriteBatch batch;


    public GameOverScreen(final Simon simon) {



        this.game = simon;
        batch = new SpriteBatch();
        gameover = new Texture("gameover.png");
        start = new TextButton("Try again?", game.getSkin());
        start.setWidth(1000);
        start.setHeight(150);
        start.setPosition(230, 1000);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button", "clicked");
                game.setScreen(new SimonGame(game));
            }
        });

        menubutton = new TextButton("Exit to menu", game.getSkin());
        menubutton.setWidth(1000);
        menubutton.setHeight(150);
        menubutton.setPosition(230, 800);
        menubutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button", "clicked");
                game.setScreen(new MainMenuScreen(game));
            }
        });

    }
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(gameover, 200, 1500);
        batch.end();

    }

    //unused methods so far
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

        game.getStage().addActor(start);
        game.getStage().addActor(menubutton);
    }

    @Override
    public void hide() {

        start.remove();
        menubutton.remove();
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