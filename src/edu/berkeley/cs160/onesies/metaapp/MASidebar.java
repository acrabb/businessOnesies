package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
	
	private int backColor = R.color.seagreen;
	
	public MASidebar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MASidebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	public MASidebar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(getResources().getColor(backColor));
	}
	
	public void setUp(DevelopmentActivity activity) {
		mActivity = activity;
		
		mHomeButton = (Button) findViewById(R.id.homeButton);
		mNotesButton = (Button) findViewById(R.id.notesButton);
		mDrawButton = (Button) findViewById(R.id.drawButton);
		mShapesButton = (Button) findViewById(R.id.shapesButton);
		mElementButton = (Button) findViewById(R.id.elementsButton);
		
		
		mHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				homeButtonTapped(arg0);
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
	}
	
	private void homeButtonTapped(View button) {
//		pMenu = new PopupWindow(button);
//		pMenu = new PopupWindow(hoontentView, width, height, true);
		
		mActivity.makeToast("Hello Home!");
	}
	private void notesButtonTapped(View button) {
//		pMenu = new PopupWindow(button);
//		pMenu = new PopupWindow(shapesContentView, width, height, true);
		mActivity.makeToast("Hello Notes!");
	}
	private void drawButtonTapped(View button) {
//		pMenu = new PopupWindow(button);
		mActivity.makeToast("Hello Draw!");
	}	
	private void shapesButtonTapped(View button) {
//		mActivity.sandboxInflateShapesGridLayout();
//		pMenu.showAsDropDown(button, 0, 0);
		mActivity.showShapesPopup(button);
		mActivity.makeToast("Hello Shapes!");
	}
	private void elementButtonTapped(View button) {
//		pMenu = new PopupWindow(button);
//		mActivity.sandbox();
		mActivity.makeToast("Hello Elements!");
	}

	public DevelopmentActivity getmActivity() {
		return mActivity;
	}

	public void setmActivity(DevelopmentActivity mActivity) {
		this.mActivity = mActivity;
	}
	
	
}
