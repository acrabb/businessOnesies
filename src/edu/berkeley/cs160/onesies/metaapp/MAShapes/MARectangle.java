package edu.berkeley.cs160.onesies.metaapp.MAShapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MARectangle extends MAScreenElement {
	
	private int left, top, width, height;
	private Paint paint;
	
	public MARectangle(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.SHAPE);
		// Set background to be some image
		this.mIsLinkable = true;
		paint = new Paint();
		setBackgroundColor(getResources().getColor(R.color.clearColor));
	}
	
	public MARectangle(Context context, AttributeSet attrs) {
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
		Log.d("meta", "OnDraw called");
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(6);
		this.width = getWidth();
		this.height = getHeight();
		canvas.drawRect(0,0, this.width, this.height, paint);
	}
	
	/*
	public void redraw(int left, int top, int width, int height) {
		this.left = left;
		this.top = top;
		this.width = width;
	}
	*/
}