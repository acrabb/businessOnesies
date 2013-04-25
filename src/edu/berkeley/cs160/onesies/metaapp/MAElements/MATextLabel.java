package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MATextLabel extends MAScreenElement {

//	private String		mText = "TextLabel";	
	private Paint paint;
	private int				textSize = 40;
	private int 			MIN_WIDTH = 60;
	private int				MIN_HEIGHT = 60;
	private int				MAX_HEIGHT = MIN_HEIGHT;
	
	public MATextLabel(Context context, MAScreen maScreen, String text) {
		super(context, maScreen, ElementType.TEXT_LABEL);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(textSize); 
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		// TODO Auto-generated constructor stub
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		mText = text;
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
		canvas.drawText(mText, this.getWidth()/2, this.getHeight()-10, paint);
	}

//	@Override
//	public void select() {
//		super.select();
////		makeToast("MATextlabel select");
//	}
//	@Override
//	public void deselect() {
//		super.deselect();
////		makeToast("MATextlabel deselect");
//	}
	
	
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
