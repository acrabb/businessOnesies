package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MAScreensAdapter extends ArrayAdapter<MAScreen> {
	
	private Context				mContext;
	private ArrayList<MAScreen> mScreens;
	private MAScreen			mScreen;
	private int					mLayoutId = R.layout.overview_item;
	
	private LayoutInflater		mLayoutInflater;
	
	public MAScreensAdapter(Context context, ArrayList<MAScreen> screens) {
		super(context, R.layout.overview_item, screens);
		mContext = context;
		mScreens = screens;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View		listItem;
		TextView	textView;
		ImageView	imageView ;
		
		// If creating new cell
		if (convertView == null) {
			listItem = mLayoutInflater.inflate(mLayoutId, parent, false);
		} else {
			listItem = convertView;
		}
		
		// Populate cell
		mScreen = mScreens.get(position);
//		imageView = DevelopmentActivity.createImageViewWithBitmapForContext(
//				DevelopmentActivity.createBitmapOfView(mScreen), getContext());
		imageView = (ImageView) listItem.findViewById(R.id.item_image);
		imageView.setImageBitmap(DevelopmentActivity.createBitmapOfView(mScreen));
//		imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300*16/10));
		textView = (TextView) listItem.findViewById(R.id.item_text);
		textView.setText(mScreen.getName());
//		textView.setVisibility(View.GONE);
		
		return listItem;
	}
	
}
