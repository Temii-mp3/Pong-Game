package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class HostScreen implements Screen {

    final Pong game;

    private Label infoScreen;

    private TextButton button;
    Stage hostScreenStage;
    Table root;
    volatile Server server;
    HostScreen(final Pong game){
        hostScreenStage = new Stage();
        root = new Table();
        root.setFillParent(true);
        hostScreenStage.addActor(root);

        this.game = game;
        infoScreen = new Label("Waiting for Client......", game.skin);
        System.out.print("We are here ");
        button = new TextButton("Play", game.skin);
        root.add(infoScreen).center();
        root.row();


    }




    @Override
    public void show() {
        Gdx.input.setInputProcessor(hostScreenStage);
        Thread serverCreation = new Thread(() -> {
             server = new Server(8080, () -> {
                root.clear();
                root.add(button).center();
            });
            server.start();
        });
        serverCreation.start();

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(server);
                server.sendInfo("PLAY");
                dispose();
                game.setScreen(new GameScreen(game, Option.DIFFCOMPUTER, Type.IS_HOST, null, server));

            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        hostScreenStage.act(delta);
        hostScreenStage.draw();
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

    }
}
