package com.echo.game.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



//This is the options menu, which will be displayed when the user selects it from the main menu
public class OptionsScreen implements Screen {
    final Echo game;
    final TextButton back;
    final CheckBox altSound;
    public static Preferences prefs;

    //set and get alternate sound preference
    public static void setAltSound(boolean bool) {
        prefs.putBoolean("UseAltSound", bool);
        prefs.flush();
    }

    public static boolean getAltSound() {
        return prefs.getBoolean("UseAltSound");
    }


    public OptionsScreen(final Echo echo) {
        this.game = echo;
        prefs = Gdx.app.getPreferences("Echo");
        if (!prefs.contains("UseAltSound")) {
            prefs.putBoolean("UseAltSound", false);
        }

        altSound = new CheckBox("Use alternate sounds?", game.getSkin());
        altSound.setChecked(getAltSound());
        altSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean enabled = altSound.isChecked();
                setAltSound(enabled);
            }
        });

        altSound.setPosition(350, 1800);
        altSound.getCells().get(0).size(80, 80);
        back = new TextButton("Back to main menu", game.getSkin());
        back.setWidth(1000);
        back.setHeight(200);
        back.setPosition(230, 750);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });


    }
    @Override
    public void render(float delta) {
        game.getStage().getCamera().update();
        game.getStage().getBatch().setProjectionMatrix(game.getStage().getCamera().combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

        game.getStage().addActor(back);
        game.getStage().addActor(altSound);

    }

    @Override
    public void hide() {

        back.remove();
        altSound.remove();
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