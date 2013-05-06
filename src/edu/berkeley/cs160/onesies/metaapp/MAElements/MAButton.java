package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAButton extends MAScreenElement {

	private Rect textBounds;
	
	private ImageView	mLinkBadge;
	
	private Paint paint;
	
	public MAButton(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.CHECKBOX);
		
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));
		paint = new Paint();
		mText = "Button";
		mType = ElementType.BUTTON;
		textBounds = new Rect();
	}

	//-------------------------------------------------------------------------
	public MAButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	//-------------------------------------------------------------------------
	@Override
	public void onFinishInflate() {
		// Call super
		super.onFinishInflate();
		Log.i("BUBUBU", "BUTTON CREATED");
		
		// Set background to be some image
		setBackgroundColor(getResources().getColor(R.color.clearColor));

		// Set up paint, text, type, and link badge.
		// mScreen should get assigned by devAct.
		paint = new Paint();
		mText = "Button";
		mType = ElementType.BUTTON;
		textBounds = new Rect();
		
		mLinkBadge = (ImageView) findViewById(R.id.linkBadge);
		mLinkBadge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	//-------------------------------------------------------------------------
	@Override
	public void onDraw(Canvas canvas) {
		paint.setTextAlign(Paint.Align.CENTER);
		
		paint.setColor(getResources().getColor(R.color.uiGray));
		paint.setStyle(Style.FILL);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
		
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(10);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

		paint.setStyle(Style.FILL);
		paint.setTextSize(Math.min(150, this.getHeight()/3)); 
		paint.setColor(Color.BLACK);
		paint.getTextBounds(mText, 0, mText.length(), textBounds);
		canvas.drawText(mText, this.getWidth()/2, this.getHeight()/2 + Math.abs(textBounds.top - textBounds.bottom) / 2, paint);
	}
	
	
	//-------------------------------------------------------------------------
	@Override
	public void select() {
		super.select();
		if (mDestinationScreen != null && mLinkBadge != null) {
			mLinkBadge.setVisibility(VISIBLE);
		}
	}
	//-------------------------------------------------------------------------
	@Override
	public void deselect() {
		super.deselect();
		mLinkBadge.setVisibility(INVISIBLE);
	}
	
	
	//-----------------GETTERS AND SETTERS-----------------------------------
	public void setText(String lbl) {
		mText = lbl;
		
		this.invalidate();
	}
	
	//-------------------------------------------------------------------------
	public void setSize(int width, int height) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams(); 
		params.height = height;
		params.width = width;
		this.setLayoutParams(params);
	}
}
