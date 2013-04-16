package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAButton extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private String		mLabel = "Button";	
	private DrawFilter mDrawFilter;
	private Paint paint;
	
	public MAButton(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.BUTTON);
		// Set background to be some image
		this.mIsLinkable = true;
		this.setBackgroundResource(R.drawable.btn_default_normal);
		paint = new Paint();
	}

	public MAButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		canvas.save();
//		canvas.scale(mScaleFactor, mScaleFactor);
		
		paint.setColor(Color.BLACK);
		paint.setTextSize(40); 
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(mLabel, this.getWidth()/2, this.getHeight()/2, paint);
		
//		canvas.restore();
	}
	
	@Override
	public void select() {
		super.select();
		this.getBackground().setColorFilter(getResources().getColor(R.color.blue),
				PorterDuff.Mode.DARKEN);
		isSelected = true;
	}
	@Override
	public void deselect() {
		super.deselect();
		this.getBackground().clearColorFilter();
	}
	
	
	//-----------------GETTERS AND SETTERS-----------------------------------
	public MAScreen getDestinationScreen() {
		return mDestinationScreen;
	}

	public void setDestinationScreen(MAScreen mDestinationScreen) {
		this.mDestinationScreen = mDestinationScreen;
	}
	
	public void setLabel(String lbl) {
		mLabel = lbl;
		
		this.invalidate();
	}
	
	public void setSize(int width, int height) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
		params.height = height;
		params.width = width;
		this.setLayoutParams(params);
	}
}
