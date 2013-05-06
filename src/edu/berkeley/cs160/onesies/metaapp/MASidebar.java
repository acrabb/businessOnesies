package edu.berkeley.cs160.onesies.metaapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
public class MASidebar extends RelativeLayout{

	private DevelopmentActivity mActivity;
	private int 	backColor = R.color.sidebarColor;
	
	private Button 	mHomeButton;
	private Button 	mAddButton;
	private Button 	mMenuButton;
	private Button  mUndoButton;
	private Button	mHelpButton;
	
	private Button	mSketchAddButton;
	private Button	mSketchCancelButton;
	private Button	mDeleteButton;
	private Button	mElementForwardButton;
	private View	mSpacerContext;
	private View	mSpacerBottom;
	private Button	mLinkButton;
	private Button	mEditTextButton;

	//-------------------------------------------------------------------------
	public MASidebar(Context context) {
		super(context);
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	//-------------------------------------------------------------------------
	public MASidebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(getResources().getColor(backColor));
	}

	//-------------------------------------------------------------------------
	public MASidebar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setBackgroundColor(getResources().getColor(backColor));
	}
	
	//-------------------------------------------------------------------------
	public void setUp(DevelopmentActivity activity) {
		mActivity = activity;
		
		//---Main Dev Buttons ---------------------------------
		mHomeButton = (Button) findViewById(R.id.homeButton);
		mAddButton = (Button) findViewById(R.id.addButton);
		mMenuButton = (Button) findViewById(R.id.menuButton);
		mUndoButton = (Button) findViewById(R.id.undoButton);
		mHelpButton = (Button) findViewById(R.id.helpButton);
		
		//---Contextual Buttons ---------------------------------
		mLinkButton = (Button) findViewById(R.id.linkButton);
		mEditTextButton = (Button) findViewById(R.id.editTextButton);
		mDeleteButton = (Button) findViewById(R.id.deleteElementButton);
		mSketchAddButton = (Button) findViewById(R.id.sketchAddButton);
		mSketchCancelButton = (Button) findViewById(R.id.sketchCancelButton);
		mElementForwardButton = (Button) findViewById(R.id.elementForward);
		mSpacerBottom = findViewById(R.id.spacer4);
		mSpacerContext = findViewById(R.id.spacer5);
		
		mHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onHomeButtonTapped();
			}
		});
		mAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showAddPopup(arg0);
			}
			
		});
		mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.onDeleteButtonTapped(v);
			}
		});
		mMenuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.showMenuPopup(v);
			}
		});
		mUndoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.onUndoTapped();
			}
		});
		mHelpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.onHelpTapped();
			}
		});
		
	//---- SKETCH ZONE CONTEXT BUTTONS ----
		mSketchAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.onAddSketchTapped();
			}
		});
		mSketchCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.hideSketchZone();
			}
		});
	//---- MABUTTON CONTEXT BUTTONS ----
		mLinkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.showLinkPopup(arg0);
			}
		});
	//---- _______ CONTEXT BUTTONS ----
		mEditTextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onEditTextTapped();
			}
		});
		mElementForwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mActivity.onElementForwardTapped();
			}
		});
		
		showDefaultSidebar();
	}

	//-------------------------------------------------------------------------
	public void showDefaultSidebar() {
		mHomeButton.setVisibility(VISIBLE);
		mSpacerContext.setVisibility(INVISIBLE);
		mSpacerBottom.setVisibility(VISIBLE);
		mUndoButton.setVisibility(VISIBLE);
		mMenuButton.setVisibility(VISIBLE);
		
		
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mElementForwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
	}
	//-------------------------------------------------------------------------
	public void showSketchZoneBar() {
		mHomeButton.setVisibility(VISIBLE);
		mUndoButton.setVisibility(INVISIBLE);
		mMenuButton.setVisibility(INVISIBLE);
		mSpacerContext.setVisibility(INVISIBLE);
		mSpacerBottom.setVisibility(INVISIBLE);
		
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mElementForwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(VISIBLE);
		mSketchCancelButton.setVisibility(VISIBLE);
		mUndoButton.setVisibility(INVISIBLE);
	}
	//-------------------------------------------------------------------------
	public void showElementContextBar(MAScreenElement element) {
		if (element == null)
			return;
		switch (element.getmType()) {
		case BUTTON:
		case TEXT_LABEL:
			mLinkButton.setVisibility(VISIBLE);
			mEditTextButton.setVisibility(VISIBLE);
			break;
		case CHECKBOX:
		case RADIOBUTTON:
			mLinkButton.setVisibility(INVISIBLE);
			mEditTextButton.setVisibility(VISIBLE);
			break;
		case SHAPE:
			mLinkButton.setVisibility(VISIBLE);
			mEditTextButton.setVisibility(INVISIBLE);
			break;
		case SLIDER:
		case TOGGLE:
			mLinkButton.setVisibility(INVISIBLE);
			mEditTextButton.setVisibility(INVISIBLE);
			break;
		default:
			break;
		}
		mSpacerContext.setVisibility(VISIBLE);
		mSpacerBottom.setVisibility(VISIBLE);

		mDeleteButton.setVisibility(VISIBLE);
		mElementForwardButton.setVisibility(VISIBLE);
		// IMPLEMENT FIRST
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
		mUndoButton.setVisibility(VISIBLE);
	}
	//-------------------------------------------------------------------------
	public void showCustomElementBar() {
		mSpacerContext.setVisibility(VISIBLE);
		mSpacerBottom.setVisibility(VISIBLE);
		mLinkButton.setVisibility(VISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(VISIBLE);
		mElementForwardButton.setVisibility(VISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
		mUndoButton.setVisibility(VISIBLE);
	}
	
	//-------------------------------------------------------------------------
	public void showTestBar() {
		mHomeButton.setVisibility(VISIBLE);
		mUndoButton.setVisibility(INVISIBLE);
		mMenuButton.setVisibility(VISIBLE);
		mSpacerContext.setVisibility(INVISIBLE);
		mSpacerBottom.setVisibility(INVISIBLE);
		
		mLinkButton.setVisibility(INVISIBLE);
		mEditTextButton.setVisibility(INVISIBLE);
		mDeleteButton.setVisibility(INVISIBLE);
		mElementForwardButton.setVisibility(INVISIBLE);
		mSketchAddButton.setVisibility(INVISIBLE);
		mSketchCancelButton.setVisibility(INVISIBLE);
	}

	//-------------------------------------------------------------------------
	public DevelopmentActivity getmActivity() {
		return mActivity;
	}

	//-------------------------------------------------------------------------
	public void setmActivity(DevelopmentActivity mActivity) {
		this.mActivity = mActivity;
	}
	
	
	//-------------------------------------------------------------------------
	public void makeToast(String format, Object... args) {
		Toast.makeText(getContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
}
