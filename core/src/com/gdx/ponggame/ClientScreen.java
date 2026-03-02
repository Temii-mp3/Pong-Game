package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class ClientScreen implements Screen {
    final Pong game;
    private Label infoScreen;
    private com.gdx.ponggame.Client client;
    private Stage clientScreenStage;
    private Table root;
    private Skin skin;
    Label joinLabel;
    Label portLabel;
    TextButton joinButton;
    TextField ipAddress;
    TextField portNumber;
    Label ipLabel;
    Label infoLabel;
    Label infolabel2;
    String ip;
    int port;

    private TextButton button;
    public ClientScreen(Pong game) {
        clientScreenStage = new Stage();
        root = new Table();
        root.setFillParent(true);
        clientScreenStage.addActor(root);
        this.game = game;
        ipLabel = new Label("Enter IP Address: ", game.skin);
        portLabel = new Label("Enter Port Number: ", game.skin);
        joinLabel = new Label("Join a Game", game.skin);
        joinButton = new TextButton("Join", game.skin);
        ipAddress = new TextField("", game.skin);
        portNumber = new TextField("", game.skin);

        root.add(joinLabel).top().spaceBottom(50f);
        root.row();
        root.add(ipLabel).spaceBottom(50f);
        root.row();
        root.add(ipAddress).width(250f).spaceBottom(50f);
        root.row();
        root.add(portLabel).spaceBottom(50f);
        root.row();
        root.add(portNumber).spaceBottom(50f);
        root.row();
        root.add(joinButton);

//        client = new Client("127.0.0.1", 8080);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(clientScreenStage);
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                root.clear();
                infoLabel = new Label("Looking for Client", game.skin);
                infolabel2 = new Label("Waiting for Host to Start", game.skin);
                root.add(infoLabel).center().spaceBottom(50f);
                ip = ipAddress.getText();
                port = Integer.parseInt(portNumber.getText());


                Thread clientJoin = new Thread(() -> {
                    client = new Client(ip, port, () -> {
                        infoLabel.setText("Connected");
                        root.add(infolabel2).top();

                        client.listen(msg-> {
                            if (msg.equals("PLAY")) {
                                Gdx.app.postRunnable(() -> {
                                    game.setScreen(new GameScreen(game, Option.DIFFCOMPUTER, Type.IS_CLIENT, client, null));
                                });                            }
                        });
                    });
                });
                clientJoin.setDaemon(true);
                clientJoin.start();

            }
        });



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
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
