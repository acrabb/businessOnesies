package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.graphics.PorterDuff;

public class MAScreenElement extends View {

	protected MAScreen		mMAScreen;
	private boolean			mWasDragged = false;
	private ElementType		mType;
	protected boolean		mIsLinkable = false;
	private boolean 		isSelected = false;
	
	private float 			dx = 0;
	private float 			dy = 0;
	private String			screenLinkedTo = null;
	public MAScreenElement(Context context, MAScreen maScreen, ElementType type) {
		super(context);
		this.mMAScreen = maScreen;
		this.mType = type;
	}
	
	public MAScreenElement(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MAScreenElement(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MAScreenElement(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		RelativeLayout.LayoutParams params;
		float x = event.getX();
		float y = event.getY();
		float distance = MainActivity.distance(x, y, dx, dy);
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				dx = x;
				dy = y;
				Log.i("ACACAC", String.format(" > DOWN > dx: %f, dy: %f", dx, dy));
				break;
			case MotionEvent.ACTION_MOVE:
				if (!mMAScreen.getTestMode()) {
					Log.i("ACACAC", String.format(" > MOVE > x: %f, y: %f", x, y));
					mWasDragged = true;
					params = (RelativeLayout.LayoutParams) this.getLayoutParams();
					params.leftMargin = (int) (params.leftMargin + (x-dx));
					params.topMargin = (int) (params.topMargin + (y-dy));
					this.setLayoutParams(params);
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.i("ACACAC", String.format("> UP >dx: %f, dy: %f", dx, dy));
				Log.i("ACACAC", String.format("Distance calced: %f", distance));
				Log.i("ACACAC", String.format("Was Dragged %b", mWasDragged));
				if (!mWasDragged) {
					// Element tapped!
					onTap();
				}
				mWasDragged = false;
				break;
			default:
				break;
		}
		mMAScreen.invalidate();
		return true;
	}
	
	private void onTap() {
		if (mMAScreen.getTestMode()) {
			if (screenLinkedTo != null) {
				mMAScreen.getmTestingActivity().showScreenWithName(screenLinkedTo);
			}
		} else {
			mMAScreen.onElementTapped(this);
		}
	}
	
	public void select() {
        isSelected = true;
        this.getBackground().setColorFilter(getResources().getColor(R.color.blue),
        		PorterDuff.Mode.SRC_ATOP);
	}
	
	public void deselect() {
		isSelected = false;
		this.getBackground().clearColorFilter();
	}
	
	public void setScreenLinkedTo(final String screenName) {
		this.screenLinkedTo = screenName;
	}
	
	public String getScreenLinkedTo() {
		return screenLinkedTo;
	}
	
	public void makeToast(String format, Object... args) {
		Toast.makeText(getContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}

	public ElementType getmType() {
		return mType;
	}

	public void setmType(ElementType mType) {
		this.mType = mType;
	}
}
