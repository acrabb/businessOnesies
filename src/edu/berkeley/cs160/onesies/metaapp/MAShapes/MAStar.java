package edu.berkeley.cs160.onesies.metaapp.MAShapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAStar extends MAScreenElement {
	
	private int left, top, width, height;
	private Paint paint;
	private Path path;
	
	public MAStar(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.SHAPE);
		// Set background to be some image
		paint = new Paint();
		path = new Path();
		paint.setStrokeWidth(3);
		setBackgroundColor(getResources().getColor(R.color.clearColor));
	}
	
	public MAStar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onAttachedToWindow() {
//		this.height = 200;
//		this.width = 200;
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
//		params.height = 200;
//		params.width = 200;
//		setLayoutParams(params);
//		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		// top left: 0, 0
		this.width = getWidth();
		this.height = getHeight();
		
		float m = Math.min(this.width, this.height);
		path.reset();
		// 5 point star, numbered 1 - 5 starting at top point going clockwise
		// point 1
		float r = m / 2;
		float cx = r;
		float cy = r;
		float[] px = new float[5];
		float[] py = new float[5];
		for (int i = 0; i < 5; i++) {
			px[i] = (float) (cx + r*(Math.cos(- Math.PI/2 + 2*i*Math.PI/5)));
			py[i] = (float) (cy + r*(Math.sin(- Math.PI/2 + 2*i*Math.PI/5)));
		}
		
		float ri = (float) ((cy - py[1]) / Math.cos(2*Math.PI/10));
		float[] pxi = new float[5];
		float[] pyi = new float[5];
		for (int i = 0; i < 5; i++) {
			pxi[i] = (float) (cx + ri*(Math.cos(Math.PI/2 + 2*(i-2)*Math.PI/5)));
			pyi[i] = (float) (cy + ri*(Math.sin(Math.PI/2 + 2*(i-2)*Math.PI/5)));
		}
		
		path.moveTo(px[0], py[0]);
		for (int i = 1; i < 5; i++) {
			path.lineTo(pxi[i-1], pyi[i-1]);
			path.lineTo(px[i], py[i]);
		}
		path.lineTo(pxi[4], pyi[4]);
		path.close();
		
		canvas.drawPath(path, paint);
		// on 5/3/2013, 11:51pm, I actually had an idea about what was going on here
	}
}