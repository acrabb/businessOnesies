package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import edu.berkeley.cs160.onesies.metaapp.MAElements.MAButton;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class MAScreen extends RelativeLayout {

	private int backColor = R.color.white;
	private float mRatio = 1.60f;
	
	private int 				mChildCount;
	private MAScreenElement 	mSelectedChild;
	private DevelopmentActivity mDevelopmentActivity;
//	private TestingActivity 	mTestingActivity;
	private String				mName;
//	protected boolean    		testMode = false;
	private ArrayList<MAScreenElement> children; // not needed?
	private UndoModel           undo;
	
	public MAScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		undo = new UndoModel(this);
		this.setBackgroundColor(getResources().getColor(backColor));
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deselectAll();
			}
		});
		
	}
	
	public void deselectAll() {
		if (mSelectedChild != null) {
			mSelectedChild.deselect();
			mSelectedChild = null;
			determineSidebarState();
		}
		
	}
	public void onElementTapped(MAScreenElement e) {
		determineSelectionState(e);
		determineSidebarState();
	}
	public void onElementTappedInTest(MAScreenElement e) {
		switch (e.getmType()) {
			case BUTTON:
				mDevelopmentActivity.onButtonTappedInTest((MAButton) e);
				break;
			default:
				break;
		}
	}
	private void determineSelectionState(MAScreenElement e) {
		if (e == null) {
			return;
		}
		// If the element is already selected, deselect it.
		if (e == mSelectedChild) {
			e.deselect();
			mSelectedChild = null;
		} else {
		// ...deselect any current selected element...
			if(mSelectedChild != null) {
				mSelectedChild.deselect();
			}
		// ...then select the tapped element.
			e.select();
			mSelectedChild = e;
		}
	}
	private void determineSidebarState() {
		if(mDevelopmentActivity.isTesting()) {
			mDevelopmentActivity.showTestSidebar();
			return;
		}
		mDevelopmentActivity.showDefaultSidebar();
		if(mSelectedChild == null) {
//			mDevelopmentActivity.showDefaultSidebar();
			return;
		}
		switch (mSelectedChild.getmType()) {
			case BUTTON:
			case TEXT_LABEL:
			case SHAPE:
				mDevelopmentActivity.showElementSidebar();
				break;
			case CUSTOM:
				mDevelopmentActivity.showCustomElementSidebar();
				break;
			default:
				//Things;	
		}
	}

//	public void setTestMode(final boolean mode) {
//		this.testMode = mode;
//	}
//	
//	public boolean getTestMode() {
//		return this.testMode;
//	}
	
	public DevelopmentActivity getmDevelopmentActivity() {
		return mDevelopmentActivity;
	}
	public void setmDevelopmentActivity(DevelopmentActivity mDevelopmentActivity) {
		this.mDevelopmentActivity = mDevelopmentActivity;
	}
//	public void setmTestingActivity(TestingActivity mTestingActivity) {
//		this.mTestingActivity = mTestingActivity;
//	}
//	public TestingActivity getmTestingActivity() {
//		return mTestingActivity;
//	}
	public boolean isTesting() {
		return mDevelopmentActivity.isTesting();
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public MAScreenElement getSelectedElement() {
		return mSelectedChild;
	}
	public void deleteSelected() {
		if (mSelectedChild != null) {
			this.updateModel(mSelectedChild, UndoStatus.REMOVE);
			this.removeView(mSelectedChild);
			mSelectedChild = null;
		}

	}
	
	/** Activity will use information from UndoModel, and handle it accordingly. */
	public void handleUndo() {
		undo.undoHandler();
		this.invalidate();
	}
	
	/** MAScreen will be responsible for passing data from Activity to its dedicated undoModel. */
	public void updateModel(final MAScreenElement screenElement, final UndoStatus status) {
		undo.updateModel(screenElement, status);
	}
	
	/** Overloaded updateModel for editing Text. */
	public void updateModel(final MAScreenElement screenElement, final UndoStatus status, final String text) {
		undo.updateModel(screenElement, status, text);
	}
	
	/**Overloaded updateModel for moving and resizing Elements. */
	public void updateModel(final MAScreenElement screenElement, final UndoStatus status, final RelativeLayout.LayoutParams prevState) {
		undo.updateModel(screenElement, status, prevState);
	}
	
	/** undoAdd reverts this MAScreen back to the state before screenElement was added.*/
	public void undoAdd(final MAScreenElement screenElement) {
		screenElement.deselect();
		this.removeView(screenElement);
		mSelectedChild = null;
		mDevelopmentActivity.showDefaultSidebar();
	}
	
	/** undoRemove reverts this MAScreen back to the state before screenElement was removed.*/
	public void undoRemove(final MAScreenElement screenElement) {
		screenElement.deselect();
		this.addView(screenElement);
		mSelectedChild = null;
		mDevelopmentActivity.showDefaultSidebar();
	}
	
	/** undoText reverts the text value of ScreenElement to prevText. */
	public void undoText(final MAScreenElement screenElement, final String prevText) {
		screenElement.undoText(prevText);
	}
	
	/** undoMove positions the ScreenElement in the last spot before it was moved. */
	public void undoMove(final MAScreenElement screenElement, final RelativeLayout.LayoutParams prevState) {
		screenElement.undoState(prevState);
	}
	
	/** undoResize resets the height/width of the screenElement before it was resized. */
	public void undoResize(final MAScreenElement screenElement, final RelativeLayout.LayoutParams prevState) {
		screenElement.undoState(prevState);
	}
}