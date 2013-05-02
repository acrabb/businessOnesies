package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAToggle extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private Paint       paint;
	
	private int MIN_WIDTH = 60;
	private int	MIN_HEIGHT = 40;
	private int	MAX_HEIGHT = MIN_HEIGHT;
	private int padding = 10;
	private int textSize = 20;
	private int minToggleWidth = 5;
	private RectF mRectF;
	private boolean toggleOn = false;
	
	public MAToggle(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.CHECKBOX);
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		paint.setDither(true);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(Color.BLACK);
		mText = "Toggle";
		// set right and bottom in onDraw
		mRectF = new RectF(padding, padding, 0, 0);
	}

	public MAToggle(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		float h = this.getHeight();
		float toggleWidth = (Math.max(minToggleWidth, Math.min(this.getWidth(), 2*h)));
		float l = padding;
		float t = padding;
		float r = l + toggleWidth;
		float b = h - padding;

		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(h / 10);

		paint.setStyle(Style.STROKE);
		canvas.drawRect(l, t, r, b, paint);

		if (toggleOn) {
			paint.setStrokeWidth(0);
			paint.setStyle(Style.FILL);
			paint.setColor(Color.BLACK);
			canvas.drawRect(l, t, l + (toggleWidth / 2), b, paint);

			paint.setStyle(Style.FILL);
			paint.setColor(getResources().getColor(R.color.uiGray));
			canvas.drawRect(l + (toggleWidth / 2), t, l + toggleWidth, b, paint);
		} else {
			paint.setStrokeWidth(0);
			paint.setStyle(Style.FILL);
			paint.setColor(getResources().getColor(R.color.uiGray));
			canvas.drawRect(l, t, l + (toggleWidth / 2), b, paint);

			paint.setStyle(Style.FILL);
			paint.setColor(Color.BLACK);
			canvas.drawRect(l + (toggleWidth / 2), t, l + toggleWidth, b, paint);
		}
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
