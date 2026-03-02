package com.gdx.ponggame;

import java.util.ArrayList;
import java.util.Optional;
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
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;
public class GameScreen implements Screen {
    /**TODO Main menu
     *TODO increase speed every 20 secs
     * TODO add powerups!
     */
    final Pong game;

        Player player1, player2;
        BodyDef player1BodyDef, player2BodyDef, ballBodyDef, wall1Def, wall2Def;
        Body player1Body, player2Body, ballBody, wall1Body, wall2Body;
        PolygonShape player1Paddle, player2Paddle, wall1, wall2;
        CircleShape ball;
        FixtureDef player1Fixture, player2Fixture, ballFixture, wall1Fixture, wall2Fixture;
        final float PLAYER_SPEED = 500f;
        int velocityIterations = 6;
        int positionIterations = 2;
        boolean PADDLE_HIT;
    Box2DDebugRenderer debugRenderer;
        Ball pingPongBall;
        Circle ballCircle;
        final int PPM = 100;
    float wallThickness = 10f /PPM;
        Random rand2;
        float networkTimer;
        private boolean isPlaying;
         Client client;
         Server server;
         World world = new World((new Vector2(0, -0.5f)), true);


        private Sound boundaty_hit_sound;
        private Sound paddle_hit_sound;

        BitmapFont ScoreTracker;
        String Score;
        String p1Score, p2Score;
        int p1score, p2score;
        SpriteBatch scoreBatch;
        Option option;
        Type type;
        private OrthographicCamera camera;
        public GameScreen (final Pong game, Option option, Type type, Client client, Server server) {
            this.game = game;
            this.option = option;
            this.type = type;
            boundaty_hit_sound = Gdx.audio.newSound(Gdx.files.internal("boundary_hit_sound.wav"));
            isPlaying = false;
            p1score = 0;
            p2score = 0;
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            ScoreTracker = new BitmapFont();
            scoreBatch = new SpriteBatch();
            debugRenderer = new Box2DDebugRenderer();
            player1 = new Player(0f, Gdx.graphics.getHeight()/2);
            player2 = new Player(Gdx.graphics.getWidth() - 25f, Gdx.graphics.getHeight()/2);
            pingPongBall = new Ball();
            ballCircle = pingPongBall.getBallCircle();
            rand2 = new Random();
            Score = "Score: ";
            p1Score = "P1: 0";
            p2Score = "P2: 0";
            this.server = server;
            this.client = client;
            player1BodyDef = new BodyDef();
            player2BodyDef = new BodyDef();
            ballBodyDef = new BodyDef();
            wall1Def = new BodyDef();
            wall2Def = new BodyDef();



            player1BodyDef.type = BodyDef.BodyType.KinematicBody;
            player1BodyDef.position.set(player1.getX_pos()/PPM, player1.getY_pos()/PPM);

            player2BodyDef.type = BodyDef.BodyType.KinematicBody;
            player2BodyDef.position.set(player2.getX_pos()/PPM, player2.getY_pos()/PPM);

            ballBodyDef.type = BodyDef.BodyType.DynamicBody;
            ballBodyDef.position.set(pingPongBall.getX_pos()/PPM, pingPongBall.getY_pos()/PPM);

            wall1Def.type = BodyDef.BodyType.StaticBody;
            wall1Def.position.set(0, -(wallThickness/2));
            wall2Def.type = BodyDef.BodyType.StaticBody;
            wall2Def.position.set(0, (float) (Gdx.graphics.getHeight() /PPM) + (wallThickness/2));



            player1Body = world.createBody(player1BodyDef);
            player2Body = world.createBody(player2BodyDef);
            ballBody = world.createBody(ballBodyDef);
            wall1Body = world.createBody(wall1Def);
            wall2Body = world.createBody(wall2Def);

            player1Paddle = new PolygonShape();
            player1Paddle.setAsBox(25f/PPM, 50f/PPM);
            player1Fixture = new FixtureDef();
            player1Fixture.shape = player1Paddle;
            player1Fixture.density = 1.0f;
            player1Body.createFixture(player1Fixture);

            wall1 = new PolygonShape();
            wall1.setAsBox((float) Gdx.graphics.getWidth() /PPM, 50f/PPM);
            wall1Fixture = new FixtureDef();
            wall1Fixture.shape = wall1;
            wall1Fixture.density = 1.0f;
            wall1Body.createFixture(wall1Fixture);

            wall2 = new PolygonShape();
            wall2.setAsBox((float) Gdx.graphics.getWidth() /PPM, 50f/PPM);
            wall2Fixture = new FixtureDef();
            wall2Fixture.shape = wall2;
            wall2Fixture.density = 1.0f;
            wall2Body.createFixture(wall2Fixture);

            player2Paddle = new PolygonShape();
            player2Paddle.setAsBox(25f/PPM, 50f/PPM);
            player2Fixture = new FixtureDef();
            player2Fixture.shape = player2Paddle;
            player2Fixture.density = 1.0f;
            player2Body.createFixture(player2Fixture);

            ball = new CircleShape();
            ball.setRadius(pingPongBall.getBALL_RADIUS()/PPM);
            ballFixture = new FixtureDef();
            ballFixture.shape = ball;
            ballFixture.density = 1.0f;
            ballBody.createFixture(ballFixture);

            ballBody.setLinearVelocity(rand2.nextFloat() * 4 + 1,rand2.nextFloat() * 4 + 1);


            world.setContactListener(new ContactListener() {
                @Override
                public void beginContact(Contact contact) {
                    PADDLE_HIT = true;
                }

                @Override
                public void endContact(Contact contact) {

                }

                @Override
                public void preSolve(Contact contact, Manifold manifold) {

                }

                @Override
                public void postSolve(Contact contact, ContactImpulse contactImpulse) {

                }
            });



        }


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
        player1 = new Player(0f, (float) Gdx.graphics.getHeight() /2);
        player2 = new Player(Gdx.graphics.getWidth() - 25f, (float) Gdx.graphics.getHeight() /2);
        pingPongBall = new Ball();
        ballCircle = pingPongBall.getBallCircle();
        rand2 = new Random();
        Score = "Score: ";
        p1Score = "P1: 0";
        p2Score = "P2: 0";
    }
    @Override
    public void show() {
            if(type == Type.IS_CLIENT){
                client.listen(msg -> {
                    System.out.println(msg);
                    String[] values = msg.split(",");
                    player1.setY_pos(Float.parseFloat(values[0]));
                    pingPongBall.setY_pos(Float.parseFloat(values[1]));
                    pingPongBall.setX_pos(Float.parseFloat(values[2]));
                    pingPongBall.setDir_y(Float.parseFloat(values[3]));
                    pingPongBall.setDir_x(Float.parseFloat(values[4]));


                    System.out.println("Message from Host: " + player1.getY_pos() );
                    System.out.println("In Here");
                });
            }else{
                server.listen(msg -> {
                    player2.setY_pos(Float.parseFloat(msg));
                    System.out.println("Message from Client: " + player1.getY_pos() );
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
        game.font.draw(game.batch, Score, (float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        game.font.draw(game.batch, p1Score, (((float) Gdx.graphics.getWidth() / 2)), ((Gdx.graphics.getHeight() / 2) - 20f));
        game.font.draw(game.batch, p2Score, ((Gdx.graphics.getWidth() / 2) + 50f), ((Gdx.graphics.getHeight() / 2) - 20f));
        game.batch.end();
        world.step(delta, velocityIterations, positionIterations);
        debugRenderer.render(world, camera.combined.cpy().scl(PPM));

        if(PADDLE_HIT){
            ballBody.setLinearVelocity(-ballBody.getLinearVelocity().x, rand2.nextFloat() * 4 + 1);
            PADDLE_HIT = false;
        }

        player1.setY_pos(player1Body.getPosition().y * PPM);
        player2.setY_pos(player2Body.getPosition().y * PPM);
        pingPongBall.setX_pos(ballBody.getPosition().x * PPM);
        pingPongBall.setY_pos(ballBody.getPosition().y * PPM);

        switch (type){
            case IS_HOST:

                String valueToSend = player1.getY_pos() + "," +
                                     pingPongBall.getY_pos() +
                                     "," + pingPongBall.getX_pos() +
                                      "," + ballBody.getLinearVelocity().y +
                                      "," + ballBody.getLinearVelocity().x;
                    server.sendInfo(valueToSend);


                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    player1Body.setLinearVelocity(0, PLAYER_SPEED/PPM);
                }else{
                    player1Body.setLinearVelocity(0, 0);
                }

                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    player1Body.setLinearVelocity(0, -(PLAYER_SPEED/PPM));
                }else{
                    player1Body.setLinearVelocity(0, 0);
                }
                break;
            case IS_CLIENT:
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    if (player2.getY_pos() + 100f < Gdx.graphics.getHeight()) {
                        player2.setY_pos((player2.getY_pos() + 5f));
                        client.sendInfo(String.valueOf(player2.getY_pos()));
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    if (player2.getY_pos() > 0f) {
                        player2.setY_pos((player2.getY_pos() - 5f));
                        client.sendInfo(String.valueOf(player2.getY_pos()));
                    }
                }
                break;
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

