package edu.berkeley.cs160.onesies.metaapp.MAElements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import edu.berkeley.cs160.onesies.metaapp.ElementType;
import edu.berkeley.cs160.onesies.metaapp.MAScreen;
import edu.berkeley.cs160.onesies.metaapp.MAScreenElement;
import edu.berkeley.cs160.onesies.metaapp.R;

public class MAButton extends MAScreenElement {

	private MAScreen	mDestinationScreen;
	private DrawFilter	mDrawFilter;
	private MAScreen	mScreen;
	
	private ImageView	mLinkBadge;
	
	private Paint paint;

	
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
		
		// Set up paint, text, type, and link badge.
		// mScreen should get assigned by devAct.
		paint = new Paint();
		mText = "Button";
		mType = ElementType.BUTTON;
		
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
		paint.setColor(Color.BLACK);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		paint.setTextSize(Math.min(150, this.getHeight()/3)); 
		canvas.drawText(mText, this.getWidth()/2, this.getHeight()/2, paint);
	}
	
	
	//-------------------------------------------------------------------------
	@Override
	public void select() {
		super.select();
		if (mDestinationScreen != null) {
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
	public MAScreen getDestinationScreen() {
		return mDestinationScreen;
	}

	//-------------------------------------------------------------------------
	public void setDestinationScreen(MAScreen mDestinationScreen) {
		this.mDestinationScreen = mDestinationScreen;
	}
	
	//-------------------------------------------------------------------------
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
