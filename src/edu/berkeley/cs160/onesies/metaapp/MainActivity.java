package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final int CODE_HOME = 1;
	private final double iPhoneScalar = 0.75; 
	private MyPagerAdapter mPageAdapter;
	private MAModel mModel;
	
	
	//-------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mModel = MAModel.getInstance();
		RelativeLayout phone = (RelativeLayout)findViewById(R.id.phone);
		final int height = 927;
		final int width = 550;
		ViewPager screen = setupPager(width, height);
		phone.addView(screen);
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
			final TextView text = new TextView(getApplicationContext());
			if (position == 0 ){
				currentView = getNewProjectView();
			} else {
				text.setText(views.get(position-1).getName());
				text.setTextColor(getResources().getColor(R.color.black));
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
			container.addView(text);
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
	boolean dragged = false;
	float mLastX;
	float mLastY;
	private ImageView getNewProjectView() {
		final int newfolder = R.drawable.new_project;
		ImageView folderView = new ImageView(this);
		folderView.setScaleType(ScaleType.FIT_START);
		folderView.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		folderView.setBackgroundResource(newfolder);
		
		/**/
		folderView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, DevelopmentActivity.class);
				startActivityForResult(intent, CODE_HOME);
			}
		});
		/**/
		return folderView;
	}
	
	
	//-------------------------------------------------------------------------
	public ViewPager setupPager(final int width, final int height) {
		final ViewPager screen = new ViewPager(this);
		mPageAdapter = new MyPagerAdapter(mModel.getProjects());
		screen.setAdapter(mPageAdapter);
		final RelativeLayout.LayoutParams screenParams =
				new RelativeLayout.LayoutParams(width - 130, (int) (height *iPhoneScalar)-15);
		screenParams.leftMargin = 58;
		screenParams.topMargin = 108;
		screen.setLayoutParams(screenParams);
		screen.setBackgroundColor(Color.BLACK);
		return screen;
	}
	//-------------------------------------------------------------------------
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
	
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
}
