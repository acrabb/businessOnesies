package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;

public class MAScreenElement extends FrameLayout {

	protected MAScreen		mMAScreen;
	private boolean			mWasDragged = false;
	private boolean			mResizing = false;
	private ElementType		mType;
	protected boolean		mIsLinkable = false;
	protected boolean 		isSelected = false;
	protected String		mLabel;
	
	private ImageView		mDragTarget;
//	private ScaleGestureDetector mScaleDetector;
//	protected float 			mScaleFactor = 1.f;
	
	private float 			mLastX = 0;
	private float 			mLastY = 0;
	private String			screenLinkedTo = null;
	
	
	public MAScreenElement(Context context, MAScreen maScreen, ElementType type) {
		super(context);
		this.mMAScreen = maScreen;
		this.mType = type;
		
		
		//----------__OTHER SETUP__-------------------
		this.setPivotX(0);
		this.setPivotY(0);
		//Set up Badge
		mDragTarget = new ImageView(getContext());
		mDragTarget.setBackgroundResource(R.drawable.arrow_dr);
		mDragTarget.setScaleType(ScaleType.FIT_CENTER);
		FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(40, 40);
		p.gravity = Gravity.BOTTOM | Gravity.RIGHT;
		mDragTarget.setLayoutParams(p);
		mDragTarget.invalidate();
		this.addView(mDragTarget);
		mDragTarget.setVisibility(INVISIBLE);
		mDragTarget.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
						mResizing = true;
						break;
					case MotionEvent.ACTION_UP:
						mResizing = false;
						makeToast("ARROW UP");
						break;
					default:
						
				}
				return false;
			}
		});
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
//		float distance = MainActivity.distance(x, y, mLastX, mLastY);
		switch(event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mLastX = x;
				mLastY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				if (!mMAScreen.getTestMode()) {
					params = (RelativeLayout.LayoutParams) this.getLayoutParams();
					mWasDragged = true;
					if (mResizing){
						this.setScaleX(x/mLastX);
						this.setScaleY(y/mLastY);
//						params.width = (int) (params.width + Math.pow((x-mLastX), 3/4));
//						params.height = (int) (params.height + Math.pow((y-mLastY), 3/4));
					} else {
						params.leftMargin = (params.leftMargin + (int)(x-mLastX));
						params.topMargin = (params.topMargin + (int)(y-mLastY));
					}
					this.setLayoutParams(params);
				}
				break;
			case MotionEvent.ACTION_UP:
				if (!mWasDragged) {
					// Element tapped!
					onTap();
				}
				mResizing = false;
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
	
	/**
	 * Called when an MAScreenElement is selected. This method should perform general
	 * on-select actions for all ScreenElements. Subclasses should call super.select()
	 * when overriding.
	 */
	public void select() {
        isSelected = true;
        showBadge();
        this.invalidate();
        // Its up to the subclasses to highlight themselves when selected.
        //        this.getBackground().setColorFilter(getResources().getColor(R.color.blue),
        //        		PorterDuff.Mode.DST_OVER);
	}

	/**
	 * Called when an MAScreenElement is deselected. This method should perform general
	 * on-deselect actions for all ScreenElements. Subclasses should call super.deselect()
	 * when overriding.
	 */
	public void deselect() {
		isSelected = false;
		hideBadge();
//		this.onDeselect();
		this.invalidate();
	}
	
	private void showBadge() {
		mDragTarget.setVisibility(VISIBLE);
	}
	
	private void hideBadge() {
		mDragTarget.setVisibility(INVISIBLE);
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
	
	public void setLabel(String lbl) {
		this.mLabel = lbl;
	}
}
