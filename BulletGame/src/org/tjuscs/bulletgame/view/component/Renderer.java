package org.tjuscs.bulletgame.view.component;

import org.tjuscs.bulletgame.util.MathUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.Matrix4;

public class Renderer {
	private SpriteBatch batch;
	private PerspectiveCamera perspectiveCamera;
	public static final int MODE_3D = 1, MODE_2D = 0;

	private Renderer() {
		batch = new SpriteBatch();
		perspectiveCamera = new PerspectiveCamera();
		setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public PerspectiveCamera get3DCamera() {
		return perspectiveCamera;
	}

	public void setFog(float fogStart, float fogEnd, float fogColorR,
			float fogColorG, float fogColorB, float fogColorA) {
		GL10 gl = Gdx.graphics.getGL10();
		if (fogStart > 0) {
			gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
			gl.glFogf(GL10.GL_FOG_START, fogStart);
			gl.glFogf(GL10.GL_FOG_END, fogEnd);
		} else if (fogStart == -1) {
			gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_EXP);
			gl.glFogf(GL10.GL_FOG_DENSITY, fogEnd);
		} else if (fogStart == -2) {
			gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_EXP2);
			gl.glFogf(GL10.GL_FOG_DENSITY, fogEnd);
		}
		gl.glFogfv(GL10.GL_FOG_COLOR, new float[] { fogColorR, fogColorG,
				fogColorB, fogColorA }, 0);

		gl.glHint(GL10.GL_FOG_HINT, GL10.GL_DONT_CARE);
		if (fogStart == fogEnd)
			gl.glDisable(GL10.GL_FOG);
		else
			gl.glEnable(GL10.GL_FOG);
	}

	public void setSize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		perspectiveCamera.viewportWidth = width;
		perspectiveCamera.viewportHeight = height;
		perspectiveCamera.update();
	}

	/**
	 * 切换渲染模式 0-2d 1-3d
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		switch (mode) {
		case 0:
			Matrix4 proj = batch.getProjectionMatrix();
			Matrix4 trans = batch.getTransformMatrix();
			GL10 gl = Gdx.gl10;
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadMatrixf(proj.val, 0);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadMatrixf(trans.val, 0);
			break;
		case 1:
			perspectiveCamera.apply(Gdx.gl10);
			break;
		default:
			break;
		}
	}

	private static Renderer instance;

	public static Renderer getInstance() {
		if (instance == null)
			instance = new Renderer();
		return instance;
	}

	public void startRender() {
		perspectiveCamera.update();
		batch.begin();
	}

	public void endRender() {
		batch.end();
	}

	public void RenderClear(float r, float g, float b, float a) {
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	public void Render(String imagename, double x, double y) {
		Render(imagename, x, y, 0);
	}

	public void Render(String imagename, double x, double y, double rot) {
		Render(imagename, x, y, rot, 1);
	}

	public void Render(String imagename, double x, double y, double rot,
			double scale) {
		Render(imagename, x, y, rot, scale, scale);
	}

	public void Render(String imagename, double x, double y, double rot,
			double hscale, double vscale) {
		Render(imagename, x, y, rot, hscale, vscale, 0.5);
	}

	public void Render(String imagename, double x, double y, double rot,
			double hscale, double vscale, double z) {
		Image img = Resources.getInstance().images.get(imagename);
		if(img == null){
			Animation anim = Resources.getInstance().anims.get(imagename);
			if(anim == null){
				System.err.println("Image or Animation: '"+imagename+"' not exist!");
			}
			img = anim.getCurrentImage();
			anim.update();
		}
		int h = img.getRegionWidth();
		int w = img.getRegionHeight();
		double cy = img.centerX;
		double cx = w - img.centerY;
		batch.setColor(img.color);
		batch.draw(img, (float) (x - cx), (float) (y - cy), (float)cx, (float)cy, w, h,
				(float) vscale, (float) hscale, (float) rot + 90, true);
	}

	public void RenderRect(String imagename, int x1, int x2, int y1, int y2) {
		Image img = Resources.getInstance().images.get(imagename);
		batch.draw(img, x1, y1, x2 - x1, y2 - y1);
	}

	public void RenderText(String fontname, String text, int x, int y) {
		BitmapFont font = Resources.getInstance().bitmapFonts.get(fontname);
		font.drawMultiLine(batch, text, x, y);
	}
	
	public TextBounds getTextBounds(String fontname, String text){
		BitmapFont font = Resources.getInstance().bitmapFonts.get(fontname);
		return font.getBounds(text);
	}
	
	public void RenderTextCenter(String fontname, String text,int y) {
		BitmapFont font = Resources.getInstance().bitmapFonts.get(fontname);
		font.drawMultiLine(batch, text, (Gdx.graphics.getWidth()-font.getBounds(text).width)/2, y);
	}

	public void RenderParticle(String particlename, double delta, double x,
			double y, double rot) {
		ParticleEffect pe = Resources.getInstance().particles.get(particlename);

//		GL10 gl = Gdx.graphics.getGL10();
//		gl.glPushMatrix();
//		gl.glTranslatef((float) x, (float) y, 0);
//		gl.glRotatef((float) rot, 0, 0, 1);
		pe.setPosition((int)x, (int)y);
		pe.draw(batch, (float) delta);
		//gl.glPopMatrix();
	}

	public void Render3D(String imagename, double x, double y, double rotation,
			double scaleX, double scaleY, double z) {
		Image img = Resources.getInstance().images.get(imagename);

		// Vector3 v = new Vector3(perspectiveCamera.viewportWidth,
		// perspectiveCamera.viewportHeight, 0.5f);
		// perspectiveCamera.unproject(v);
		// Vector3 v = perspectiveCamera.frustum.planePoints[0];
		float rate = 0.009f;// v.x / perspectiveCamera.viewportWidth;
		// float rate = (float) Math.tan(perspectiveCamera.fieldOfView * 0.5 *
		// MathUtils.degRad) *
		// perspectiveCamera.near*2/perspectiveCamera.viewportWidth;
		// z -= v.z;
		y -= 1.45;

		float width = img.getRegionWidth() * rate;
		float height = img.getRegionHeight() * rate;
		float originX = (float) (img.centerX * rate);
		float originY = (float) (height - img.centerY * rate);

		final float worldOriginX = (float) x;
		final float worldOriginY = (float) (y + 1.5);
		float fx = -originX;
		float fy = -originY;
		float fx2 = width - originX;
		float fy2 = height - originY;

		// scale
		if (scaleX != 1 || scaleY != 1) {
			fx *= scaleX;
			fy *= scaleY;
			fx2 *= scaleX;
			fy2 *= scaleY;
		}

		// construct corner points, start from top left and go counter clockwise
		final float p1x = fx;
		final float p1y = fy;
		final float p2x = fx;
		final float p2y = fy2;
		final float p3x = fx2;
		final float p3y = fy2;
		final float p4x = fx2;
		final float p4y = fy;

		float x1;
		float y1;
		float x2;
		float y2;
		float x3;
		float y3;
		float x4;
		float y4;

		// rotate
		if (rotation != 0) {
			final float cos = MathUtils.cosDeg(rotation);
			final float sin = MathUtils.sinDeg(rotation);

			x1 = cos * p1x - sin * p1y;
			y1 = sin * p1x + cos * p1y;

			x2 = cos * p2x - sin * p2y;
			y2 = sin * p2x + cos * p2y;

			x3 = cos * p3x - sin * p3y;
			y3 = sin * p3x + cos * p3y;

			x4 = x1 + (x3 - x2);
			y4 = y3 - (y2 - y1);
		} else {
			x1 = p1x;
			y1 = p1y;

			x2 = p2x;
			y2 = p2y;

			x3 = p3x;
			y3 = p3y;

			x4 = p4x;
			y4 = p4y;
		}

		x1 += worldOriginX;
		y1 += worldOriginY;
		x2 += worldOriginX;
		y2 += worldOriginY;
		x3 += worldOriginX;
		y3 += worldOriginY;
		x4 += worldOriginX;
		y4 += worldOriginY;

		Render4V(imagename, x2, y2, z, x3, y3, z, x4, y4, z, x1, y1, z, true);
	}

	public void Render4V(String imagename, double x0, double y0, double z0,
			double x1, double y1, double z1, double x2, double y2, double z2,
			double x3, double y3, double z3) {
		Render4V(imagename, x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3,
				false);
	}

	public void Render4V(String imagename, double x0, double y0, double z0,
			double x1, double y1, double z1, double x2, double y2, double z2,
			double x3, double y3, double z3, boolean blend) {
		Image img = Resources.getInstance().images.get(imagename);
		Mesh mesh = new Mesh(true, 4, 6, new VertexAttribute(Usage.Position, 3,
				"a_position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_color"), new VertexAttribute(Usage.TextureCoordinates, 2,
				"a_texCoords"));

		final float color = img.color.toFloatBits();
		final float u = img.getU();
		final float v = img.getV2();
		final float u2 = img.getU2();
		final float v2 = img.getV();

		mesh.setVertices(new float[] { (float) x0, (float) y0, (float) z0,
				color, u, v2, (float) x1, (float) y1, (float) z1, color, u2,
				v2, (float) x2, (float) y2, (float) z2, color, u2, v,
				(float) x3, (float) y3, (float) z3, color, u, v });

		mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
		GL10 gl = Gdx.graphics.getGL10();
		if (blend)
			gl.glEnable(GL10.GL_BLEND);
		img.getTexture().bind();
		mesh.render(GL10.GL_TRIANGLES, 0, 6);
		mesh.dispose();
		if (blend)
			gl.glDisable(GL10.GL_BLEND);
	}
}
