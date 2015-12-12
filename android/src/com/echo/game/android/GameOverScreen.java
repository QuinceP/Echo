package com.echo.game.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


//This is the game over screen, which will be displayed when the user loses the game
public class GameOverScreen implements Screen {
    final Echo game;
    final TextButton start;
    final TextButton menubutton;
    private Texture gameover;
    private SpriteBatch batch;

    public GameOverScreen(final Echo echo) {
        this.game = echo;
        batch = new SpriteBatch();

        //the game over picture
        gameover = new Texture("gameover.png");

        //button to start a new game
        start = new TextButton("Try again?", game.getSkin());
        //set attributes
        start.setWidth(1000);
        start.setHeight(150);
        start.setPosition(230, 1000);

        //add listener
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button", "clicked");
                game.setScreen(new EchoGame(game)); //starts a new game if clicked
            }
        });

        //button to return to the main menu
        menubutton = new TextButton("Exit to menu", game.getSkin());
        menubutton.setWidth(1000);
        menubutton.setHeight(150);
        menubutton.setPosition(230, 800);
        menubutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button", "clicked");
                game.setScreen(new MainMenuScreen(game)); //goes back to menu if clicked
            }
        });

    }
    @Override
    public void render(float delta) {
        game.getStage().getCamera().update();
        batch.setProjectionMatrix(game.getStage().getCamera().combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(gameover, 200, 1500);
        batch.end();

    }

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