package edu.berkeley.cs160.onesies.metaapp;

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
	
	public MAScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
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
		mDevelopmentActivity.showDefaultSidebar();
		if(mSelectedChild == null) {
//			mDevelopmentActivity.showDefaultSidebar();
			return;
		}
		switch (mSelectedChild.getmType()) {
			case BUTTON:
			case TEXT_LABEL:
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
			this.removeView(mSelectedChild);
			mSelectedChild = null;
		}
	}
}