package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MAScreenElement extends FrameLayout {

	protected MAScreen		mMAScreen;
	private ImageView		mDragTarget;
	
	private ElementType		mType;
	protected boolean 		isSelected = false;
	protected String		mLabel;
	private String			screenLinkedTo = null;
	
	private boolean			mWasDragged = false;
	private boolean			mResizing = false;
	private float 			mLastX = 0;
	private float 			mLastY = 0;
	private int 			mLastW = 0;
	private int				mLastH = 0;
	
	private int 			MIN_WIDTH = 60;
	private int				MIN_HEIGHT = 60;
	
	
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
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						mResizing = true;
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						mResizing = false;
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
		params = (RelativeLayout.LayoutParams) this.getLayoutParams();
		float x = event.getX();
		float y = event.getY();
		switch(event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mLastX = x;
				mLastY = y;
				mLastW = params.width;
				mLastH = params.height;
				break;
			case MotionEvent.ACTION_MOVE:
				if (!mMAScreen.isTesting()) {
					mWasDragged = true;
					int horizDiff = (int)(x-mLastX);
					int vertDiff = (int)(y-mLastY);
					if (mResizing) {
						params.width = Math.max(MIN_WIDTH, mLastW + horizDiff);
						params.height = Math.max(MIN_HEIGHT, mLastH + vertDiff);
					} else {
						params.leftMargin += horizDiff;
						params.topMargin += vertDiff;
					}
					this.setLayoutParams(params);
				}
				break;
			case MotionEvent.ACTION_UP:
				if (!mWasDragged) {
					// Element tapped!
					elementWasTapped();
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
	
	private void elementWasTapped() {
		if (mMAScreen.isTesting()) {
			mMAScreen.onElementTappedInTest(this);
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
