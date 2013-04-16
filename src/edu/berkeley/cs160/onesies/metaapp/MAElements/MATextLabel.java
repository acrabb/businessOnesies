package edu.berkeley.cs160.onesies.metaapp.MAElements;

import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;
import edu.berkeley.cs160.onesies.metaapp.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MATextLabel extends MAScreenElement {

//	private String		mLabel = "TextLabel";	
	
	public MATextLabel(Context context, MAScreen maScreen, String text) {
		super(context, maScreen, ElementType.TEXT_LABEL);
		// TODO Auto-generated constructor stub
		this.mIsLinkable = false;
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		mLabel = text;
//		TextView mTextView = new TextView(context);
//		mTextView.setTextColor(getResources().getColor(R.color.black));
//		mTextView.setTextSize(30);
//		mTextView.setText("HELLO");
//		this.addView(mTextView);
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
		/**/
		if(isSelected){
			this.setBackgroundColor(getResources().getColor(R.color.highlightColor));			
		} else {
			this.setBackgroundColor(getResources().getColor(R.color.clearColor));			
		}
		/**/
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(40); 
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		
		canvas.drawText(mLabel, this.getWidth()/2, this.getHeight()/2, paint);
	}

	@Override
	public void select() {
		super.select();
//		makeToast("MATextlabel select");
	}
	@Override
	public void deselect() {
		super.deselect();
//		makeToast("MATextlabel deselect");
	}
}
