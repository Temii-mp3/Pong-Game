package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import sun.tools.jconsole.Tab;

import java.awt.*;

public class MultiplayerScreen implements Screen {

    final Pong game;
    
    private Label title;

    private TextButton host;
    private TextButton join;
    private Stage multiplayerScreenStage;
    private Table root;

    MultiplayerScreen(final Pong game){
        multiplayerScreenStage = new Stage();
        root = new Table();
        root.setFillParent(true);
        multiplayerScreenStage.addActor(root);
        this.game = game;
        host = new TextButton("HOST " , game.skin);
        join = new TextButton("JOIN ", game.skin);
        root.add(join);
        root.add(host).space(100f);

        host.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new HostScreen(game));

            }
        });

        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new ClientScreen(game));

            }
        });
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplayerScreenStage);




    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        multiplayerScreenStage.act(delta);
        multiplayerScreenStage.draw();
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
        multiplayerScreenStage.dispose();
    }
}
