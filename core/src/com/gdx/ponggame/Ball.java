package com.gdx.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class Ball {
    private float x_pos;
    Random rand;
    private float y_pos;
    private Color color;
    private float dir_x;
    private float dir_y;
    private final float BALL_RADIUS;

    private Circle ballCircle; //collision detection

    private ShapeRenderer ballShape;

    public Ball(){
        rand = new Random(); //random initial velocity
        dir_x = rand.nextFloat(5);
        dir_y = rand.nextFloat(1);
        x_pos = Gdx.graphics.getWidth()/2;
        y_pos = Gdx.graphics.getHeight()/2;
        BALL_RADIUS = 15f;
        ballShape = new ShapeRenderer();
        color = Color.WHITE;

        ballCircle = new Circle(this.x_pos, this.y_pos, BALL_RADIUS);
    }

    public void DrawBall(){
        ballCircle.setPosition(x_pos, y_pos);
        ballShape.begin(ShapeRenderer.ShapeType.Filled);
        ballShape.setColor(color);
        ballShape.circle(x_pos, y_pos, BALL_RADIUS);
        ballShape.end();
    }

    public void moveBall(){
        x_pos += dir_x;
        y_pos += dir_y;
    }

    public void setX_pos(float x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(float y_pos) {
        this.y_pos = y_pos;
    }

    public float getX_pos() {
        return x_pos;
    }

    public float getY_pos() {
        return y_pos;
    }

    public Circle getBallCircle() {
        return ballCircle;
    }

    public void setDir_x(float dir_x) {
        this.dir_x = dir_x;
    }

    public void setDir_y(float dir_y) {
        this.dir_y = dir_y;
    }

    public float getDir_x() {
        return dir_x;
    }

    public float getDir_y() {
        return dir_y;
    }

    public float getBALL_RADIUS() {
        return BALL_RADIUS;
    }
}
