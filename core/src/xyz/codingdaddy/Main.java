package xyz.codingdaddy;

import java.util.concurrent.TimeUnit;

import xyz.codingdaddy.hud.CooldownTimer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Main extends ApplicationAdapter {

	private Stage stage;
	private CooldownTimer cooldownTimer;
	
	private long lastUpdate = 0L;
	private float remainingPercentage = 1.0f;
	
	@Override
	public void create () {
		stage = new Stage();
		
		cooldownTimer = new CooldownTimer();
		
		stage.addActor(cooldownTimer);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (System.currentTimeMillis() - lastUpdate > TimeUnit.SECONDS.toMillis(1)) {
			cooldownTimer.update(remainingPercentage);
			remainingPercentage -= 0.01f;
			lastUpdate = System.currentTimeMillis();
		}
		
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
