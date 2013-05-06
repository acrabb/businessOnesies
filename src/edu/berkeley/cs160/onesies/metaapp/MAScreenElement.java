package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MAScreenElement extends FrameLayout {

	protected MAScreen		mMAScreen;
	protected MAScreen		mDestinationScreen;
//	private ImageView		mDragTarget;
	private View			mHighlightOverlay;
	private int				mHighlightColor = getResources().getColor(R.color.highlightColor);
	
	protected ElementType	mType;
	protected boolean 		isSelected = false;
	protected String		mText;
	private String			screenLinkedTo = null;
	
//	private boolean			mWasDragged = false;
//	private boolean			mResizing = false;
	static final int 		NONE = 0;
    static final int 		DRAG = 1;
    static final int 		RESIZE = 2;
    static final int 		PINCH = 3;
    int						mMode = NONE;
    static final int		MIN_DRAG_DIST = 10;
	
	private float 			mLastX = 0;
	private float 			mLastY = 0;
	private int 			mLastW = 0;
	private int				mLastH = 0;
	private int 			mLastTop = 0;
	private int				mLastLeft = 0;
	
	private int				INVALID_POINTER_ID = -1;
	private int 			mActivePointerId = INVALID_POINTER_ID;
	
	/****
	 * WRITE A GETTER FUNCTION TO GET MIN WIDTH / HEIGHT
	 * OVERWRITE GETTERS IN SUBCLASSES SO THAT EACH CAN RETURN A 
	 * UNIQUE VALUE.
	 * IE...SO THAT TEXT LABELS CAN CALCULATE W&H FOR ITSELF.
	 */
	protected int 			MIN_WIDTH = 60;
	protected int 			MAX_WIDTH = Integer.MAX_VALUE;
	protected int			MIN_HEIGHT = 60;
	protected int			MAX_HEIGHT = Integer.MAX_VALUE;
	
	private RelativeLayout.LayoutParams prev;
	
	//-------------------------------------------------------------------------
	/**/
	public MAScreenElement(Context context, MAScreen maScreen, ElementType type) {
		super(context);
		this.mMAScreen = maScreen;
		this.mType = type;
		
		LayoutInflater mInflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.e_default, null);
		//----------__OTHER SETUP__-------------------
//		this.setPivotX(0);
//		this.setPivotY(0);
		
		// THIS SHOULD ALL MOVE TO ONFINISHINFLATE() LATER
		// Currently done for:
			// MAScreenElements
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
		//Set up Drag Badge
//		mDragTarget = new ImageView(getContext());
//		mDragTarget.setBackgroundResource(R.drawable.arrow_dr);
//		mDragTarget.setScaleType(ScaleType.FIT_CENTER);
//		FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(40, 40);
//		p.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//		mDragTarget.setLayoutParams(p);
//		mDragTarget.invalidate();
//		this.addView(mDragTarget);
//		mDragTarget.setVisibility(INVISIBLE);
//		mDragTarget.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch(event.getAction()) {
//					case MotionEvent.ACTION_DOWN:
////						mResizing = true;
//						mMode = RESIZE;
//						break;
//					case MotionEvent.ACTION_MOVE:
//						break;
//					case MotionEvent.ACTION_UP:
////						mResizing = false;
//						mMode = NONE;
//						break;
//					default:
//				}
//				return false;
//			}
//		});
		
		
		
		//////
	}
	/**/
	
	//-------------------------------------------------------------------------
	// THESE SHOULD BE FILLED OUT WITH A 'SETUP' FUNCTION IF WE CHANGE TO USING
	// LAYOUT INFLATION INSTEAD OF INSTANTIATION FOR SCREEN ELEMENTS.
	public MAScreenElement(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MAScreenElement(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i("MAMAMA", "MASE inflated");
		// TODO Auto-generated constructor stub
	}

	public MAScreenElement(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	//-------------------------------------------------------------------------
	/*
	 * Make all connections for the element such as touch listeners.
	 * 
	 * (non-Javadoc)
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	public void onFinishInflate() {
		// The following will only work if we inflate from ma_element.xml,
		// and not create a UI element via a constructor. (In Development Activity.)
		
		// Get mScreen (?)
			// I don't think we can. I think this needs to be assigned by devAct.
		// Set width and height?
			// No. This should be a property of the xml file we inflate from.
		
		// get mHighlightOverlay
		mHighlightOverlay = findViewById(R.id.highlightOverlay);
		invalidate();
	}

	//-------------------------------------------------------------------------
	float touchDistX = 0;
	float touchDistY = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		RelativeLayout.LayoutParams params;
		params = (RelativeLayout.LayoutParams) this.getLayoutParams();
		float x;
		float y;
		switch(event.getActionMasked()) {
			//-----------------------------------------------------------------
			case MotionEvent.ACTION_DOWN:
				mActivePointerId = event.getPointerId(0);
				prev = new RelativeLayout.LayoutParams(params.width, params.height);
				prev.leftMargin = params.leftMargin;
				prev.topMargin = params.topMargin;
				mLastX = event.getX();
				mLastY = event.getY();
				break;
			//-----------------------------------------------------------------
			case MotionEvent.ACTION_POINTER_DOWN:
				mMode = PINCH;
				mLastW = params.width;
				mLastH = params.height;
				mLastLeft = params.leftMargin;
				mLastTop = params.topMargin;
				touchDistX = spacingHoriz(event);
				touchDistY = spacingVert(event);
				break;
			//-----------------------------------------------------------------
			case MotionEvent.ACTION_MOVE:
				if (!mMAScreen.isTesting() && mActivePointerId == 0) {
					final int pointerIndex = event.findPointerIndex(mActivePointerId);
					
			        x = event.getX(pointerIndex);
			        y = event.getY(pointerIndex);
					int horizDiff = (int)(x-mLastX);
					int vertDiff = (int)(y-mLastY);
					
					if (mMode == PINCH) {
						int dX = (int) ((spacingHoriz(event) - touchDistX));///event.getXPrecision());
						int dY = (int) ((spacingVert(event) - touchDistY));// /event.getYPrecision());
						
						// Add the diff in X distance to width, and diff in Y distance to height
						int oldWidth = params.width;
						int oldHeight = params.height;
						params.width = Math.min(Math.max(this.getMinWidth(),
														mLastW + dX),
														this.getMaxWidth());
						params.height = Math.min(Math.max(this.getMinHeight(),
														mLastH + dY),
														this.getMaxHeight());
						// Keep the element centered while resizing.
						if (oldWidth != params.width) {
							params.leftMargin = (int) (mLastLeft - dX/2);
						} if (oldHeight != params.height) {
							params.topMargin = (int) (mLastTop - dY/2);
						}
						
					} else {
						float dist = dist(mLastX, mLastY, event.getX(), event.getY());
//						Log.i("ACACAC", String.format("Drag dist: %f", dist));
						if (dist > MIN_DRAG_DIST) {
							mMode = DRAG;
						}
						if (mMode == DRAG) {
							params.leftMargin += horizDiff;
							params.topMargin += vertDiff;
							params.rightMargin -= horizDiff;
							params.bottomMargin -= vertDiff;
						}
					}
					this.setLayoutParams(params);
				}
				break;
			//-----------------------------------------------------------------
			case MotionEvent.ACTION_UP:
				if (mMode == NONE) {
					elementWasTapped();
				} else {
					mMAScreen.updateModel(this, UndoStatus.MOVE, prev);
				}
				if (mMode == RESIZE) {
					mMAScreen.updateModel(this, UndoStatus.RESIZE, prev);
				}
				mMode = NONE;
				mActivePointerId = INVALID_POINTER_ID;
				break;
			//-----------------------------------------------------------------
			case MotionEvent.ACTION_POINTER_UP:
//				Log.i("ACACAC", "ACTION POINTER UP");
				// Extract the index of the pointer that left the touch sensor
		        final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
		                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		        final int pointerId = event.getPointerId(pointerIndex);
		        if (pointerId == mActivePointerId) {
		            // This was our active pointer going up. Choose a new
		            // active pointer and adjust accordingly.
		            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
		            mLastX = event.getX(newPointerIndex);
		            mLastY = event.getY(newPointerIndex);
		            mActivePointerId = event.getPointerId(newPointerIndex);
		        }
				mMode = DRAG;
				break;
			default:
				break;
		}
		mMAScreen.invalidate();
		return true;
	}
	
	//-------------------------------------------------------------------------
	public void enforceDimensionConstraints() {
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
		if (lp == null) return;

		if (lp.height > getMaxHeight()) 
			lp.height = getMaxHeight();
		else if (lp.height < getMinHeight())
			lp.height = getMinHeight();

		if (lp.width > getMaxWidth()) 
			lp.width = getMaxWidth();
		else if (lp.width < getMinWidth())
			lp.width = getMinWidth();
		
		this.setLayoutParams(lp);
	}
	
	//-------------------------------------------------------------------------
	private void elementWasTapped() {
		if (mMAScreen.isTesting()) {
			mMAScreen.onElementTappedInTest(this);
		} else {
			mMAScreen.onElementTapped(this);
		}
	}
	
	//-------------------------------------------------------------------------
	/**
	 * Called when an MAScreenElement is selected. This method should perform general
	 * on-select actions for all ScreenElements. Subclasses should call super.select()
	 * when overriding.
	 */
	public void select() {
        isSelected = true;
        mHighlightOverlay.setVisibility(VISIBLE);
//		mDragTarget.setVisibility(VISIBLE);
        this.invalidate();
	}

	//-------------------------------------------------------------------------
	/**
	 * Called when an MAScreenElement is deselected. This method should perform general
	 * on-deselect actions for all ScreenElements. Subclasses should call super.deselect()
	 * when overriding.
	 */
	public void deselect() {
		isSelected = false;
        mHighlightOverlay.setVisibility(GONE);
//		mDragTarget.setVisibility(GONE);
		this.invalidate();
	}
	
	//-------------------------------------------------------------------------
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
	
	
	//-------------------------------------------------------------------------
	/** Determine the space between the first two fingers */
//    private float spacing(MotionEvent event) {
//        // ...
//        float x = event.getX(0) - event.getX(1);
//        float y = event.getY(0) - event.getY(1);
//        return Math.abs(FloatMath.sqrt(x * x + y * y));
//    }
    private float spacingHoriz(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        return Math.abs(x);
    }
    private float spacingVert(MotionEvent event) {
        float y = event.getY(0) - event.getY(1);
        return Math.abs(y);
    }
    private float dist(float x1, float y1, float x2, float y2) {
    	float x = x1 - x2;
        float y = y1 - y2;
        return Math.abs(FloatMath.sqrt(x * x + y * y));
    }
	
	//-------------------------------------------------------------------------
	public int getMinWidth() {
		return this.MIN_WIDTH;
	}
	public int getMaxWidth() {
		return this.MAX_WIDTH;
	}
	public int getMinHeight() {
		return this.MIN_HEIGHT;
	}
	public int getMaxHeight() {
		return this.MAX_HEIGHT;
	}
	
	//-------------------------------------------------------------------------
	public MAScreen getDestinationScreen() {
		return mDestinationScreen;
	}

	//-------------------------------------------------------------------------
	public void setDestinationScreen(MAScreen mDestinationScreen) {
		this.mDestinationScreen = mDestinationScreen;
	}
	
	//-------------------------------------------------------------------------
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

	public MAScreen getmScreen() {
		return mMAScreen;
	}

	public void setmScreen(MAScreen mMAScreen) {
		this.mMAScreen = mMAScreen;
	}
}
