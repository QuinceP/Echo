package com.echo.game.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



//This is the main menu, which will be displayed when the user starts the game
public class OptionsScreen implements Screen {
    final Simon game;
    final TextButton back;
    final CheckBox altSound;
    public static Preferences prefs;

    public static void setAltSound(boolean bool) {
        prefs.putBoolean("UseAltSound", bool);
        prefs.flush();
    }
    public static boolean getAltSound() {
        return prefs.getBoolean("UseAltSound");
    }


    public OptionsScreen(final Simon simon) {
        this.game = simon;
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


    }

    //unused methods so far
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