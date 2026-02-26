package com.gdx.ponggame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.ponggame.MainMenuScreen;

public class Pong extends Game {
    public Stage stage;
    public Table table;
    public SpriteBatch batch;
   Skin skin;
    public BitmapFont font;

    public Music soundtrack;

    public void create(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table(skin);
        table.background("window");
        table.setFillParent(true);
        stage.addActor(table);
        skin =  new Skin(Gdx.files.internal("uiskin.json"));
        table.setDebug(true); //debugging purposes
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("sound_track.mp3"));
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    public void addActor(Actor a){
        table.add(a);
    }
}
