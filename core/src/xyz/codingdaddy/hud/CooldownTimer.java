package xyz.codingdaddy.hud;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CooldownTimer extends Table {
	
	private static final float START_ANGLE = 90;
	
	private Table cooldownDisplay;
	private TextureRegionDrawable cooldownTexture;
	
	public CooldownTimer() {
		cooldownDisplay = new Table();
		cooldownDisplay.setPosition(0, 0);
		addActor(cooldownDisplay);
	}
	
	public void update(float remainingPercentage) {
		cooldownDisplay.clear();
		
		Image cooldownTimer = new Image(cooldownTimer(100, 100, 90, remainingPercentage));
		cooldownTimer.setPosition(5, 5);
		cooldownTimer.setColor(1.0f, 1.0f, 1.0f, 0.5f);
		cooldownDisplay.addActor(cooldownTimer);
	}
	
	// 100, 100, 90, 90, angle
	private Drawable cooldownTimer(float x, float y, float radius, float remainingPercentage) {
		float angle = 360 - 360 * remainingPercentage;
		int segments = calculateSegments(angle);
		
		System.out.println(angle);

		Pixmap display = new Pixmap(200, 200, Format.RGBA8888);
		Pixmap finalCooldownDisplay = new Pixmap(60, 60, Format.RGBA8888);
		Pixmap round = new Pixmap(finalCooldownDisplay.getWidth(), finalCooldownDisplay.getHeight(), Pixmap.Format.RGBA8888);
		

		try {
			float theta = (2 * MathUtils.PI * (angle / 360.0f)) / segments;
			float cos = MathUtils.cos(theta);
			float sin = MathUtils.sin(theta);
			float cx = radius * MathUtils.cos(START_ANGLE * MathUtils.degreesToRadians);
			float cy = radius * MathUtils.sin((-1 * START_ANGLE) * MathUtils.degreesToRadians);

			display.setColor(1.0f, 1.0f, 1.0f, 1.0f);

			for (int count = 0; count < segments; count++) {
				float pcx = cx;
				float pcy = cy;
				float temp = cx;
				cx = cos * cx - sin * cy;
				cy = sin * temp + cos * cy;
				display.fillTriangle((int) x, (int) y, (int) (x + pcx), (int) (y + pcy), (int) (x + cx), (int) (y + cy));
			}

			finalCooldownDisplay.drawPixmap(display, 0, 0, 70, 70, 60, 60);

			Pixmap.setBlending(Blending.None);
			
			for(int yy=0;yy<finalCooldownDisplay.getHeight();yy++)
		    {
		        for(int xx=0;xx<finalCooldownDisplay.getWidth();xx++)
		        {
		            //check if pixel is outside circle. Set pixel to transparant;
		            double dist_x = (30 - xx);
		            double dist_y = 30 - yy;
		            double dist = Math.sqrt((dist_x*dist_x) + (dist_y*dist_y));
		            if(dist < 10)
		            {
		                round.drawPixel(xx, yy, finalCooldownDisplay.getPixel(xx, yy));
		            }
		            else
		                round.drawPixel(xx, yy, 0);
		        }
		    }

			if (cooldownTexture == null) {
				cooldownTexture = new TextureRegionDrawable(new TextureRegion(new Texture(round)));
			} else {
				cooldownTexture.getRegion().getTexture().draw(round, 0, 0);
			}

			return cooldownTexture;
		} finally {
			display.dispose();
			finalCooldownDisplay.dispose();
			round.dispose();
		}
	}
	
	private static int calculateSegments(float angle) {
		return Math.max(1, (int) (6 * (float) Math.cbrt(Math.abs(angle)) * (Math.abs(angle) / 360.0f)));
	}
}
