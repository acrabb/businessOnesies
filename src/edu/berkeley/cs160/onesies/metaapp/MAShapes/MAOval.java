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
	
	public MAOval(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.SHAPE);
		// Set background to be some image
		paint = new Paint();
		setBackgroundColor(getResources().getColor(R.color.clearColor));
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
		ovalRect = new RectF(0,0,this.width,this.height);
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		ovalRect.right = this.width - 3;
		ovalRect.bottom = this.height - 3;
		canvas.drawOval(ovalRect, paint);
	}
	
}