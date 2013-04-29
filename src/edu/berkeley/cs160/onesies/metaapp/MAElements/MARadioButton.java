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
	
	private int MIN_WIDTH = 60;
	private int	MIN_HEIGHT = 40;
	private int	MAX_HEIGHT = MIN_HEIGHT;
	private int padding = 10;
	private int textSize = 20;
	private RectF mRectF;
	private boolean isChecked;
	
	public MARadioButton(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.CHECKBOX);
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		paint.setDither(true);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(Color.BLACK);
		mText = "Radio button";
		// set right and bottom in onDraw
		mRectF = new RectF(padding, padding, 0, 0);
	}

	public MARadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		float h = this.getHeight();
		mRectF.right = h + 2*padding;
		mRectF.bottom = h + 2*padding;
		float radius = h / 2 - padding;

		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(h / 10);
		paint.setStyle(Style.STROKE);
		canvas.drawCircle(padding + radius, h / 2, radius, paint);
		paint.setStyle(Style.FILL);
		canvas.drawText(mText, h + padding, h / 2 + padding, paint);

		if (isChecked) {
			canvas.drawCircle(padding + radius, h/2, (float) (radius * 0.7), paint);
		}
		paint.setColor(getResources().getColor(R.color.uiGray));
		paint.setStrokeWidth(0);
		canvas.drawCircle(padding + radius, h / 2, radius, paint);

	}
	
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
