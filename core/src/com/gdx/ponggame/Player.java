package com.gdx.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {
    private float x_pos;
    private float y_pos;
    private Color color;

    boolean checkCollision;
    private SpriteBatch batch;

    final float PLAYER_WIDTH;
    final float PLAYER_HEIGHT;
    private ShapeRenderer drawPlayer;

    private BitmapFont playerID;

    private Rectangle playerRect; //colission detection

    Player(float x_pos, float y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        drawPlayer = new ShapeRenderer();
        color = Color.WHITE;
        PLAYER_HEIGHT = 100f;
        PLAYER_WIDTH = 25f;
        playerRect = new Rectangle(x_pos, y_pos,PLAYER_WIDTH, PLAYER_HEIGHT); //colission detection
        batch = new SpriteBatch();
        playerID = new BitmapFont();
    }

    void DrawPlayer(){
        playerRect.setPosition(x_pos, y_pos);
        drawPlayer.begin(ShapeRenderer.ShapeType.Filled);
        drawPlayer.setColor(color);
        drawPlayer.rect(x_pos, y_pos, PLAYER_WIDTH, PLAYER_HEIGHT);
        drawPlayer.end();
    }

    public float getX_pos() {
        return x_pos;
    }

    public float getY_pos() {
        return y_pos;
    }

    public void setX_pos(float x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(float y_pos) {
        this.y_pos = y_pos;
    }

    //check if the ball game object collides with the player game object
    public boolean isCollied(Circle ballCircle){
        if(Intersector.overlaps(ballCircle, playerRect)){
            checkCollision = true;
            return checkCollision;
        }
        checkCollision = false;
        return checkCollision;
    }

    public void setCheckCollision(boolean checkCollision) {
        this.checkCollision = checkCollision;
    }

    public void player_ID(String n){
        batch.begin();
        playerID.setColor(1f,1f,1f,1f);
        playerID.draw(batch, n, x_pos, (y_pos + 5f));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
