package edu.berkeley.cs160.onesies.metaapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class DevelopmentActivity extends Activity {

	// UI Objects-------------------------------
	private MAScreen			mScreen;
	private RelativeLayout		mDevRelLayout;
	private MASidebar			mSidebar;
	private PopupWindow			mPopupWindow;
	private PopupMenu			mPopupMenu;
	private GridLayout			mElementsGridView;

	private View 				mShapesContentView;
	private View 				mElementsContentView;
//	private View 				mLinkContentView;
	
	// Non-UI Objects--------------------------
	private LayoutInflater		mLayoutInflater;
	private Resources			mResources;
	private MAProject			mProject;
	
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
		
		createNewProject();
	}
	
	private void createNewProject() {
		//TODO
		mProject = new MAProject("Project1");
		String name = mProject.getNextDefaultScreenName();
		mProject.addScreenToProject(name, mScreen);
		mScreen.setName(name);
	}
	
	private void showScreenWithName(String name) {
		MAScreenElement selected = mScreen.getSelectedElement();
		if(selected != null) {
			selected.deselect();
		}
		MAScreen newScreen = mProject.getScreenWithName(name);
		mDevRelLayout.removeView(mScreen);
		mDevRelLayout.addView(newScreen,mScreen.getLayoutParams());
		showDefaultSidebar();
//		String newName = mProject.getNextDefaultScreenName();
//		newScreen.setName(newName);
//		mProject.addScreenToProject(newName, newScreen);
		mScreen = newScreen;
	}
	
	
	
	/*****************************************************************************
	 * MASIDEBAR "CALLBACK" METHODS
	 *****************************************************************************/
	
	public void showShapesPopup(View button) {
		mShapesContentView = mLayoutInflater.inflate(R.layout.shape_popup, null);
		mPopupWindow = new PopupWindow(mShapesContentView,
				(int) mResources.getDimension(R.dimen.shapePopupWidth),
				(int) mResources.getDimension(R.dimen.shapePopupHeight), false);
		/*
		 * setBackgroundDrawable MUST MUST MUST be called in order for the popup to be
		 * dismissed properly
		 */
		
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.showAsDropDown(button, 0, 0);
	}
	
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
	
	public void showLinkPopup(View button) {
		mPopupMenu = new PopupMenu(getApplicationContext(), button);
		
		mPopupMenu.inflate(R.menu.link_menu);
		mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch(item.getItemId()) {
					case R.id.link_menu_new:
						onLinkToNewScreenSelected();
						break;
					case R.id.link_menu_existing:
						onLinkToExistingScreenSelected();
						break;
					default:
				}
				return false;
			}
		});
		mPopupMenu.show();
	}
	public void showMenuPopup(View button) {
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		//TODO HACK HACK HACK HACK HACK HACK HACK HACK HACK
		final Dialog dia = new Dialog(this);
		View v = mLayoutInflater.inflate(R.layout.overview_temp, null, false);
		ListView screenList = (ListView) v.findViewById(android.R.id.list);
		
//		ArrayList<MAScreen> sorted;
//		Collections.sort(mProject.getScreens(), new Comparator<MAScreen>() {
//			@Override
//			public int compare(MAScreen one, MAScreen other) {
//				// TODO Auto-generated method stub
//				return one.getName().compareToIgnoreCase(other.getName());
//			}
//		});
		
		screenList.setAdapter(new MAScreensAdapter(getApplicationContext(), mProject.getScreens()));
		
		screenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				makeToast("Clicked item at %d", position);
				showScreenWithName(((MAScreen) parent.getAdapter().getItem(position)).getName());
				dia.dismiss();
			}
		});
		
		
		dia.setContentView(v);
		dia.setTitle("Select Screen");
		dia.show();
		
		/*/
		mPopupMenu = new PopupMenu(getApplicationContext(), button);
		
		mPopupMenu.inflate(R.menu.dev_menu);
		mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch(item.getItemId()) {
//					case R.id.link_menu_new:
//						onLinkToNewScreenSelected();
//						break;
//					case R.id.link_menu_existing:
//						onLinkToExistingScreenSelected();
//						break;
					default:
				}
				return false;
			}
		});
		mPopupMenu.show();
		/**/
	}
	
	
	
	/*****************************************************************************
	 * POPUP "CALLBACK" METHODS
	 *****************************************************************************/
	private void addUIElement(View element) {
		// Take the view, and add it to the MAScreen object.
		Bitmap map = createBitmapOfView(element);
		ElementType type;
		if (element instanceof Button) {
			type = ElementType.BUTTON;
		} else { //(element instanceof TextView) {
			type = ElementType.TEXT_LABEL; 
		}
		MAScreenElement clone = createElement(type, map);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				200, 100);
		clone.setLayoutParams(params);
		mScreen.addView(clone);
	}
	private void onLinkToNewScreenSelected() {
		// TODO MODULARIZE
		// Create new Screen
		MAScreen newScreen = (MAScreen) mLayoutInflater.inflate(R.layout.ma_screen, null);
		newScreen.setmDevelopmentActivity(this);
		String newName = mProject.getNextDefaultScreenName();
		newScreen.setName(newName);
		mProject.addScreenToProject(newName, newScreen);
		
		showScreenWithName(newName);
		
		// Actually link the button to new screen
		//TODO LINK BUTTON TO SCREEN
		//TODO LINK BUTTON TO SCREEN
		//TODO LINK BUTTON TO SCREEN
		//TODO LINK BUTTON TO SCREEN
		//TODO LINK BUTTON TO SCREEN
	}
	private void onLinkToExistingScreenSelected() {
		makeToast("LINK TO EXISTING SCREEN");
		
	}
	
	
	/*****************************************************************************
	 * SIDEBAR RELAY METHODS
	 *****************************************************************************/
	public void showDefaultSidebar() {
		hideElementSidebar();
	}
	public void showElementSidebar(View v) {
		mSidebar.showElementContextBar();
	}
	public void hideElementSidebar() {
		mSidebar.hideElementContextBar();
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
	
	public void makeToast(String format, Object... args) {
		Toast.makeText(getApplicationContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
}
