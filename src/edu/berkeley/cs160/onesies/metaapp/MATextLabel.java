package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MATextLabel extends MAScreenElement {

//	private String		mLabel = "TextLabel";	
	
	public MATextLabel(Context context, MAScreen maScreen, String text) {
		super(context, maScreen, ElementType.TEXT_LABEL);
		// TODO Auto-generated constructor stub
		this.mIsLinkable = false;
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		mLabel = text;
	}

	public MATextLabel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MATextLabel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MATextLabel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(40); 
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		/**/
		if(isSelected){
            this.setBackgroundColor(getResources().getColor(R.color.highlightColor));			
//			paint.setColorFilter(
//					new PorterDuffColorFilter(getResources().getColor(R.color.highlightColor),
//							PorterDuff.Mode.DST_ATOP));
			
//			canvas.drawColor(getResources().getColor(R.color.highlightColor), PorterDuff.Mode.MULTIPLY);
//			canvas.setDrawFilter(mDrawFilter);
		} else {
            this.setBackgroundColor(getResources().getColor(R.color.clearColor));			
			
		}
		/**/
		
		canvas.drawText(mLabel, this.getWidth()/2, this.getHeight()/2, paint);
	}

}
