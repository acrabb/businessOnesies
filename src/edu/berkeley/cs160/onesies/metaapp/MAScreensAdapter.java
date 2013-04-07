package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MAScreensAdapter extends ArrayAdapter<MAScreen> {
	
	private Context				mContext;
	private ArrayList<MAScreen> mScreens;
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
		// If creating new cell
		if (convertView == null) {
			listItem = mLayoutInflater.inflate(mLayoutId, parent, false);
		} else {
			listItem = convertView;
		}
		// Populate cell
		textView = (TextView) listItem.findViewById(R.id.item_text);
		textView.setText(mScreens.get(position).getName());
		
		return listItem;
	}
}
