package edu.berkeley.cs160.onesies.metaapp;

import android.view.View;

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
public class MASidebar {

	private View	mSidebar;
	
	public MASidebar() {
		
	}
	public MASidebar(View sidebar) {
		mSidebar = sidebar;
	}
	

}
