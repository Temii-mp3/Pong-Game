package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Pong game;
    Label gameName;

    private TextButton button_AIPlayer;
    private TextButton button_MultiPlayer;
    private TextButton button_TwoPlayers;

    private Stage mainMenuStage;
    private Table root;

    Button musicButton;

    OrthographicCamera camera;
    public MainMenuScreen(final Pong pongGame) {
        mainMenuStage = new Stage();
        root = new Table();
        root.setFillParent(true);
        mainMenuStage.addActor(root);


        this.game = pongGame;

        button_AIPlayer = new TextButton("Play Against AI" , game.skin);
        button_MultiPlayer = new TextButton("Play with friends (Different Computers)", game.skin);
        button_TwoPlayers = new TextButton("Play with friends (Same Computer)", game.skin);
        musicButton = new Button(game.skin, "music");

        gameName = new Label("MULTIPLAYER PONG", game.skin);
        gameName.setAlignment(Align.center);
        gameName.setFontScale(2.5f);
        root.add(gameName).top().spaceBottom(100f);
        root.row();
        root.add(button_AIPlayer).center().spaceBottom(50f);
        root.row();
        root.add(button_MultiPlayer).center().spaceTop(50f);
        root.row();
        root.add(button_TwoPlayers).center().spaceTop(50f);
        root.row();
        root.add(musicButton).right().bottom();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
        game.soundtrack.play();
        game.soundtrack.setLooping(true);
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.soundtrack.pause();
            }
        });

        button_TwoPlayers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });


        button_MultiPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MultiplayerScreen(game, Option.DIFFCOMPUTER));
            }
        });


    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(mainMenuStage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.end();
        mainMenuStage.act(delta);
        mainMenuStage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("HELLO FROM DISPOSE");
        mainMenuStage.dispose();
    }
}
