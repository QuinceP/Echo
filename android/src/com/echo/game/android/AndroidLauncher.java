package com.echo.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.echo.game.Simon;
import com.echo.game.SimonGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//frees up resources by disabling these
		config.useAccelerometer = false;
		config.useCompass = false;
		//intialize a new game
		initialize(new Simon(), config);
	}
}
