package com.mygdx.runrunrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.runrunrun.handler.Content;
import com.mygdx.runrunrun.states.GSM;
import com.mygdx.runrunrun.states.PlayState;

public class Main extends ApplicationAdapter {

    public static final String TITLE = "Run Run Run!";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public static final int GROUND_LEVEL = 30;

    private GSM gsm;
    private SpriteBatch sb;
    private ShapeRenderer sr;

    public static Content resource;


	@Override
	public void create () {

        resource = new Content();
        resource.loadAtlas("pack1.pack","assets");

        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        gsm = new GSM();
        gsm.push(new PlayState(gsm,5));

	}

	@Override
	public void render () {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
        gsm.shapeRender(sr);

	}
}
