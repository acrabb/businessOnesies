package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MAScreenElement extends FrameLayout {

	protected MAScreen		mMAScreen;
	private ImageView		mDragTarget;
	private View			mHighlightOverlay;
	private int				mHighlightColor = getResources().getColor(R.color.highlightColor);
	
	private ElementType		mType;
	protected boolean 		isSelected = false;
	protected String		mText;
	private String			screenLinkedTo = null;
	
	private boolean			mWasDragged = false;
	private boolean			mResizing = false;
	private float 			mLastX = 0;
	private float 			mLastY = 0;
	private int 			mLastW = 0;
	private int				mLastH = 0;
	
	/****
	 * WRITE A GETTER FUNCTION TO GET MIN WIDTH / HEIGHT
	 * OVERWRITE GETTERS IN SUBCLASSES SO THAT EACH CAN RETURN A 
	 * UNIQUE VALUE.
	 * IE...SO THAT TEXT LABELS CAN CALCULATE W&H FOR ITSELF.
	 */
	private int 			MIN_WIDTH = 60;
	private int				MIN_HEIGHT = 60;
	private int				MAX_HEIGHT = Integer.MAX_VALUE;
	
	private RelativeLayout.LayoutParams prev;
	
	public MAScreenElement(Context context, MAScreen maScreen, ElementType type) {
		super(context);
		this.mMAScreen = maScreen;
		this.mType = type;
		
		LayoutInflater mInflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.ma_element, null);
		//----------__OTHER SETUP__-------------------
		this.setPivotX(0);
		this.setPivotY(0);
		
		mHighlightOverlay = new View(getContext());
//		mHighlightOverlay.setBackgroundResource(R.drawable.arrow_dr);
		mHighlightOverlay.setBackgroundColor(mHighlightColor);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		mHighlightOverlay.setLayoutParams(lp);
		mHighlightOverlay.invalidate();
		this.addView(mHighlightOverlay);
		mHighlightOverlay.setVisibility(INVISIBLE);
		
		// DO THIS BETTER
		// DO THIS BETTER
		// DO THIS BETTER
		// DO THIS BETTER
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
	public void onAttachedToWindow() {
		// Get mScreen
		// Set width and height?
		// get mResizeTarget
		// get mHighlightOverlay
			// The following will only work if we inflate from ma_element.xml,
			// and not create a UI element via a constructor. (In Development Activity.)
//		mHighlightOverlay = this.findViewById(R.id.highlightOverlay);
//		mHighlightOverlay.setVisibility(VISIBLE);
		
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		RelativeLayout.LayoutParams params;
		params = (RelativeLayout.LayoutParams) this.getLayoutParams();
		float x = event.getX();
		float y = event.getY();
		switch(event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				prev = new RelativeLayout.LayoutParams(params.width, params.height);
				prev.leftMargin = params.leftMargin;
				prev.topMargin = params.topMargin;
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
						params.width = Math.max(this.getMinWidth(), mLastW + horizDiff);
						params.height = Math.min(Math.max(this.getMinHeight(),
														mLastH + vertDiff),
												this.getMaxHeight());
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
				} else {
					mMAScreen.updateModel(this, UndoStatus.MOVE, prev);
				}
				if (mResizing) {
					mMAScreen.updateModel(this, UndoStatus.RESIZE, prev);
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
        mHighlightOverlay.setVisibility(VISIBLE);
		mDragTarget.setVisibility(VISIBLE);
        this.invalidate();
	}

	/**
	 * Called when an MAScreenElement is deselected. This method should perform general
	 * on-deselect actions for all ScreenElements. Subclasses should call super.deselect()
	 * when overriding.
	 */
	public void deselect() {
		isSelected = false;
        mHighlightOverlay.setVisibility(GONE);
		mDragTarget.setVisibility(GONE);
		this.invalidate();
	}
	
	/* Reverts the label of this MAScreenElement to its previous text label, prevText. */
	public void undoText(final String prevText) {
		this.setText(prevText);
		this.invalidate();
	}
	
	/* Reverts both MOVES and RESIZES by setting the LayoutParams to prevState. */
	public void undoState(final RelativeLayout.LayoutParams prevState) {
		this.setLayoutParams(prevState);
		this.invalidate();
	}
	
	/* Returns the LayoutParams of this MAScreenElement. */
	public RelativeLayout.LayoutParams getState() {
		return (RelativeLayout.LayoutParams) this.getLayoutParams();
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
	
	public String getText() {
		return this.mText;
	}
	
	public void setText(String lbl) {
		this.mText = lbl;
	}
}
