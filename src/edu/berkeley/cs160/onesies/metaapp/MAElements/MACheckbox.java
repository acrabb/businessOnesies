package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MACheckbox extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private Paint       paint;
	
	private int MIN_WIDTH = 60;
	private int	MIN_HEIGHT = 40;
	private int	MAX_HEIGHT = MIN_HEIGHT;
	private int padding = 10;
	private int textSize = 20;
	private boolean isChecked = false;
	
	public MACheckbox(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.CHECKBOX);
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		paint.setDither(true);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(Color.BLACK);
		mText = "Checkbox";
	}

	public MACheckbox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onDraw(Canvas canvas) {
		float h = this.getHeight();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(h / 10);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(padding, padding, h - padding, h - padding, paint);
		paint.setStyle(Style.FILL);
		canvas.drawText(mText, padding+h, h / 2 + padding, paint);
		
		if (isChecked) {
			canvas.drawRect(padding*2, padding*2, h - padding*2, h - padding*2, paint);
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
