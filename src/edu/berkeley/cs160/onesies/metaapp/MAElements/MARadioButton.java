package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MARadioButton extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private Paint       paint;
	

	// LOL HACKACKACK
	private int min_min_height = 30;
	private int max_max_height = 50;
	private int max_max_width = 1000;
	private int min_min_width = 0;
	
	private int tolerable_padding = 1;
	private int padding;
	private RectF mRectF;
	private boolean isChecked;
	
	public MARadioButton(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.CHECKBOX);
	
		MIN_WIDTH = 0;
		MAX_WIDTH = 1000;
		MIN_HEIGHT = 80;
		MAX_HEIGHT = 80;

		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		paint.setDither(true);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(Color.BLACK);
		mText = "Radio";
		// set right and bottom in onDraw
		mRectF = new RectF(padding, padding, 0, 0);
	}

	public MARadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		float h = Math.min(max_max_height, this.getHeight());
		padding = (int) (h / 10);
		mRectF.left = padding;
		mRectF.top = padding;
		mRectF.right = h + 2*padding;
		mRectF.bottom = h + 2*padding;
		float radius = h / 2 - padding;

		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(h / 10);
		paint.setStyle(Style.STROKE);
		canvas.drawCircle(padding + radius, h / 2, radius, paint);

		float textSize = 0;

		/*
		 * decrease font size while 
		 * 		the total text width is greater than the width of the element 
		 * 			or 
		 * 		the text size is greater than the maximum allowed height
		 * 
		 * 		but stop if the font size goes below min_min_height
		 */
		while ((paint.measureText(mText) < (this.getWidth() - h - padding - tolerable_padding) || textSize < min_min_height)
				&& textSize < max_max_height)
			paint.setTextSize(textSize++);
		if (paint.measureText(mText) > (this.getWidth() - h - padding - tolerable_padding)) {
			if (this.getLayoutParams() != null) {
				this.getLayoutParams().width = (int) (h + padding + tolerable_padding + paint.measureText(mText));
				this.setLayoutParams(this.getLayoutParams());
			}
		}

		paint.setStyle(Style.FILL);
		canvas.drawText(mText, padding + h, h - padding, paint);

		MIN_HEIGHT = MAX_HEIGHT = (int) textSize;
		if (MAX_HEIGHT >= max_max_height)
			MAX_WIDTH = this.getWidth();

		if (MIN_HEIGHT <= min_min_height)
			MIN_WIDTH = this.getWidth();

		if (isChecked) {
			canvas.drawCircle(padding + radius, h/2, (float) (radius * 0.7), paint);
		}
		paint.setColor(getResources().getColor(R.color.uiGray));
		paint.setStrokeWidth(0);
		canvas.drawCircle(padding + radius, h / 2, radius, paint);

	}
	
	//-----------------GETTERS AND SETTERS-----------------------------------
	
	public void setText(String lbl) {
		this.mText = lbl;
		MAX_WIDTH = max_max_width;
		MIN_WIDTH = min_min_width;
	}
	
	public MAScreen getDestinationScreen() {
		return mDestinationScreen;
	}

	public void setDestinationScreen(MAScreen mDestinationScreen) {
		this.mDestinationScreen = mDestinationScreen;
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
