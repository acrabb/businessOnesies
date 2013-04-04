package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

public class MAScreen extends RelativeLayout {

	private int backColor = R.color.lightseagreen;
	private float mRatio = 1.60f;
	
	public MAScreen(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MAScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MAScreen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}
	

}
