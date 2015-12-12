package com.echo.game.android;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

//This is the main game engine. Instances of the "Echo" class create a new screen. The screen changes to the specific Echo instance.
public class Echo extends Game {
    //create a batch to draw images to the screen
    SpriteBatch batch;

    //create the camera and stage
    private OrthographicCamera camera;
    private Stage stage;

    //use the uiskin.json for fonts, etc.
    private Skin skin;

    //creates the screen
    @Override
    public void create() {
        batch = new SpriteBatch();

        //set the dimensions of the camera
        camera = new OrthographicCamera(1440, 2560);
        camera.setToOrtho(false, 1440, 2560);

        //fit the viewport to the stage, and position the camera; this step is needed to ensure that thee game retains its aspect ratio between all devices
        stage = new Stage(new FitViewport(1440, 2560, camera), batch);
        this.camera.position.set(1440/2, 2560/2, 0f);

        //specify skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        //set the default font
        skin.getFont("default-font").getData().setScale(4f, 4f);

        //make a main menu
        setScreen(new MainMenuScreen(this));

        //recognize input
        Gdx.input.setInputProcessor(stage);
    }

    public void render() {

        //clear the screen on render
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
