package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
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
		// TODO Check what type (ElementType) e is, then act accordingly.
		if (e == null) {
			return;
		}
		// If the element is already selected, deselect it.
		if (e == mSelectedChild) {
			e.deselect();
			mSelectedChild = null;
			mDevelopmentActivity.hideElementSidebar();
		} else {
			if(mSelectedChild != null) {
		// ...then deselect the current selected element...
				mSelectedChild.deselect();
			}
		// ...then select the tapped element.
			e.select();
			mDevelopmentActivity.showElementSidebar(e);
			mSelectedChild = e;
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
