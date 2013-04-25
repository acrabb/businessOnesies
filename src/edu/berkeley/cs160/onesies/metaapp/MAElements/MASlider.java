package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MASlider extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private DrawFilter mDrawFilter;
	private Paint      paint;
	
	private int 			MIN_WIDTH = 60;
	private int				MIN_HEIGHT = 40;
	private int				MAX_HEIGHT = MIN_HEIGHT;
	
	
	public MASlider(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.BUTTON);
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		paint.setDither(true);
		paint.setColor(Color.BLACK);

	}

	public MASlider(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		canvas.save();
//		canvas.scale(mScaleFactor, mScaleFactor);
		// lol arbitrary hard coded values
		float xleft = 0,
			  xright = this.getWidth(),
			  xcenter = (xright - xleft)/2,
			  ytop = 0,
			  ybottom = this.getHeight(),
			  ycenter = (ybottom - ytop)/2;
		
		paint.setColor(getResources().getColor(R.color.achalRed));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);

		// slider line
		canvas.drawLine(xleft,ycenter,xcenter,ycenter,paint);

		// slider unfilled line
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GRAY);
		canvas.drawLine(xcenter,ycenter,xright,ycenter,paint);
		
		// slider active point (thumb)
		paint.setColor(getResources().getColor(R.color.achalRed));
		paint.setStrokeWidth(2);
		canvas.drawCircle(xcenter, ycenter, 15, paint);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(xcenter, ycenter, 10, paint);
	}
	
//	@Override
//	public void select() {
//		super.select();
//		isSelected = true;
//	}
//	@Override
//	public void deselect() {
//		super.deselect();
//	}
	
	
	//-----------------GETTERS AND SETTERS-----------------------------------
	public MAScreen getDestinationScreen() {
		return mDestinationScreen;
	}

	public void setDestinationScreen(MAScreen mDestinationScreen) {
		this.mDestinationScreen = mDestinationScreen;
	}
	
	public void setText(String lbl) {
		mText = lbl;
		
		this.invalidate();
	}
	
	public void setSize(int width, int height) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
		params.height = height;
		params.width = width;
		this.setLayoutParams(params);
	}
	
	
	public int getMinWidth() {
		return this.MIN_WIDTH;
	}
	public int getMinHeight() {
		return this.MIN_HEIGHT;
	}
	public int getMaxHeight() {
		return this.MAX_HEIGHT;
	}
	
}
