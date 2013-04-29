package edu.berkeley.cs160.onesies.metaapp.MAShapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAOval extends MAScreenElement {
	
	private int left, top, width, height;
	private Paint paint;
	private RectF ovalRect;
	private int padding = 10;
	
	public MAOval(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.SHAPE);
		// Set background to be some image
		paint = new Paint();
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		ovalRect = new RectF(padding,padding,this.width-padding,this.height-padding);
	}

	public MAOval(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttachedToWindow() {
		this.height = 200;
		this.width = 200;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
		params.height = 200;
		params.width = 200;
		setLayoutParams(params);
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(6);
		this.width = getWidth();
		this.height = getHeight();
		ovalRect.right = this.width - padding;
		ovalRect.bottom = this.height - padding;
		canvas.drawOval(ovalRect, paint);
	}
	
}
