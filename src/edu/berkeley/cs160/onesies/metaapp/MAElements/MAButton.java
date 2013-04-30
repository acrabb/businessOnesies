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
	
	public MAButton(Context context, MAScreen maScreen) {
		super(context, maScreen, ElementType.BUTTON);
		// Set background to be some image
		this.setBackgroundResource(R.drawable.btn_default_normal);
		paint = new Paint();
		mScreen = maScreen;
		
		mText = "Button";
		
		// Set up Link Badge
		mLinkBadge = new ImageView(getContext());
		mLinkBadge.setBackgroundColor(getResources().getColor(R.color.highlightColor));
		mLinkBadge.setImageDrawable(getResources().getDrawable(R.drawable.link_default));
		mLinkBadge.setScaleType(ScaleType.FIT_CENTER);
		FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(40, 40);
		p.gravity = Gravity.TOP | Gravity.LEFT;
		mLinkBadge.setLayoutParams(p);
		mLinkBadge.invalidate();
		this.addView(mLinkBadge);
		mLinkBadge.setVisibility(INVISIBLE);
		mLinkBadge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
	}

	public MAButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onAttachedToWindow() {
		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(40); 
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(mText, this.getWidth()/2, this.getHeight()/2, paint);
	}
	
	
	@Override
	public void select() {
		super.select();
		if (mDestinationScreen != null) {
			mLinkBadge.setVisibility(VISIBLE);
		}
	}
	@Override
	public void deselect() {
		super.deselect();
		mLinkBadge.setVisibility(INVISIBLE);
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
}
