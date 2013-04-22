package edu.berkeley.cs160.onesies.metaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MAButton;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MATextLabel;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MAOval;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MARectangle;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MATriangle;


public class DevelopmentActivity extends Activity {

	// UI Objects-------------------------------
	private MAScreen			mScreen;
//	private MAScreen            testScreen;
	private RelativeLayout		mDevRelLayout;
	private MASidebar			mSidebar;
	private PopupWindow			mPopupWindow;
	private PopupMenu			mMenuPopupMenu;
	private PopupMenu			mLinkPopupMenu;
	private GridLayout			mElementsGridView;
	private GridLayout			mShapesGridView;

	private View 				mShapesContentView;
	private View 				mElementsContentView;
//	private View 				mLinkContentView;
	
	private MACanvas			mSketchCanvas;
	
	// Non-UI Objects--------------------------
	private LayoutInflater		mLayoutInflater;
	private Resources			mResources;
	protected static MAProject	mProject;
	private boolean				mInSketchZone = false;
	private boolean				mIsTesting = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the menu; this adds items to the action bar if it is present.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.development_main);
		
		mLayoutInflater = (LayoutInflater)getApplicationContext().
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResources = getResources();
		
		mDevRelLayout = (RelativeLayout) findViewById(R.id.developmentRelative);
		mScreen = (MAScreen) findViewById(R.id.screen);
		mScreen.setmDevelopmentActivity(this);
		mSidebar = (MASidebar) findViewById(R.id.sidebar);
		mSidebar.setUp(this);
		
//		testScreen = (MAScreen) mLayoutInflater.inflate(R.layout.ma_screen, null);
		
		createNewProject();
		mProject.addFirstScreen(mScreen);
		
		//TESTING BADGING
//		MAScreenElement v = (MAScreenElement) mLayoutInflater.inflate(R.layout.ma_element, null);
//		mScreen.addView(v);
	}
	
	//-------------------------------------------------------------------------
	private void createNewProject() {
		//TODO
		mProject = new MAProject("Project1");
		String name = mProject.getNextDefaultScreenName();
		mProject.addScreenToProject(name, mScreen);
		mScreen.setName(name);
		makeToast("Screen '%s' Created.", name);
	}
	
	//-------------------------------------------------------------------------
	private void showScreenWithName(String name) {
		// Deselect any current selection.
		MAScreenElement selected = mScreen.getSelectedElement();
		if(selected != null) {
			selected.setScreenLinkedTo(name);
			selected.deselect();
		}
		MAScreen newScreen = mProject.getScreenWithName(name);
		mDevRelLayout.removeView(mScreen);
		mDevRelLayout.addView(newScreen,mScreen.getLayoutParams());
		showDefaultSidebar();
		mScreen = newScreen;
	}
	
	
	
	/*****************************************************************************
	 * MASIDEBAR "CALLBACK" METHODS
	 *****************************************************************************/
	public void showSketchZone(View sketchButton) {
		if (!mInSketchZone) {
			mSketchCanvas = new MACanvas(getApplicationContext());
			mDevRelLayout.addView(mSketchCanvas, mScreen.getLayoutParams());			
			mSidebar.showSketchZoneBar();
			mInSketchZone = true;
		} else {
			onAddSketchTapped();
			hideSketchZone();
		}
	}
	//-------------------------------------------------------------------------
	private void hideSketchZone() {
			mDevRelLayout.removeView(mSketchCanvas);
			showDefaultSidebar();
			mInSketchZone = false;
	}
	//-------------------------------------------------------------------------
	public void onAddSketchTapped() {
		Bitmap map = mSketchCanvas.getSketch();
		if (map != null) {
			Log.i("ACACACAC", String.format("Map h: %d, w:%d", map.getHeight(), map.getWidth()));
			MAScreenElement custom = createElement(ElementType.CUSTOM, map);
			RelativeLayout.LayoutParams p = 
					new RelativeLayout.LayoutParams(map.getWidth(), map.getHeight());
			p.leftMargin = mSketchCanvas.getLeftMargin();
			p.topMargin = mSketchCanvas.getTopMargin();
			custom.setLayoutParams(p);
			mScreen.addView(custom);
		}
		hideSketchZone();
	}
	
	//-------------------------------------------------------------------------
	public void showShapesPopup(View button) {
		mShapesContentView = mLayoutInflater.inflate(R.layout.shape_popup, null);
		mPopupWindow = new PopupWindow(mShapesContentView,
				(int) mResources.getDimension(R.dimen.shapePopupWidth),
				(int) mResources.getDimension(R.dimen.shapePopupHeight), false);
		ImageView triangle = (ImageView) mShapesContentView.findViewById(R.id.ma_triangle);
		Log.d("meta", "Triangle: w: " +triangle.getWidth() +";h:"+triangle.getHeight());
		Bitmap triBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
		Canvas triCanvas = new Canvas(triBitmap);
		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.achalRed));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		triCanvas.drawLine(50, 50, 25, 0, paint);
		triCanvas.drawLine(25, 0, 0, 50, paint);
		triCanvas.drawLine(0,50,50,50,paint);
		triangle.setImageDrawable(new BitmapDrawable(getResources(), triBitmap));
		
		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup to be
		 * dismissed properly
		 */
		mShapesGridView = (GridLayout) mShapesContentView.findViewById(R.id.shapesGrid);
		View v;
		int numChildren = mShapesGridView.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			v = mShapesGridView.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("meta", "Clicked on a shape");
					addShapeElement(v);
				}
			});
		}
		
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.showAsDropDown(button, 0, 0);
	}
	
	//-------------------------------------------------------------------------
	public void showElementsPopup(View button) {
		mElementsContentView = mLayoutInflater.inflate(R.layout.elements_popup, null);
		mPopupWindow = new PopupWindow(mElementsContentView,
				(int) mResources.getDimension(R.dimen.elementsPopupWidth),
				(int) mResources.getDimension(R.dimen.elementsPopupHeight), false);
		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup to be
		 * dismissed properly
		 */
		mElementsGridView = (GridLayout) mElementsContentView.findViewById(R.id.elementsGrid);
		int numChildren = mElementsGridView.getChildCount();
		View v;
		for (int i=0; i<numChildren; i++) {
			v = mElementsGridView.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addUIElement(v);
				}
			});
		}
		
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.showAsDropDown(button, 0, 0);
	}
	
	//-------------------------------------------------------------------------
	public void showLinkPopup(View linkButton) {
		mLinkPopupMenu = new PopupMenu(getApplicationContext(), linkButton);
		mLinkPopupMenu.inflate(R.menu.link_menu);
		mLinkPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//Make sure they're linking an MAButton
				if (!(mScreen.getSelectedElement() instanceof MAButton)) {
					makeToast("CAN ONLY LINK BUTTONS.");
					return true;
				}
				MAButton button = (MAButton) mScreen.getSelectedElement();
				switch(item.getItemId()) {
					case R.id.link_menu_new:
						onLinkToNewScreenSelected(button);
						break;
					case R.id.link_menu_existing:
						onLinkToExistingScreenSelected(button);
						break;
					default:
				}
				return true;
			}
		});
		mLinkPopupMenu.show();
	}
	//-------------------------------------------------------------------------
	public void showMenuPopup(View button) {
		mMenuPopupMenu = new PopupMenu(getApplicationContext(), button);
		mMenuPopupMenu.inflate(R.menu.dev_menu);
		mMenuPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if(mIsTesting) {
					mMenuPopupMenu.getMenu().getItem(3).setVisible(false);
					mMenuPopupMenu.getMenu().getItem(4).setVisible(true);				
				} else {
					mMenuPopupMenu.getMenu().getItem(3).setVisible(true);
					mMenuPopupMenu.getMenu().getItem(4).setVisible(false);
				}
				switch(item.getItemId()) {
					case R.id.link_menu_new:
						// Create a new screen that is not linked to by anything.
//						onLinkToNewScreenSelected();
						break;
					case R.id.link_menu_existing:
						// Show Big Picture for the selection of a screen.
//						onLinkToExistingScreenSelected();
						break;
					case R.id.dev_menu_big_picture:
						onBigPictureSelected();
						break;
					case R.id.dev_menu_test:
						onTestModeTapped();
						break;
					case R.id.dev_menu_test_exit:
						onExitTestModeTapped();
						break;
					default:
				}
				return false;
			}
		});
		mMenuPopupMenu.show();
		/**/
	}
	
	
	//-------------------------------------------------------------------------
	public void onDeleteButtonTapped(View deleteButton) {
		mScreen.deleteSelected();
		showDefaultSidebar();
	}
	
	//-------------------------------------------------------------------------
	String m_Text = "";
	public void onEditTextTapped() {
		if(mScreen.getSelectedElement() == null) { 
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Title");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        m_Text = input.getText().toString();
		        ((MAScreenElement) mScreen.getSelectedElement()).setLabel(m_Text);
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.show();
	}
	
	
	
	/*****************************************************************************
	 * POPUP "CALLBACK" METHODS
	 *****************************************************************************/
	private void addUIElement(View element) {
		// Take the view, and add it to the MAScreen object.
		MAScreenElement clone;
		if (element instanceof Button) {
			clone = new MAButton(getApplicationContext(), mScreen);
		} else if(element instanceof TextView) {
			clone = new MATextLabel(getApplicationContext(), mScreen, "Text Label");
		} else {
			return;
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 100);
		clone.setLayoutParams(params);
		mScreen.addView(clone);
	}
	//-------------------------------------------------------------------------
	private void addShapeElement(View element) {
		MAScreenElement clone;
		switch (element.getId()) {
		case R.id.ma_rectangle:
			clone = new MARectangle(getApplicationContext(), mScreen);
			break;
		case R.id.ma_oval:
			clone = new MAOval(getApplicationContext(), mScreen);
			break;
		case R.id.ma_triangle:
			clone = new MATriangle(getApplicationContext(), mScreen);
			break;
		default:
			clone = new MARectangle(getApplicationContext(), mScreen);
			break;
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 100);
		clone.setLayoutParams(params);

	
		mScreen.addView(clone);
	}
	
	//-------------------------------------------------------------------------
	private void onLinkToNewScreenSelected(MAButton button) {
		// TODO MODULARIZE
		// Create new Screen
		MAScreen newScreen = (MAScreen) mLayoutInflater.inflate(R.layout.ma_screen, null);
		newScreen.setmDevelopmentActivity(this);
		String newName = mProject.getNextDefaultScreenName();
		newScreen.setName(newName);
		mProject.addScreenToProject(newName, newScreen);
		if(button != null) {
			button.setDestinationScreen(newScreen);			
		}
		
		showScreenWithName(newName);
		
		// Actually link the button to new screen
		button.setDestinationScreen(newScreen);
		makeToast("Screen '%s' Created.", newName);
	}
	//-------------------------------------------------------------------------
	private void onLinkToExistingScreenSelected(MAButton button) {
		makeToast("LINK TO EXISTING SCREEN");
		
	}
	
	
	
	/*****************************************************************************
	 * MENU "CALLBACK" METHODS
	 *****************************************************************************/
	public void onTestModeTapped() {
		if (!mIsTesting) {
			mIsTesting = true;
			makeLogI(">>> Test mode tapped");
			showScreenWithName(mProject.getFirstScreen().getName());
			showTestSidebar();
			// update menu
			mMenuPopupMenu.getMenu().getItem(3).setVisible(false);
			mMenuPopupMenu.getMenu().getItem(4).setVisible(true);
		}
		
		// ------ OLD ------
//		Intent mIntent = new Intent(this, TestingActivity.class);
//		startActivity(mIntent);		
	}
	//-------------------------------------------------------------------------
	public void onExitTestModeTapped() {
		if (mIsTesting) {
			mIsTesting = false;
			makeLogI(">>> Exit test mode tapped");
			showScreenWithName(mScreen.getName());
			showDefaultSidebar();
			// update menu
			mMenuPopupMenu.getMenu().getItem(3).setVisible(true);
			mMenuPopupMenu.getMenu().getItem(4).setVisible(false);
		}
	}
	
	/** 
	 * Test for passing custom objects through intents using serializable bundles
	 */
	public String getActivityString() {
		return "Development";
	}
	//-------------------------------------------------------------------------
	private void onBigPictureSelected() {
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		final Dialog dia = new Dialog(this);
		View v = mLayoutInflater.inflate(R.layout.overview_temp, null, false);
		ListView screenList = (ListView) v.findViewById(android.R.id.list);
		screenList.setAdapter(new MAScreensAdapter(getApplicationContext(), mProject.getScreens()));
		screenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showScreenWithName(((MAScreen) parent.getAdapter().getItem(position)).getName());
				dia.dismiss();
			}
		});
		
		dia.setContentView(v);
		dia.setTitle("Go To Screen:");
		dia.show();
	}
	
	
	/*****************************************************************************
	 * TESTING MODE METHODS
	 *****************************************************************************/
	public void onButtonTappedInTest(MAButton button) {
		if (button.getDestinationScreen() != null) {
			showScreenWithName(button.getDestinationScreen().getName());
			showTestSidebar();			// HANDLED IN ONTESTBUTTONTAPPED
		}
	}
	
	/*****************************************************************************
	 * SIDEBAR RELAY METHODS
	 *****************************************************************************/
	public void showDefaultSidebar() {
		mSidebar.showDefaultSidebar();
	}
	public void showElementSidebar() {
		mSidebar.showElementContextBar(mScreen.getSelectedElement());
	}
	public void showCustomElementSidebar(){
		mSidebar.showCustomElementBar();
	}
	public void showTestSidebar() {
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK HACK HACK
		mSidebar.showTestBar();
	}
		
	
	/*****************************************************************************
	 * MISC METHODS
	 *****************************************************************************/
	
	private Bitmap createBitmapOfView(View theView) {
		Bitmap b = Bitmap.createBitmap(theView.getWidth(),
				theView.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		theView.draw(c);
		return b;
	}
	//-------------------------------------------------------------------------
	private ImageView createImageViewWithBitmap(Bitmap bitmap) {
		ImageView image = new ImageView(getApplicationContext());
		image.setImageBitmap(bitmap);
		return image;
	}
	//------------- Testing bitmap background ----------------
	private MAScreenElement createElement(ElementType type, Bitmap back) {
		MAScreenElement element = new MAScreenElement(getApplicationContext(), mScreen, type);
		element.setBackgroundDrawable(new BitmapDrawable(back));
		
		return element;
	}
	
	//-------------------------------------------------------------------------
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{ 
	   if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) { 
	       // Do your thing 
		   makeToast("ACHAL ROX MY SOX");
	       return true;
	   } else {
	       return super.onKeyDown(keyCode, event); 
	   }
	}
	
	
	//-------------------------------------------------------------------------
	public boolean isTesting() {
		return mIsTesting;
	}
	
	
	//-------------------------------------------------------------------------
	public void makeLogI(String format, Object... args) {
		Log.i("meta", String.format(format, args));
	}
	public void makeToast(String format, Object... args) {
		Toast.makeText(getApplicationContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}

}
