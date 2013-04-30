package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button mNewProjectButton;
	private Intent mIntent;
	private final int CODE_HOME = 1;
	private final double iPhoneScalar = 0.75; 
	private final static DisplayMetrics display = new DisplayMetrics();
	private final ArrayList<View> testScreens = new ArrayList<View>();
	
	private MyPagerAdapter mPageAdapter;
	
	
	private MAModel mModel;
	
	
	//-------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mModel = MAModel.getInstance();
//		mProjectImages = get
		
//		this.getWindowManager().getDefaultDisplay().getMetrics(display);
		RelativeLayout activityMain = (RelativeLayout)findViewById(R.id.background);
		
		RelativeLayout phone = (RelativeLayout)findViewById(R.id.phone);
//		
//		RelativeLayout droid = new RelativeLayout(this);
//		final int height = (int) (iPhoneScalar * display.heightPixels);
//		final int width = (int) (iPhoneScalar * display.widthPixels);
		final int height = 927;
		final int width = 550;
//		final int leftOffset = (int) ((1- iPhoneScalar) * display.heightPixels / 3);
//		final int topOffset = (int) ((1- iPhoneScalar) * display.widthPixels);
//		
//		final int droidID = this.getResources().getIdentifier("androidphone", "drawable", this.getPackageName());
//		final TextView metaApp = createMetaApp();
		
//		final TextView swipe = createSwipe();
		
//		RelativeLayout.LayoutParams iPhoneParams = new RelativeLayout.LayoutParams(width, height);
//		iPhoneParams.leftMargin = leftOffset;
//		iPhoneParams.topMargin = topOffset;
//		
//		droid.setLayoutParams(iPhoneParams);
//		droid.setBackgroundResource(droidID);
//		
		ViewPager screen = setupPager(width, height);
//		
		phone.addView(screen);
//		activityMain.addView(droid);
//		activityMain.addView(metaApp);
//		activityMain.addView(swipe);
//		activityMain.setBackgroundColor(Color.WHITE);
		
//		ImageView drd = new ImageView(this);
//		Drawable phone = getResources().getDrawable(R.drawable.androidphone);
//		
//		
//		RelativeLayout.LayoutParams phoneParams =
//				new RelativeLayout.LayoutParams(phone.getIntrinsicWidth()/2, phone.getIntrinsicHeight()/2);
////				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		phoneParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//		drd.setBackgroundResource(R.drawable.androidphone);
//		drd.setLayoutParams(phoneParams);
//		activityMain.addView(drd);
		
	}

	
	//-------------------------------------------------------------------------
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("ACACAC", String.format("ATTACHED. Size:%d", mModel.getNumProjects()));
		mPageAdapter.notifyDataSetChanged();
		
	}
	
	
	
	// -- START MyPagerAdapter Class --------------------------------------------
	private class MyPagerAdapter extends PagerAdapter {
		private ArrayList<MAProject> views;
		public MyPagerAdapter(ArrayList<MAProject> views) {
			this.views = views;
		}
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			final ImageView currentView;
			if (position == 0 ){
				currentView = getFolderView();
			} else {
				currentView = createImageViewFromView(views.get(position-1).getFirstScreen());
				currentView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// startProjectAtIndex(position-1);
						Intent intent = new Intent(MainActivity.this, DevelopmentActivity.class);
						intent.putExtra("PROJECT", position-1);
						startActivityForResult(intent, CODE_HOME);
					}
				});
			}
			container.addView(currentView);
			return currentView;
		}
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
		@Override
		public int getCount() {
			return views.size()+1;
		}
		@Override 
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((ImageView) object);
		}
	}
	// -- END MyPagerAdapter Class ------------------------------------------
	
	
	//-------------------------------------------------------------------------
	private ImageView getFolderView() {
		final int newfolder = R.drawable.newfolder;
		ImageView folderView = new ImageView(this);
		folderView.setScaleType(ScaleType.FIT_START);
		folderView.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		folderView.setBackgroundResource(newfolder);
		folderView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, DevelopmentActivity.class);
				startActivityForResult(intent, CODE_HOME);
			}
		});
		return folderView;
	}
	
	//-------------------------------------------------------------------------
//	private ArrayList<ImageView> getProjectImages() {
//		ArrayList<ImageView> images = new ArrayList<ImageView>();
//		for (int i=0; i<mModel.getNumProjects(); i++) {
//			ImageView temp = MAModel.createImageViewWithBitmapForContext(
//					MAModel.createBitmapOfView(mModel.getProject(i).getFirstScreen()), this);
//			images.add(temp);
//		}
//		
//		return images;
//	}
	
//	private void populateScreens(int width, int height) {
////		final int newfolder = this.getResources().getIdentifier("newfolder", "drawable", this.getPackageName());
//		final int newfolder = R.drawable.newfolder;
//		ImageView test1 = new ImageView(this);
//		test1.setScaleType(ScaleType.FIT_START);
//		test1.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
//		test1.setBackgroundResource(newfolder);
//		test1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Intent intent = new Intent(MainActivity.this, DevelopmentActivity.class);
//				startActivityForResult(intent, CODE_HOME);
//			}
//		});
//		
////		ImageView test2 = new ImageView(this);
////		test2.setLayoutParams(new RelativeLayout.LayoutParams(
////				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
////		test2.setBackgroundColor(Color.GREEN);
//		
//		MAProject proj;
//		ImageView temp = new ImageView(this);
//		for (int i=0; i<mModel.getNumProjects(); i++) {
//			proj = mModel.getProject(i);
//			
//			temp.setLayoutParams(new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT));
//			temp.setImageBitmap(DevelopmentActivity.createBitmapOfView(proj.getFirstScreen()));
//		}
//		
//		ImageView test3 = new ImageView(this);
//		test3.setLayoutParams(new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//		test3.setBackgroundColor(Color.YELLOW);	
//		
//		testScreens.add(test1);
////		testScreens.add(test2);
//		testScreens.add(test3);
//	}
	
//	public TextView createMetaApp() {
//		TextView metaApp = new TextView(this);
//		RelativeLayout.LayoutParams metaAppParams = new RelativeLayout.LayoutParams(600, 250);
//		metaAppParams.leftMargin = 125;
//		metaAppParams.topMargin = 35;
//		metaApp.setTextSize(100);
//		metaApp.setTextColor(Color.BLUE);
//		metaApp.setText("MetaApp");
//		metaApp.setLayoutParams(metaAppParams);
//		return metaApp;
//	}
	
//	public TextView createSwipe() {
//		TextView swipe = new TextView(this);
//		RelativeLayout.LayoutParams swipeParams = new RelativeLayout.LayoutParams(800, 100);
//		swipeParams.leftMargin = 140;
//		swipeParams.topMargin = 1100;
//		swipe.setTextSize(30);
//		swipe.setTextColor(Color.BLUE);
//		swipe.setText("Swipe above to view recent prototypes.");
//		swipe.setLayoutParams(swipeParams);
//		return swipe;
//	}
	
	//-------------------------------------------------------------------------
	public ViewPager setupPager(final int width, final int height) {
//		populateScreens(width, height);
		final ViewPager screen = new ViewPager(this);
//		mPageAdapter = new MyPagerAdapter(testScreens);
		mPageAdapter = new MyPagerAdapter(mModel.getProjects());
		screen.setAdapter(mPageAdapter);
		final RelativeLayout.LayoutParams screenParams =
				new RelativeLayout.LayoutParams(width - 130, (int) (height *iPhoneScalar)-15);
		screenParams.leftMargin = 58;
		screenParams.topMargin = 108;
		screen.setLayoutParams(screenParams);
		screen.setBackgroundColor(Color.BLUE);
		return screen;
	}
	//-------------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	//-------------------------------------------------------------------------
	public void makeToast(String format, Object... args) {
		Toast.makeText(getApplicationContext(),
				String.format(format, args), Toast.LENGTH_SHORT).show();
	}
	
	//-------------------------------------------------------------------------
	private ImageView createImageViewFromView(View v) {
		return MAModel.createImageViewWithBitmapForContext(
				MAModel.createBitmapOfView(v), this);
	}
	
	
	//-------------------------------------------------------------------------
//	public static float distance(float x1, float y1, float x2, float y2) {
//		float dist  = 0;
//		dist = (float) Math.sqrt(Math.pow(Math.abs(x2-x1), 2) + Math.pow(Math.abs(y2-y1), 2));
////		dist = (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
////		Math.sqrt(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2));
//		return dist;
//	}

}
