package com.gdx.ponggame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ClientScreen implements Screen {
    final Pong game;
    private Label infoScreen;
    private com.gdx.ponggame.Client client;

    private TextButton button;
    public ClientScreen(Pong game) {
        this.game = game;
        client = new Client("127.0.0.1", 8080);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
