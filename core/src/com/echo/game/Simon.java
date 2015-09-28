package com.echo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Simon extends Game {

    //create variables for the font
    BitmapFont titleFont;

    //create a batch to draw images to the screen
    SpriteBatch batch;

    private void createFonts() {
        //the file of the font used
        FileHandle fontFile = Gdx.files.internal("BRITANIC.TTF");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 114;
        titleFont = generator.generateFont(parameter);
        generator.dispose();
    }
    public void create() {

        //create the fonts and the main menu
        createFonts();
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        //render everything to the screen
        super.render();
    }

    public void dispose() {
        //garbage cleanup
        batch.dispose();
        titleFont.dispose();
    }
}
