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
		this.setBackgroundResource(R.drawable.rectangle);
		paint = new Paint();
		Log.d("meta", "Created MARectangle");
	}

	public MARectangle(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Log.d("meta", "Drawing rectangle");
		Log.d("meta", "Width: " + this.getWidth() + "; height: " + this.getHeight());
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(0, 500, 500, 0, paint);
	}
	
	public void redraw(int left, int top, int width, int height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
		params.height = height;
		params.width = width;
		setLayoutParams(params);
		Log.d("meta", "Called redraw");
		invalidate();
	}
}