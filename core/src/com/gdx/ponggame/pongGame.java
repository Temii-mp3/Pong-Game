package com.gdx.ponggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;


/**TODO Main menu
 *TODO increase speed every 20 secs
 * TODO add powerups!
 */
public class pongGame extends ApplicationAdapter {
	Player player1, player2;
	Ball pingPongBall;
	Circle ballCircle;
	Random rand2;

	private Sound boundaty_hit_sound;
	private Sound paddle_hit_sound;

	BitmapFont ScoreTracker;
	String Score;
	String p1Score, p2Score;
	int p1score, p2score;
	SpriteBatch scoreBatch;

	private OrthographicCamera camera;

	@Override
	public void create () {


		boundaty_hit_sound = Gdx.audio.newSound(Gdx.files.internal("boundary_hit_sound.wav"));

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
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		camera.update();
		player1.DrawPlayer();
		player2.DrawPlayer();
		player1.player_ID("P1");
		player2.player_ID("P2");
		pingPongBall.DrawBall();
		pingPongBall.moveBall();
		scoreBatch.begin();
		ScoreTracker.draw(scoreBatch, Score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		ScoreTracker.draw(scoreBatch, p1Score, ((Gdx.graphics.getWidth() / 2)), ((Gdx.graphics.getHeight() / 2) - 20f));
		ScoreTracker.draw(scoreBatch, p2Score, ((Gdx.graphics.getWidth() / 2) + 50f), ((Gdx.graphics.getHeight() / 2) - 20f));
		scoreBatch.end();

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
			pingPongBall.setDir_x(5f);
			pingPongBall.setDir_y((float) rand2.nextInt(10) - 5);
			boundaty_hit_sound.play();
		}

		if (player2.isCollied(ballCircle)) {
			pingPongBall.setDir_x(-5f);
			pingPongBall.setDir_y((float) rand2.nextInt(10) - 5);
			boundaty_hit_sound.play();
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

	}
	
	@Override
	public void dispose () {
		scoreBatch.dispose();
	}
}
