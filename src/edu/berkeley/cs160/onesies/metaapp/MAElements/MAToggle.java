package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAToggle extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private Paint       paint;
	
	private int MIN_WIDTH = 60;
	private int	MIN_HEIGHT = 70;
	private int	MAX_HEIGHT = MIN_HEIGHT;
	private int padding = 10;
	private int textSize = 20;
	private boolean toggleOn = false;
	private Rect textBounds;
	private String on = "On";
	private String off = "Off";
	
	public MAToggle(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.CHECKBOX);
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		paint.setDither(true);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(Color.BLACK);
		mText = "Toggle";
		textBounds = new Rect();
		// set right and bottom in onDraw
	}

	public MAToggle(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		float h = this.getHeight();
		float w = this.getWidth();
		float toggleHeight = h - 2*padding;
		float toggleWidth  = w - 2*padding;

		float l = padding;
		float r = l + toggleWidth;
		float t = padding;
		float b = t + toggleHeight;
		
		paint.setStyle(Style.FILL);
		paint.setColor(getResources().getColor(R.color.uiGray));
		canvas.drawRect(l, t, r, b, paint);

		paint.setStrokeWidth(10);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(l, t, r, b, paint);
		
		paint.setColor(Color.BLACK);
		if (toggleOn) {
			canvas.drawRect(l + (toggleWidth / 2), t, r, b, paint);
			paint.setStyle(Style.FILL);
			paint.setColor(getResources().getColor(R.color.uiDarkerGray));
			canvas.drawRect(l + (toggleWidth / 2), t, r, b, paint);
		} else {
			canvas.drawRect(l, t, l + (toggleWidth / 2), b, paint);
			paint.setStyle(Style.FILL);
			paint.setColor(getResources().getColor(R.color.uiDarkerGray));
			canvas.drawRect(l, t, l + (toggleWidth / 2), b, paint);
		}
		
		paint.setColor(Color.BLACK);
		paint.getTextBounds(off, 0, off.length(), textBounds);
		canvas.drawText(off, l + (toggleWidth / 4), h / 2 - (textBounds.top - textBounds.bottom)/2, paint);
		
		paint.getTextBounds(on, 0, on.length(), textBounds);
		canvas.drawText(on, r - (toggleWidth / 4), h / 2 - (textBounds.top - textBounds.bottom)/2, paint);
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
