package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class GameScreen implements Screen {
    /**TODO Main menu
     * TODO increase speed every 20 secs
     * TODO add powerups!
     */
    final Pong game;

    Player player1, player2;
    final float PLAYER_SPEED = 500f;

    Ball pingPongBall;
    Circle ballCircle;

    Random rand2;
    private boolean isPlaying;
    Client client;
    Server server;
    final float MAXANGLE = (float)Math.toRadians(60);
    float hitPos;


    private Sound boundaty_hit_sound;
    private Sound paddle_hit_sound;

    BitmapFont ScoreTracker;
    String Score;
    String p1Score, p2Score;
    int p1score, p2score;
    SpriteBatch scoreBatch;
    Option option;
    float angle;
    float speed;
    Type type;
    private OrthographicCamera camera;

    public GameScreen(final Pong game, Option option, Type type, Client client, Server server) {
        this.game = game;
        this.option = option;
        this.type = type;
        this.server = server;
        this.client = client;

        boundaty_hit_sound = Gdx.audio.newSound(Gdx.files.internal("boundary_hit_sound.wav"));
        isPlaying = false;
        p1score = 0;
        p2score = 0;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        ScoreTracker = new BitmapFont();
        scoreBatch = new SpriteBatch();

        player1 = new Player(0f, (float) Gdx.graphics.getHeight() / 2);
        player2 = new Player(Gdx.graphics.getWidth() - 25f, (float) Gdx.graphics.getHeight() / 2);

        pingPongBall = new Ball();
        ballCircle = pingPongBall.getBallCircle();
        rand2 = new Random();

        Score = "Score: ";
        p1Score = "P1: 0";
        p2Score = "P2: 0";
    }

    public GameScreen(final Pong game) {
        this.game = game;

        boundaty_hit_sound = Gdx.audio.newSound(Gdx.files.internal("boundary_hit_sound.wav"));
        isPlaying = false;
        p1score = 0;
        p2score = 0;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        ScoreTracker = new BitmapFont();
        scoreBatch = new SpriteBatch();

        player1 = new Player(0f, (float) Gdx.graphics.getHeight() / 2);
        player2 = new Player(Gdx.graphics.getWidth() - 25f, (float) Gdx.graphics.getHeight() / 2);

        pingPongBall = new Ball();
        ballCircle = pingPongBall.getBallCircle();
        rand2 = new Random();

        Score = "Score: ";
        p1Score = "P1: 0";
        p2Score = "P2: 0";
    }

    @Override
    public void show() {
        if (type == Type.IS_CLIENT) {
            client.listen(msg -> {
                System.out.println(msg);
                String[] values = msg.split(",");
                player1.setY_pos(Float.parseFloat(values[0]));
                pingPongBall.setY_pos(Float.parseFloat(values[1]));
                pingPongBall.setX_pos(Float.parseFloat(values[2]));
                pingPongBall.setDir_y(Float.parseFloat(values[3]));
                pingPongBall.setDir_x(Float.parseFloat(values[4]));

                System.out.println("Message from Host: " + player1.getY_pos());
                System.out.println("In Here");
            });
        } else {
            server.listen(msg -> {
                player2.setY_pos(Float.parseFloat(msg));
                System.out.println("Message from Client: " + player1.getY_pos());
            });
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        player1.DrawPlayer();
        player2.DrawPlayer();
        player1.player_ID("P1");
        player2.player_ID("P2");
        pingPongBall.DrawBall();

        game.batch.begin();
        game.font.draw(game.batch, Score, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        game.font.draw(game.batch, p1Score, ((float) Gdx.graphics.getWidth() / 2), (((float) Gdx.graphics.getHeight() / 2) - 20f));
        game.font.draw(game.batch, p2Score, (((float) Gdx.graphics.getWidth() / 2) + 50f), (((float) Gdx.graphics.getHeight() / 2) - 20f));
        game.batch.end();
        switch (type) {
            case IS_HOST:
                pingPongBall.moveBall();
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    if (player1.getY_pos() + 100f < Gdx.graphics.getHeight()) {
                        player1.setY_pos(player1.getY_pos() + PLAYER_SPEED * delta);
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    if (player1.getY_pos() > 0f) {
                        player1.setY_pos(player1.getY_pos() - PLAYER_SPEED * delta);
                    }
                }
                pingPongBall.setX_pos(pingPongBall.getX_pos() + pingPongBall.getDir_x() * delta);
                pingPongBall.setY_pos(pingPongBall.getY_pos() + pingPongBall.getDir_y() * delta);

                if (player1.isCollied(ballCircle)) {
                    isPlaying = true;
                    float hitPos = (pingPongBall.getY_pos() - (player1.getY_pos() + player1.PLAYER_HEIGHT / 2f))
                            / (player1.PLAYER_HEIGHT / 2f);
                    angle  = hitPos * MAXANGLE;
                    speed  = Vector2.len(pingPongBall.getDir_x(), pingPongBall.getDir_y());

                    pingPongBall.setDir_x(speed * (float) Math.cos(angle));
                    pingPongBall.setDir_y(speed * (float) Math.sin(angle));
                    pingPongBall.setX_pos(player1.getX_pos() + player1.PLAYER_WIDTH + pingPongBall.getBALL_RADIUS() + 1);
                    if(isPlaying){
                        isPlaying = false;
                        boundaty_hit_sound.play();
                    }
                }

                if (player2.isCollied(ballCircle)) {
                    isPlaying = true;
                    float hitPos = (pingPongBall.getY_pos() - (player2.getY_pos() + player2.PLAYER_HEIGHT / 2f))
                            / (player2.PLAYER_HEIGHT / 2f);
                    angle  = hitPos * MAXANGLE;
                    speed  = Vector2.len(pingPongBall.getDir_x(), pingPongBall.getDir_y());

                    pingPongBall.setDir_x(speed * (float) Math.cos(angle));
                    pingPongBall.setDir_y(speed * (float) Math.sin(angle));
                    pingPongBall.setX_pos(player2.getX_pos() - pingPongBall.getBALL_RADIUS() - 1);
                    if(isPlaying){
                        isPlaying = false;
                        boundaty_hit_sound.play();
                    }
                }

                if (pingPongBall.getY_pos() + pingPongBall.getBALL_RADIUS() > Gdx.graphics.getHeight() ||
                        pingPongBall.getY_pos() - pingPongBall.getBALL_RADIUS() < 0) {
                    pingPongBall.setDir_y(-pingPongBall.getDir_y());
                }



                if (pingPongBall.getX_pos() + pingPongBall.getBALL_RADIUS() > Gdx.graphics.getWidth()) {
                    p1score++;
                    p1Score = "P1: " + p1score;
                    pingPongBall.resetPosition();
                }

                if (pingPongBall.getX_pos() - pingPongBall.getBALL_RADIUS() < 0) {
                    p2score++;
                    p2Score = "P2: " + p2score;
                    pingPongBall.resetPosition();
                }
                String serverToClient = player1.getY_pos() + "," +
                        pingPongBall.getY_pos() + "," +
                        pingPongBall.getX_pos() + "," +
                        pingPongBall.getDir_y() + "," +
                        pingPongBall.getDir_x();
                server.sendInfo(serverToClient);
                break;

            case IS_CLIENT:
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    if (player2.getY_pos() + 100f < Gdx.graphics.getHeight()) {
                        player2.setY_pos(player2.getY_pos() + PLAYER_SPEED * delta);
                        System.out.println("Halo from client");
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    if (player2.getY_pos() > 0f) {
                        player2.setY_pos(player2.getY_pos() - PLAYER_SPEED * delta);
                        System.out.println("Halo from client");
                    }
                }
                String clientToServer = Float.toString(player2.getY_pos());
                client.sendInfo(clientToServer);
                break;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            camera.update();
            game.table.clear();
            this.game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        scoreBatch.dispose();
    }
}