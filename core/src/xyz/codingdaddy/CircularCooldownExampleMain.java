package xyz.codingdaddy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import xyz.codingdaddy.hud.CooldownTimer;

/**
 * Circular cooldown example renderer.
 * 
 * @author serhiy
 *
 */
public class CircularCooldownExampleMain extends ApplicationAdapter {

	private Stage stage;
	private CooldownTimer cooldownTimerBlue;
	private CooldownTimer cooldownTimerGray;
	
	private long lastUpdate = 0L;
	private float remainingPercentage = 1.0f;
	
	@Override
	public void create () {
		stage = new Stage();
		
		cooldownTimerBlue = new CooldownTimer(false);
		cooldownTimerBlue.setSize(100, 100);
		cooldownTimerBlue.setPosition(100, 100);
		cooldownTimerBlue.setColor(Color.BLUE);
		
		stage.addActor(cooldownTimerBlue);
		
		cooldownTimerGray = new CooldownTimer(true);
		cooldownTimerGray.setSize(100, 100);
		cooldownTimerGray.setPosition(100, 250);
		cooldownTimerGray.setColor(Color.GRAY);
		cooldownTimerGray.setAlpha(0.25f);
		
		stage.addActor(cooldownTimerGray);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (System.currentTimeMillis() - lastUpdate > 25L) {
			cooldownTimerBlue.update(remainingPercentage);
			cooldownTimerGray.update(remainingPercentage);
			
			remainingPercentage -= 0.01f;
			lastUpdate = System.currentTimeMillis();
			
			if (remainingPercentage <= 0.0f) {
				remainingPercentage = 1.0f;
			}
		}
		
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
