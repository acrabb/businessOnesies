package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class MAScreen extends RelativeLayout {

	private int backColor = R.color.lightseagreen;
	private float mRatio = 1.60f;
	
	private int 				mChildCount;
	private MAScreenElement 	mSelectedChild;
	private DevelopmentActivity mDevelopmentActivity;
	private String				mName;
	
	public MAScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(getResources().getColor(backColor));
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSelectedChild != null) {
					mSelectedChild.deselect();
					mDevelopmentActivity.hideElementSidebar();
					mSelectedChild = null;
				}
			}
		});
		
	}
	
	public void onElementTapped(MAScreenElement e) {
		determineSelectionState(e);
		determineSidebarState();
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
		if(mSelectedChild == null) {
			mDevelopmentActivity.showDefaultSidebar();
			return;
		}
		switch (mSelectedChild.getmType()) {
			case BUTTON:
				mDevelopmentActivity.showElementSidebar(mSelectedChild);
				break;
			case TEXT_LABEL:
				break;
			default:
				//Things;	
		}
	}

	public DevelopmentActivity getmDevelopmentActivity() {
		return mDevelopmentActivity;
	}
	public void setmDevelopmentActivity(DevelopmentActivity mDevelopmentActivity) {
		this.mDevelopmentActivity = mDevelopmentActivity;
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
	
	

}
