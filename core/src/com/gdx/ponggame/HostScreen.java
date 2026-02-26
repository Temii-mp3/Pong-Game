package com.gdx.ponggame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public class HostScreen implements Screen {

    final Pong game;

    private Label infoScreen;

    private TextButton button;
    Stage hostScreenStage;
    Table root;

    HostScreen(final Pong game){
        hostScreenStage = new Stage();
        root = new Table();
        root.setFillParent(true);
        hostScreenStage.addActor(root);

        this.game = game;
        infoScreen = new Label("Waiting for Client......", game.skin);
        System.out.print("We are here ");
        button = new TextButton("Play", game.skin);
        Thread serverCreation = new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server(8080);
                server.setOnClientConnected(() ->{
                    root.add(button).center();
                });

            }
        });
        serverCreation.start();

        root.add(infoScreen).center();
        root.row();
    }




    @Override
    public void show() {

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
