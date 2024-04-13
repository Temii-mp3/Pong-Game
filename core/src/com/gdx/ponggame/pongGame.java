package com.gdx.ponggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class pongGame extends ApplicationAdapter {
	Player player1, player2;
	Ball pingPongBall;
	Circle ballCircle;
	Random rand2;
	private OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);


		player1 = new Player(0f, Gdx.graphics.getHeight()/2);
		player2 = new Player(Gdx.graphics.getWidth() - 25f, Gdx.graphics.getHeight()/2);
		pingPongBall = new Ball();
		ballCircle = pingPongBall.getBallCircle();
		rand2 = new Random();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		camera.update();
		player1.DrawPlayer();
		player2.DrawPlayer();
		player1.player_ID("1");
		player2.player_ID("2");
		pingPongBall.DrawBall();
		pingPongBall.moveBall();

		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			if(player1.getY_pos() + 100f < Gdx.graphics.getHeight()){
				player1.setY_pos((player1.getY_pos() + 5f));
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			if(player1.getY_pos() >  0f){
				player1.setY_pos((player1.getY_pos() - 5f));
			}
		}


		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			if(player2.getY_pos() + 100f < Gdx.graphics.getHeight()){
				player2.setY_pos((player2.getY_pos() + 5f));
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if(player2.getY_pos() >  0f){
				player2.setY_pos((player2.getY_pos() - 5f));
			}
		}

		if(player1.isCollied(ballCircle)){
			pingPongBall.setDir_x(5f);
			pingPongBall.setDir_y((float) rand2.nextInt(10) - 5);
		}

		if(player2.isCollied(ballCircle)){
			pingPongBall.setDir_x(-5f);
			pingPongBall.setDir_y((float) rand2.nextInt(10) - 5);
		}

		if(pingPongBall.getY_pos() + pingPongBall.getBALL_RADIUS() > Gdx.graphics.getHeight()){
			pingPongBall.setDir_y(-1 * (pingPongBall.getDir_y()));
		}

		if(pingPongBall.getY_pos() - pingPongBall.getBALL_RADIUS() < 0f ){
			pingPongBall.setDir_y(-1 * (pingPongBall.getDir_y()));
		}
	}
	
	@Override
	public void dispose () {
	}
}
