package com.echo.game.android;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Simon extends Game {
    //create a batch to draw images to the screen
    SpriteBatch batch;

    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(1440, 2560);
        camera.setToOrtho(false, 1440, 2560);
        stage = new Stage(new FitViewport(1440, 2560, camera), batch);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(4f, 4f);
        setScreen(new MainMenuScreen(this));
        Gdx.input.setInputProcessor(stage);
    }

    public void render() {

        Gdx.gl.glClearColor(20/255f, 20/255f,20/255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render();
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void dispose() {
        //garbage cleanup
        batch.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;

    }
}
