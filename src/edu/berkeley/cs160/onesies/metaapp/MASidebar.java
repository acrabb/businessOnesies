package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * This is a class to simply take a load off the DevelopmentActivity class and 
 * just handle the sidebar view. It should be instantiated with a view, and any
 * internal handlings of the sidebar should be handled via function calls to
 * this class.
 * 
 * @author andre
 *
 */
public class MASidebar extends LinearLayout{

	private DevelopmentActivity mActivity;
	
	private Button mHomeButton;
	private Button mNotesButton;
	private Button mDrawButton;
	private Button mShapesButton;
	private Button mElementButton;
	private Button mMenuButton;
	
	private Button mLinkButton;
	
	private boolean mElementBarShowing = false;
	
	private int backColor = R.color.seagreen;
	
	public MASidebar(Context context) {
		super(context);
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MASidebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MASidebar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setBackgroundColor(getResources().getColor(backColor));
	}
	
	public void setUp(TestingActivity activity) {
		this.setBackgroundColor(Color.GRAY);
	}
	
	public void setUp(DevelopmentActivity activity) {
		mActivity = activity;
		
		mHomeButton = (Button) findViewById(R.id.homeButton);
		mNotesButton = (Button) findViewById(R.id.notesButton);
		mDrawButton = (Button) findViewById(R.id.drawButton);
		mShapesButton = (Button) findViewById(R.id.shapesButton);
		mElementButton = (Button) findViewById(R.id.elementsButton);
		mMenuButton = (Button) findViewById(R.id.menuButton);
		
		mLinkButton = (Button) findViewById(R.id.linkButton);
		
//		Button b = new Button(getContext());
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		b.setLayoutParams(params);
//		addView(b);
		mLinkButton = new Button(getContext());
		mLinkButton.setText("Link"); 
		mLinkButton.setLayoutParams(mElementButton.getLayoutParams());
		
		
		mHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//homeButtonTapped(arg0);
				mActivity.goTestMode();
			}
		});
		mNotesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				notesButtonTapped(arg0);
			}
		});
		mDrawButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				drawButtonTapped(arg0);
			}
		});
		mShapesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				shapesButtonTapped(arg0);
			}
		});
		mElementButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				elementButtonTapped(arg0);
			}
		});
		mMenuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				menuButtonTapped(v);
			}
		});
		mLinkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				linkButtonTapped(arg0);
			}
		});
	}
	
	private void homeButtonTapped(View button) {
		mActivity.makeToast("Hello Home!");
	}
	private void notesButtonTapped(View button) {
		mActivity.makeToast("Hello Notes!");
	}
	private void drawButtonTapped(View button) {
		mActivity.makeToast("Hello Draw!");
	}	
	private void shapesButtonTapped(View button) {
		mActivity.showShapesPopup(button);
//		mActivity.makeToast("Hello Shapes!");
	}
	private void elementButtonTapped(View button) {
		mActivity.showElementsPopup(button);
//		mActivity.makeToast("Hello Elements!");
	}
	private void menuButtonTapped(View button) {
		mActivity.showMenuPopup(button);
	}
	private void linkButtonTapped(View button) {
//		mActivity.makeToast("Hello Link!");
		mActivity.showLinkPopup(button);
	}

	
	public void showElementContextBar() {
		if (!mElementBarShowing) {			
//			makeToast("SHOW ELEMENT CONTEXT BAR");
	//		mLinkButton.setVisibility(VISIBLE);
			addView(mLinkButton);
		}
		mElementBarShowing = true;
	}
	public void hideElementContextBar() {
//		makeToast("HIDE ELEMENT CONTEXT BAR");
//		mLinkButton.setVisibility(GONE);
		removeView(mLinkButton);
		mElementBarShowing = false;
	}
	
	
	
	public DevelopmentActivity getmActivity() {
		return mActivity;
	}

	public void setmActivity(DevelopmentActivity mActivity) {
		this.mActivity = mActivity;
	}
	
	
	public void makeToast(String format, Object... args) {
		Toast.makeText(getContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
}
