package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MATextLabel extends MAScreenElement {

//	private String		mText = "TextLabel";	
	private Paint 			paint;
	// MATextLabels emphasize width.
	private int				tolerable_padding = 1;
	
	public MATextLabel(Context context, MAScreen maScreen, String text) {
		super(context, maScreen, ElementType.TEXT_LABEL);
		paint = new Paint();
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		mText = text;
		
		MIN_WIDTH = 150;
		MAX_WIDTH = 900;
		MIN_HEIGHT = 60;
		MAX_HEIGHT = 300;
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
		
		float h = paint.getTextSize();
		while (paint.measureText(mText) > this.getWidth()) paint.setTextSize(h--);
		while (paint.measureText(mText) < (this.getWidth() - tolerable_padding)) paint.setTextSize(h++);
		
		this.MIN_HEIGHT = this.MAX_HEIGHT = (int) h;
		enforceDimensionConstraints();
//		canvas.drawText(mText, this.getWidth()/2, this.getHeight(), paint);
		canvas.drawText(mText, this.getWidth()/2, (int)(h*.75), paint);
	}

	//-------------------------------------------------------------------------
}
