package edu.berkeley.cs160.onesies.metaapp.MAShapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
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
		setBackgroundColor(getResources().getColor(R.color.clearColor));
	}
	
	public MAStar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onAttachedToWindow() {
		this.height = 200;
		this.width = 200;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
		params.height = 200;
		params.width = 200;
		setLayoutParams(params);
		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		// top left: 0, 0
		this.width = getWidth();
		this.height = getHeight();
		
		float m = Math.min(this.width, this.height);
		path.reset();
		// 5 point star, numbered 1 - 5 starting at top point going clockwise
		// point 1
		float x1 = m / 2;
		float y1 = 0;
		
		float x2 = m;
		float y2 = (float) (m - m * Math.sin(Math.toRadians(36)));
		
		float x3 = m - (float) (m/2 - m * Math.cos(Math.toRadians(72)));
		float y3 = m;

		float x4 = m - x3;
		float y4 = m;

		float x5 = 0;
		float y5 = y2;

		path.moveTo(x1, y1);
		path.lineTo(x4, y4);
		path.lineTo(x2, y2);
		path.lineTo(x5, y5);
		path.lineTo(x3, y3);
		path.close();

		canvas.drawPath(path, paint);
	}
}