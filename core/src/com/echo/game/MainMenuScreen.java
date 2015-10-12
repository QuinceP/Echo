package com.echo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import sun.rmi.runtime.Log;


//This is the main menu, which will be displayed when the user starts the game
public class MainMenuScreen implements Screen {
    final Simon game;
    final TextButton start;


    public MainMenuScreen(final Simon simon) {
        this.game = simon;
        start = new TextButton("Press to start!", game.getSkin());
        start.setWidth(1000);
        start.setHeight(300);
        start.setPosition(230, 1000);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button", "clickedHO");
                game.setScreen(new SimonGame(game));
            }
        });


    }
    @Override
    public void render(float delta) {

    }

    //unused methods so far
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        game.getStage().addActor(start);
    }

    @Override
    public void hide() {
        start.remove();
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