package edu.berkeley.cs160.onesies.metaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MAButton;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MACheckbox;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MARadioButton;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MASlider;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MATextLabel;
import edu.berkeley.cs160.onesies.metaapp.MAElements.MAToggle;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MAOval;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MARectangle;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MAStar;
import edu.berkeley.cs160.onesies.metaapp.MAShapes.MATriangle;

public class DevelopmentActivity extends Activity {

	// UI Objects-------------------------------
	private MAScreen			mScreen;
	private RelativeLayout		mDevRelLayout;
	private MASidebar			mSidebar;
	private PopupWindow			mPopupWindow;
	private PopupMenu			mMenuPopupMenu;
	private PopupMenu			mLinkPopupMenu;
	private GridLayout			mElementsGridView;
	private GridLayout			mShapesGridView;
	private Button 				mSketchButton;
	private MAScreenElement		mButtonToLink;
	private View 				mAddContentView;
	private MACanvas			mSketchCanvas;
	private ImageView			mHelpImage;
	
	// Non-UI Objects--------------------------
	private MAModel				mModel;
	private LayoutInflater		mLayoutInflater;
	private Resources			mResources;
	protected static MAProject	mProject;
	private boolean				mInSketchZone = false;
	private boolean				mIsTesting = false;
	private boolean				mPopupIsShowing = false;
	private Intent				mIntent;
	
	private final int			SHOW_TAPPED = 1;
	private final int			LINK_TAPPED = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the menu; this adds items to the action bar if it is present.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.development_main);

		// Get model
		mModel = MAModel.getInstance();

		mLayoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResources = getResources();
		mDevRelLayout = (RelativeLayout) findViewById(R.id.developmentRelative);

		mHelpImage = (ImageView) findViewById(R.id.helpImage);
		mHelpImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onHelpTapped();
			}
		});
		
		// Set up sidebar
		mSidebar = (MASidebar) findViewById(R.id.sidebar);
		mSidebar.setUp(this);

		// Get menu for manipulation
		mMenuPopupMenu = new PopupMenu(getApplicationContext(),
				mSidebar.findViewById(R.id.menuButton));
		mMenuPopupMenu.inflate(R.menu.dev_menu);
		// Set up screen
		mScreen = (MAScreen) findViewById(R.id.screen);
		mScreen.setmDevelopmentActivity(this);

		// Get project if any
		mIntent = getIntent();
		int projIndex = mIntent.getIntExtra("PROJECT", -1);
		if (projIndex != -1) {
			loadProject(projIndex);
		} else {
			// Set up project
			createNewProject();
			onHelpTapped();
		}
		mModel.clearHistory();
	}
	
	// -------------------------------------------------------------------------
	private void loadProject(int projIndex) {
		mProject = mModel.getProject(projIndex);
		showScreenWithName(mProject.getFirstScreen().getName());
		for (MAScreen s : mProject.getScreens()) {
			s.setmDevelopmentActivity(this);
		}
	}

	// -------------------------------------------------------------------------
	private void createNewProject() {
		// TODO
		mProject = new MAProject(String.format("Project %d",
				mModel.getNumProjects() + 1));
		mModel.addProject(mProject);

		String name = mProject.getNextDefaultScreenName();
		mProject.addScreenToProject(name, mScreen);
		mScreen.setName(name);
		mProject.addFirstScreen(mScreen);
		showScreenWithName(name);
		makeToast("Screen '%s' Created.", name);
	}

	// -------------------------------------------------------------------------
	private void showScreenWithName(String name) {
		showScreenWithName(name, false);
	}

	// -------------------------------------------------------------------------
	private void showScreenWithName(String name, boolean addToHistory) {
		// Deselect any current selection.
		MAScreenElement selected = mScreen.getSelectedElement();
		if (selected != null) {
			// FIXME What is this doing here??
			// FIXME What is this doing here??
			// FIXME What is this doing here??
			// FIXME What is this doing here??
			// FIXME What is this doing here??
			selected.setScreenLinkedTo(name);
			mScreen.deselectAll();
		}
		MAScreen newScreen = mProject.getScreenWithName(name);
		
		mDevRelLayout.removeView(mScreen);
		mDevRelLayout.addView(newScreen, mScreen.getLayoutParams());
		if (!mIsTesting) {
			showDefaultSidebar();
		}
		if (addToHistory) {
			mModel.addToHistory(mScreen);
		}
		mScreen = newScreen;
	}

	// -------------------------------------------------------------------------
	@Override
	public void finish() {
		// Book keeping
		mScreen.deselectAll();
		mDevRelLayout.removeView(mScreen);

		Intent intent = new Intent(DevelopmentActivity.this, MainActivity.class);
		int ind = mModel.getProjects().indexOf(mProject);
		intent.putExtra("PROJECT_INDEX", ind);
		makeLogI("PUTTING INDEX %d", ind);
		if (getParent() == null) {
			setResult(Activity.RESULT_OK, intent);
		} else {
			getParent().setResult(Activity.RESULT_OK, intent);
		}
		super.finish();
	}

	/*****************************************************************************
	 * MASIDEBAR "CALLBACK" METHODS
	 *****************************************************************************/
	// -------------------------------------------------------------------------
	public void onHomeButtonTapped() {
		finish();
	}

	// -------------------------------------------------------------------------
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

	// -------------------------------------------------------------------------
	public void hideSketchZone() {
		mDevRelLayout.removeView(mSketchCanvas);
		showDefaultSidebar();
		mInSketchZone = false;
	}

	// -------------------------------------------------------------------------
	public void onAddSketchTapped() {
		Bitmap map = mSketchCanvas.getSketch();
		if (map != null) {
			MAScreenElement custom = (MAScreenElement) mLayoutInflater.inflate(
					R.layout.e_default, null);
			setUpCustomElement(custom, map);
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
					map.getWidth(), map.getHeight());
			p.leftMargin = mSketchCanvas.getLeftMargin();
			p.topMargin = mSketchCanvas.getTopMargin();
			custom.setLayoutParams(p);
			mScreen.updateModel(custom, UndoStatus.ADD);
			mScreen.addView(custom);
		}
		hideSketchZone();
	}

	// -------------------------------------------------------------------------
	public void showAddPopup(View button) {
		// Custom check needed to dismiss popup on "+" button click again.
		if (mPopupIsShowing) {
			mPopupWindow.dismiss();
			return;
		}
		mAddContentView = mLayoutInflater.inflate(R.layout.add_popup, null);
		mPopupWindow = new PopupWindow(mAddContentView,
				(int) mResources.getDimension(R.dimen.addPopupWidth),
				(int) mResources.getDimension(R.dimen.addPopupHeight), false);

		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup
		 * to be dismissed properly
		 */
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.popup_bg));
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				mPopupIsShowing = false;
			}
		});
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.showAsDropDown(button, 0, 0);
		mPopupIsShowing = true;
		setupElements();
		setupShapes();
		setupSketch();
	}

	// -------------------------------------------------------------------------
	private void setupElements() {
		mElementsGridView = (GridLayout) mAddContentView
				.findViewById(R.id.elements_popup);
		int numChildren = mElementsGridView.getChildCount();
		View v;
		for (int i = 0; i < numChildren; i++) {
			v = mElementsGridView.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addUIElement(v);
					mPopupWindow.dismiss();
				}
			});
		}
			// lolol just make random buttons man
		addElementsToPopup(new MAButton(getApplicationContext(),
				(MAScreen) null), R.id.ma_button);
		addElementsToPopup(new MASlider(getApplicationContext(),
				(MAScreen) null), R.id.ma_slider);
		addElementsToPopup(new MACheckbox(getApplicationContext(),
				(MAScreen) null), R.id.ma_checkbox);
		addElementsToPopup(new MARadioButton(getApplicationContext(),
				(MAScreen) null), R.id.ma_radiobutton);
		addElementsToPopup(new MAToggle(getApplicationContext(),
				(MAScreen) null), R.id.ma_toggle);
	}

	// -------------------------------------------------------------------------
	private void setupShapes() {

		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup
		 * to be dismissed properly
		 */
		mShapesGridView = (GridLayout) mAddContentView.findViewById(R.id.shapesGrid);
		View v;
		int numChildren = mShapesGridView.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			v = mShapesGridView.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addShapeElement(v);
					mPopupWindow.dismiss();
				}
			});
		}

		addShapesToPopup(new MATriangle(getApplicationContext(),
				(MAScreen) null), R.id.ma_triangle);
		addShapesToPopup(new MARectangle(getApplicationContext(),
				(MAScreen) null), R.id.ma_rectangle);
		addShapesToPopup(new MAOval(getApplicationContext(), (MAScreen) null),
				R.id.ma_oval);
		addShapesToPopup(new MAStar(getApplicationContext(), (MAScreen) null),
				R.id.ma_star);
	}

	// -------------------------------------------------------------------------
	private void setupSketch() {
		mSketchButton = (Button) mAddContentView
				.findViewById(R.id.sketchButton);
		mSketchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showSketchZone(arg0);
				mPopupWindow.dismiss();
			}
		});
	}

	// -------------------------------------------------------------------------
	private void addShapesToPopup(MAScreenElement elem, int id) {
		ImageView imageView = (ImageView) mShapesGridView.findViewById(id);
		Bitmap viewBitmap = DevelopmentActivity.createBitmapOfView(elem,
				imageView.getLayoutParams().width,
				imageView.getLayoutParams().height);
		imageView.setImageBitmap(viewBitmap);
	}

	// -------------------------------------------------------------------------
	private void addElementsToPopup(MAScreenElement elem, int id) {
		ImageView imageView = (ImageView) mElementsGridView.findViewById(id);
		Bitmap viewBitmap = DevelopmentActivity.createBitmapOfView(elem,
				imageView.getLayoutParams().width,
				imageView.getLayoutParams().height);
		imageView.setImageBitmap(viewBitmap);
	}

	// -------------------------------------------------------------------------
	public void showLinkPopup(View linkButton) {
		mLinkPopupMenu = new PopupMenu(getApplicationContext(), linkButton);
		mLinkPopupMenu.inflate(R.menu.link_menu);
		mLinkPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				MAScreenElement element = mScreen.getSelectedElement();
				switch(item.getItemId()) {
					case R.id.link_menu_new:
						onLinkToNewScreenSelected(element);
						break;
					case R.id.link_menu_existing:
						onLinkToExistingScreenSelected(element);
						break;
					default:
				}
				return true;
			}
		});
		mLinkPopupMenu.show();
	}

	// -------------------------------------------------------------------------
	public void onDeleteButtonTapped(View deleteButton) {
		mScreen.deleteSelected();
		showDefaultSidebar();
	}

	// -------------------------------------------------------------------------
	public void onElementForwardTapped() {
		mScreen.getSelectedElement().bringToFront();
		mScreen.invalidate();
	}

	// -------------------------------------------------------------------------
	String m_Text = "";

	public void onEditTextTapped() {
		if (mScreen.getSelectedElement() == null) {
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Title");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected
		input.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_NORMAL);
		input.setText(mScreen.getSelectedElement().getText());
		input.setSelection(input.getText().length());
		mScreen.getSelectedElement().invalidate();
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				m_Text = input.getText().toString();
				mScreen.updateModel(mScreen.getSelectedElement(),
						UndoStatus.TEXT, mScreen.getSelectedElement().getText());
				((MAScreenElement) mScreen.getSelectedElement())
						.setText(m_Text);
				mScreen.getSelectedElement().invalidate();
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	// -------------------------------------------------------------------------
	public void onUndoTapped() {
		if (!mIsTesting) {
			mScreen.handleUndo();
		}
	}
	// -------------------------------------------------------------------------
	public void onHelpTapped() {
		if (!mIsTesting) {
			if (mHelpImage.isShown()) {
				mHelpImage.setVisibility(View.INVISIBLE);
			} else {
				mHelpImage.bringToFront();
				mHelpImage.setVisibility(View.VISIBLE);
			}
		}
	}

	/*****************************************************************************
	 * POPUP "CALLBACK" METHODS
	 *****************************************************************************/
	private void addUIElement(View element) {
		addUIElement(element, 0, 0);
	}
	//-------------------------------------------------------------------------
	private void addUIElement(View element, float x, float y) {
		// Create the view, and add it to the MAScreen object.
		// TODO HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK
		// TODO HACK HACK HACK HACK HACK HACK

		MAScreenElement newElement;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				element.getWidth(), element.getHeight());
		switch (element.getId()) {
		case R.id.ma_text_label:
			newElement = new MATextLabel(getApplicationContext(), mScreen);
			newElement.setLayoutParams(params);
			break;
		case R.id.ma_slider:
			newElement = new MASlider(getApplicationContext(), mScreen);
			params.height = newElement.getMaxHeight();
			newElement.setLayoutParams(params);
			break;
		case R.id.ma_checkbox:
			newElement = new MACheckbox(getApplicationContext(), mScreen);
			params.height = newElement.getMaxHeight();
			newElement.setLayoutParams(params);
			break;
		case R.id.ma_radiobutton:
			newElement = new MARadioButton(getApplicationContext(), mScreen);
			params.height = newElement.getMaxHeight();
			newElement.setLayoutParams(params);
			break;
		case R.id.ma_toggle:
			newElement = new MAToggle(getApplicationContext(), mScreen);
			newElement.setLayoutParams(params);
			break;
		case R.id.ma_button:
		default:
			// lolol just make random buttons man
			newElement = (MAScreenElement) mLayoutInflater.inflate(
					R.layout.e_button, mScreen, false);
//			params = (RelativeLayout.LayoutParams) newElement.getLayoutParams();
//			params.topMargin = (int) y;
//			params.leftMargin = (int) x;
			newElement.setmScreen(mScreen);
			break;
		}

		mScreen.updateModel((MAScreenElement) newElement, UndoStatus.ADD);
		mScreen.addView(newElement);
	}

	// -------------------------------------------------------------------------
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
		case R.id.ma_star:
			clone = new MAStar(getApplicationContext(), mScreen);
			break;
		default:
			clone = new MARectangle(getApplicationContext(), mScreen);
			break;
		}

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				200, 200);
		clone.setLayoutParams(params);

		mScreen.updateModel(clone, UndoStatus.ADD);
		mScreen.addView(clone);
	}
	
	//-------------------------------------------------------------------------
	private void onLinkToNewScreenSelected(MAScreenElement button) {
		// TODO MODULARIZE
		// Create new Screen
		MAScreen newScreen = (MAScreen) mLayoutInflater.inflate(
				R.layout.ma_screen, null);
		newScreen.setmDevelopmentActivity(this);
		String newName = mProject.getNextDefaultScreenName();
		newScreen.setName(newName);
		mProject.addScreenToProject(newName, newScreen);
		if (button != null) {
			button.setDestinationScreen(newScreen);
		}

		showScreenWithName(newName, true);

		// Actually link the button to new screen
		button.setDestinationScreen(newScreen);
		makeToast("Screen '%s' Created.", newName);
	}
	//-------------------------------------------------------------------------
	private void onLinkToExistingScreenSelected(MAScreenElement button) {
//		makeToast("LINK TO EXISTING SCREEN");
		mButtonToLink = button;
		showBigPictureForCode(LINK_TAPPED);
	}

	// -------------------------------------------------------------------------
	public void showMenuPopup(View button) {
		mMenuPopupMenu.getMenu().findItem(R.id.dev_menu_delete_screen)
				.setVisible(false);
		mMenuPopupMenu.getMenu().findItem(R.id.dev_menu_new_screen)
				.setVisible(false);

		mMenuPopupMenu
				.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case R.id.dev_menu_big_picture:
							onBigPictureSelected();
							break;
						case R.id.dev_menu_delete_screen:
							// onDeleteScreenSelected();
							break;
						case R.id.dev_menu_new_screen:
							// onNewScreenSelected();
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
	}

	/*****************************************************************************
	 * MENU "CALLBACK" METHODS
	 *****************************************************************************/
	// -------------------------------------------------------------------------
	public void onTestModeTapped() {
		if (!mIsTesting) {
			mIsTesting = true;
			makeLogI(">>> Test mode tapped");
			mScreen.deselectAll();
			showScreenWithName(mProject.getFirstScreen().getName());
			showTestSidebar();
			mMenuPopupMenu.getMenu().findItem(R.id.dev_menu_test)
					.setVisible(false);
			mMenuPopupMenu.getMenu().findItem(R.id.dev_menu_test_exit)
					.setVisible(true);
		}
	}

	// -------------------------------------------------------------------------
	public void onExitTestModeTapped() {
		if (mIsTesting) {
			mIsTesting = false;
			makeLogI(">>> Exit test mode tapped");
			showScreenWithName(mScreen.getName());
			showDefaultSidebar();
			mMenuPopupMenu.getMenu().findItem(R.id.dev_menu_test)
					.setVisible(true);
			mMenuPopupMenu.getMenu().findItem(R.id.dev_menu_test_exit)
					.setVisible(false);
		}
	}

	// -------------------------------------------------------------------------
	/**
	 * Test for passing custom objects through intents using serializable
	 * bundles
	 */
	public String getActivityString() {
		return "Development";
	}

	// -------------------------------------------------------------------------
	private void onBigPictureSelected() {
		mScreen.deselectAll();
		showBigPictureForCode(SHOW_TAPPED);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// When BigPicture finishes for showing the tapped screen.
		case SHOW_TAPPED:
			if (resultCode == RESULT_OK) {
				int i = data.getIntExtra("INDEX", -1);
				Log.d("ACACAC", "SELECTED:" + i);
				showScreenWithName(mProject.getScreens().get(i).getName(), true);
			}
			break;
		// When BigPicture finishes for linking a button to the tapped screen.
		case LINK_TAPPED:
			if (resultCode == RESULT_OK) {
				int i = data.getIntExtra("INDEX", -1);
				Log.d("ACACAC", "SELECTED:" + i);
				mButtonToLink
						.setDestinationScreen(mProject.getScreens().get(i));
			}
			break;
		default:
		}
	}

	// -------------------------------------------------------------------------
	private void showBigPictureForCode(int requestCode) {
		Intent intent = new Intent(DevelopmentActivity.this, BigPicture.class);
		intent.putExtra("PROJECT_INDEX", mModel.getProjects().indexOf(mProject));
		intent.putExtra("RETURN_TAPPED", true);
		mScreen.deselectAll();
		startActivityForResult(intent, requestCode);
	}

	/*****************************************************************************
	 * TESTING MODE METHODS
	 *****************************************************************************/
	//-------------------------------------------------------------------------
	public void onButtonTappedInTest(MAScreenElement button) {
		if (button.getDestinationScreen() != null) {
			showScreenWithName(button.getDestinationScreen().getName());
			showTestSidebar(); // HANDLED IN ONTESTBUTTONTAPPED
		}
	}

	/*****************************************************************************
	 * SIDEBAR RELAY METHODS
	 *****************************************************************************/
	// -------------------------------------------------------------------------
	public void showDefaultSidebar() {
		mSidebar.showDefaultSidebar();
	}

	// -------------------------------------------------------------------------
	public void showElementSidebar() {
		mSidebar.showElementContextBar(mScreen.getSelectedElement());
	}

	// -------------------------------------------------------------------------
	public void showCustomElementSidebar() {
		mSidebar.showCustomElementBar();
	}

	// -------------------------------------------------------------------------
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
	// -------------------------------------------------------------------------
//	public static Bitmap createBitmapOfView(View theView) {
//		theView.setDrawingCacheEnabled(true);
//		theView.buildDrawingCache();
//		Bitmap a = Bitmap.createBitmap(theView.getDrawingCache());
//		theView.setDrawingCacheEnabled(false);
//		return a;
//	}

	// -------------------------------------------------------------------------
	public static Bitmap createBitmapOfView(View theView, int width, int height) {
		theView.setDrawingCacheEnabled(true);
		theView.layout(0, 0, width, height);
		theView.buildDrawingCache();
		Bitmap a = Bitmap.createBitmap(theView.getDrawingCache());
		theView.setDrawingCacheEnabled(false);
		return a;
	}

	// -------------------------------------------------------------------------
	public static ImageView createImageViewWithBitmapForContext(Bitmap bitmap,
			Context context) {
		ImageView image = new ImageView(context);
		image.setImageBitmap(bitmap);
		return image;
	}

	// ------------- Testing bitmap background ----------------
	// private MAScreenElement createElement(ElementType type, Bitmap back) {
	// MAScreenElement element = new MAScreenElement(getApplicationContext(),
	// mScreen, type);
	// element.setBackgroundDrawable(new BitmapDrawable(back));
	//
	// return element;
	// }
	private void setUpCustomElement(MAScreenElement e, Bitmap back) {
		e.setmScreen(mScreen);
		e.setmType(ElementType.CUSTOM);
		e.setBackgroundDrawable(new BitmapDrawable(back));
	}

	// -------------------------------------------------------------------------
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			// Do your thing
			makeToast("...champagne?");
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// -------------------------------------------------------------------------
	public boolean isTesting() {
		return mIsTesting;
	}

	// -------------------------------------------------------------------------
	@Override
	public void onBackPressed() {
		// Do nothing.
		// onHomeButtonTapped();
		if (mHelpImage.isShown()) {
			onHelpTapped();
			return;
		}
		MAScreen s = mModel.getFromHistory();
		if (s != null) {
			showScreenWithName(s.getName());
		} else if (isTesting()) {
			onExitTestModeTapped();
		} else {
			makeToast("History is empty...");
		}
	}

	// -------------------------------------------------------------------------
	public void makeLogI(String format, Object... args) {
		Log.i("meta", String.format(format, args));
	}

	// -------------------------------------------------------------------------
	public void makeToast(String format, Object... args) {
		Toast.makeText(getApplicationContext(), String.format(format, args),
				Toast.LENGTH_SHORT).show();
	}

}
