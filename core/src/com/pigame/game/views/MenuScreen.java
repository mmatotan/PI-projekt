package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigame.game.*;

public class MenuScreen implements Screen {
	
	private Game parent;
	private Stage stage;
	
	public MenuScreen(Game game) {
		parent = game;
		
		stage = new Stage(new ScreenViewport()); //Controller that will react to inputs from the user
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage); //Listen for inputs from the stage
		
		Table table = new Table(); //Used to store buttons for UI
		table.setFillParent(true);
		//table.setDebug(true); //If left uncommented it will show the borders of the table for debugging
		stage.addActor(table);
		
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json")); //Adds a skin template which we'll use
		
		//UI Elements
		TextButton newGame = new TextButton("New Game", skin);
		TextButton preferences = new TextButton("Preferences", skin);
		TextButton exit = new TextButton("Exit", skin);
		Label title = new Label("Igrica", skin);
		
		//Fill the screen with before created elements
		table.add(title);
		table.row().pad(10, 0, 10, 0); //Add empty space 10 pixels up, 10 pixels down
		table.add(newGame).fillX().uniformX();
		table.row().pad(0, 0, 10, 0);
		table.add(preferences).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
		
		//List of different actions to take when the buttons are pressed
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stage.dispose();
				parent.changeScreen(Game.APPLICATION);
			}
		});
		
		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Game.PREFERENCES);	
			}
		});
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}

	//Render the data set in the show method
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f)); //The stage will draw at 30 fps
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
