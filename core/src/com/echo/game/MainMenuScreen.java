package com.echo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

//This is the main menu, which will be displayed when the user starts the game
public class MainMenuScreen implements Screen {
    final Simon game;

    //create a camera
    OrthographicCamera camera;

    public MainMenuScreen(final Simon simon) {
        game = simon;

        //set up the camera. Need to fix this so the camera will work in all resolutions
        camera = new OrthographicCamera();

        //i have a galaxy s6 so these are the dimensions for my phone
        camera.setToOrtho(false, 1440, 2560);

    }
    @Override
    public void render(float delta) {

        //rendering everything to the screen.
        //clear color is black.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update the camera at each frame
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //draw the main menu - for now, just a welcome greeting and a prompt to tap the screen to begin.
        game.titleFont.draw(game.batch, "Welcome to Echo!",1440/5 - 100 , 2560-1000);
        game.titleFont.draw(game.batch, "Tap anywhere to start.", 1440/5- 100, 2560 - 1300);
        game.batch.end();

        //if any part of the screen is touched, start a new game.
        if (Gdx.input.isTouched()) {
            game.setScreen(new SimonGame(game));
            dispose();
        }
    }

    //unused methods so far
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
}