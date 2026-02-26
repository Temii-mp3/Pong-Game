package com.gdx.ponggame;

import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;
public class GameScreen implements Screen {
    /**TODO Main menu
     *TODO increase speed every 20 secs
     * TODO add powerups!
     */
    final Pong game;

        Player player1, player2;
        Ball pingPongBall;
        Circle ballCircle;
        Random rand2;
        private boolean isPlaying;

        private Sound boundaty_hit_sound;
        private Sound paddle_hit_sound;

        BitmapFont ScoreTracker;
        String Score;
        String p1Score, p2Score;
        int p1score, p2score;
        SpriteBatch scoreBatch;

        private OrthographicCamera camera;


        public GameScreen (final Pong game) {
            this.game = game;
            boundaty_hit_sound = Gdx.audio.newSound(Gdx.files.internal("boundary_hit_sound.wav"));
            isPlaying = false;
            p1score = 0;
            p2score = 0;
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            ScoreTracker = new BitmapFont();

            scoreBatch = new SpriteBatch();
            player1 = new Player(0f, Gdx.graphics.getHeight()/2);
            player2 = new Player(Gdx.graphics.getWidth() - 25f, Gdx.graphics.getHeight()/2);
            pingPongBall = new Ball();
            ballCircle = pingPongBall.getBallCircle();
            rand2 = new Random();
            Score = "Score: ";
            p1Score = "P1: 0";
            p2Score = "P2: 0";
        }
    @Override
    public void show() {

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
        pingPongBall.moveBall();
        game.batch.begin();
        game.font.draw(game.batch, Score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        game.font.draw(game.batch, p1Score, ((Gdx.graphics.getWidth() / 2)), ((Gdx.graphics.getHeight() / 2) - 20f));
        game.font.draw(game.batch, p2Score, ((Gdx.graphics.getWidth() / 2) + 50f), ((Gdx.graphics.getHeight() / 2) - 20f));
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (player1.getY_pos() + 100f < Gdx.graphics.getHeight()) {
                player1.setY_pos((player1.getY_pos() + 5f));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (player1.getY_pos() > 0f) {
                player1.setY_pos((player1.getY_pos() - 5f));
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (player2.getY_pos() + 100f < Gdx.graphics.getHeight()) {
                player2.setY_pos((player2.getY_pos() + 5f));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (player2.getY_pos() > 0f) {
                player2.setY_pos((player2.getY_pos() - 5f));
            }
        }

        if (player1.isCollied(ballCircle)) {
            isPlaying = true;
            pingPongBall.setDir_x(5f);
            pingPongBall.setDir_y((float) rand2.nextInt(10) - 5);
            if(isPlaying){
                isPlaying = false;
                boundaty_hit_sound.play();
            }
        }

        if (player2.isCollied(ballCircle)) {
            isPlaying = true;
            pingPongBall.setDir_x(-5f);
            pingPongBall.setDir_y((float) rand2.nextInt(10) - 5);
            if(isPlaying){
                isPlaying = false;
                boundaty_hit_sound.play();
            }
        }

        if (pingPongBall.getY_pos() + pingPongBall.getBALL_RADIUS() > Gdx.graphics.getHeight()) {
            pingPongBall.setDir_y(-1 * (pingPongBall.getDir_y()));
            boundaty_hit_sound.play();
        }

        if (pingPongBall.getY_pos() - pingPongBall.getBALL_RADIUS() < 0f) {
            pingPongBall.setDir_y(-1 * (pingPongBall.getDir_y()));
            boundaty_hit_sound.play();
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

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            camera.update();
            game.table.clear();
            this.game.setScreen(new MainMenuScreen(game));
            dispose();
        }
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
        public void dispose () {
            scoreBatch.dispose();
        }
    }

