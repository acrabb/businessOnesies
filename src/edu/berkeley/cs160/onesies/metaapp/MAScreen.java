package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

public class MAScreen extends RelativeLayout {

	private int backColor = R.color.lightseagreen;
	private float mRatio = 1.60f;
	
	private int 				mChildCount;
	private MAScreenElement 	mSelectedChild;
	private DevelopmentActivity mDevelopmentActivity;
	
	/*
	public MAScreen(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
//		this.setBackgroundColor(getResources().getColor(backColor));
	}
	*/

	public MAScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}
	/*
	public MAScreen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
//		this.setBackgroundColor(getResources().getColor(backColor));
	}
	*/
	
	public void onElementTapped(MAScreenElement e) {
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
	

}
