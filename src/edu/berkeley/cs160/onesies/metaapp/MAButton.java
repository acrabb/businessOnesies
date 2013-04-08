package edu.berkeley.cs160.onesies.metaapp;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MAButton extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private String		mLabel = "Button";	
	
	public MAButton(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.BUTTON);
		// TODO Auto-generated constructor stub
		// Set background to be some image.
		this.mIsLinkable = true;
		this.setBackgroundResource(R.drawable.btn_default_normal);
	}

	public MAButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	DrawFilter mDrawFilter;
	
	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();	
		
		paint.setColor(Color.BLACK);
		paint.setTextSize(40); 
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		/**/
		if(isSelected){
            this.getBackground().setColorFilter(getResources().getColor(R.color.blue),
            		PorterDuff.Mode.SRC_ATOP);
//			paint.setColorFilter(
//					new PorterDuffColorFilter(getResources().getColor(R.color.highlightColor),
//							PorterDuff.Mode.DST_ATOP));
			
//			canvas.drawColor(getResources().getColor(R.color.highlightColor), PorterDuff.Mode.MULTIPLY);
//			canvas.setDrawFilter(mDrawFilter);
		} else {
			
		}
		/**/
		
		canvas.drawText(mLabel, this.getWidth()/2, this.getHeight()/2, paint);
		
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
	

}
