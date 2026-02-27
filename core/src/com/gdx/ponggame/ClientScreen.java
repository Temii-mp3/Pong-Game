package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ClientScreen implements Screen {
    final Pong game;
    private Label infoScreen;
    private com.gdx.ponggame.Client client;
    private Stage clientScreenStage;
    private Table root;
    private Skin skin;

    private TextButton button;
    public ClientScreen(Pong game) {
        clientScreenStage = new Stage();
        root = new Table();
        root.setFillParent(true);
        clientScreenStage.addActor(root);
        this.game = game;
        TextField ipAddress = new TextField("", game.skin);
        TextField portNumber = new TextField("", game.skin);

        root.add(ipAddress);
        root.add(portNumber);


//        client = new Client("127.0.0.1", 8080);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(clientScreenStage);
    }

    @Override
    public void render(float delta) {
        clientScreenStage.act(delta);
        clientScreenStage.draw();
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
