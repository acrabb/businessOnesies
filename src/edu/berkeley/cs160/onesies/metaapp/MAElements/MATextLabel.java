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
	private Paint 			paint;
	// MATextLabels emphasize width.
	private int 			MIN_WIDTH = 150;
	private int 			MAX_WIDTH = 900;
	private int				MIN_HEIGHT = 60;
	private int				MAX_HEIGHT = 300;
	
	public MATextLabel(Context context, MAScreen maScreen, String text) {
		super(context, maScreen, ElementType.TEXT_LABEL);
		paint = new Paint();
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		mText = text;
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
		paint.setColor(Color.BLACK);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		int h = 0;
		do {
			paint.setTextSize(++h); //Math.min(MAX_HEIGHT, this.getHeight())); 
		} while (paint.measureText(mText) < this.getWidth());
		this.MIN_HEIGHT = this.MAX_HEIGHT = h;
//		canvas.drawText(mText, this.getWidth()/2, this.getHeight(), paint);
		canvas.drawText(mText, this.getWidth()/2, (int)(h*.75), paint);
	}

	public int getMinWidth() {
		return this.MIN_WIDTH;
	}
	public int getMaxWidth() {
		return this.MAX_WIDTH;
	}
	public int getMinHeight() {
		return this.MIN_HEIGHT;
	}
	public int getMaxHeight() {
		return this.MAX_HEIGHT;
	}
}
