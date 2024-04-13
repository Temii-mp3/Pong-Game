package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Ball {
    private Vector2 ball_pos;
    Random rand;
    private Vector2 ball_direction;
    private Color color;
    private final float BALL_RADIUS;

    private Circle ballCircle; //collision detection

    private ShapeRenderer ballShape;

    public Ball(){
        rand = new Random(); //random initial velocity
        ball_direction = new Vector2(rand.nextFloat(5f),rand.nextFloat(1f));
        ball_pos = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        BALL_RADIUS = 15f;
        ballShape = new ShapeRenderer();
        color = Color.WHITE;

        ballCircle = new Circle(ball_pos.x, ball_pos.y, BALL_RADIUS);
    }

    public void DrawBall(){
        ballCircle.setPosition(ball_pos);
        ballShape.begin(ShapeRenderer.ShapeType.Filled);
        ballShape.setColor(color);
        ballShape.circle(ball_pos.x,ball_pos.y, BALL_RADIUS);
        ballShape.end();
    }

    public void moveBall(){
        ball_pos.add(ball_direction);
    }

    public void setX_pos(float x_pos) {
        ball_pos.x = x_pos;
    }

    public void setY_pos(float y_pos) {
        ball_pos.y = y_pos;
    }

    public float getX_pos() {
        return ball_pos.x;
    }

    public float getY_pos() {
        return ball_pos.y;
    }

    public Circle getBallCircle() {
        return ballCircle;
    }

    public void setDir_x(float dir_x) {
        ball_direction.x = dir_x;
    }

    public void setDir_y(float dir_y) {
        ball_direction.y = dir_y;
    }

    public float getDir_x() {
        return ball_direction.x;
    }

    public float getDir_y() {
        return ball_direction.y;
    }

    public float getBALL_RADIUS() {
        return BALL_RADIUS;
    }

    public void resetPosition(){
        ball_pos.x = Gdx.graphics.getWidth()/2;
        ball_pos.y = Gdx.graphics.getHeight()/2;
    }
}
