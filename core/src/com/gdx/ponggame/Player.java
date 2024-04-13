package com.gdx.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {
    private Color color;

    boolean checkCollision;
    private SpriteBatch batch;

    private Vector2 playerPos;

    final float PLAYER_WIDTH;
    final float PLAYER_HEIGHT;
    private ShapeRenderer drawPlayer;

    private BitmapFont playerID;

    private Rectangle playerRect; //colission detection

    Player(float x_pos, float y_pos){
        drawPlayer = new ShapeRenderer();
        color = Color.WHITE;
        PLAYER_HEIGHT = 100f;
        PLAYER_WIDTH = 25f;
        playerRect = new Rectangle(x_pos, y_pos,PLAYER_WIDTH, PLAYER_HEIGHT); //colission detection
        batch = new SpriteBatch();
        playerID = new BitmapFont();
        playerPos = new Vector2(x_pos, y_pos);
    }

    void DrawPlayer(){
        playerRect.setPosition(playerPos);
        drawPlayer.begin(ShapeRenderer.ShapeType.Filled);
        drawPlayer.setColor(color);
        drawPlayer.rect(playerPos.x, playerPos.y, PLAYER_WIDTH, PLAYER_HEIGHT);
        drawPlayer.end();
    }

    public float getX_pos() {
        return playerPos.x;
    }

    public float getY_pos() {
        return playerPos.y;
    }

    public void setX_pos(float x_pos) {
        playerPos.x = x_pos;
    }

    public void setY_pos(float y_pos) {
        playerPos.y = y_pos;
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
        playerID.draw(batch, n, playerPos.x, (playerPos.y - 2f));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
