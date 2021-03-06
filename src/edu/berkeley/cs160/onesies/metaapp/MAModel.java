/**
 * 
 */
package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;
import java.util.Stack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * @author andre
 *
 */
public class MAModel {

	private static MAModel			instance = null;
	public ArrayList<MAProject>		mProjects = null;
	private MAProject				mCurrentProject = null;
	private Stack<MAScreen> 		mHistory;
	
	//-------------------------------------------------------------------------
	private MAModel() {
		mProjects = new ArrayList<MAProject>();
		mHistory = new Stack<MAScreen>();
	}
	
	//-------------------------------------------------------------------------
	public static MAModel getInstance() {
		if (instance == null) {
			instance = new MAModel();
		}
		return instance;
	}

	//-------------------------------------------------------------------------
//	public ArrayList<MAProject> getmProjects() {
//		return mProjects;
//	}
	
	
	//-------------------------------------------------------------------------
	public void addToHistory(MAScreen s) {
		mHistory.push(s);
	}
	//-------------------------------------------------------------------------
	private MAScreen getFromHistory() {
		if (mHistory.isEmpty()) {
			return null;
		} else {
			return mHistory.pop();
		}
	}
	public MAScreen getScreenFromHistory() {
		MAScreen retval = getFromHistory();
		// If the history is empty, return;
		if (retval == null) {
			return null;
		}
		// If the screen has been deleted, try the next screen.
		if (mCurrentProject.getScreenWithName(retval.getName()) == null) {
			retval = getScreenFromHistory();
		}
		return retval;
	}
	//-------------------------------------------------------------------------
	public boolean historyIsEmpty() {
		return mHistory.isEmpty();
	}
	//-------------------------------------------------------------------------
	public void clearHistory() {
		mHistory.clear();
	}
	
	//-------------------------------------------------------------------------
	public void addProject(MAProject project) {
		mProjects.add(project);
		mCurrentProject = project;
	}
	
	
	public ArrayList<MAProject> getProjects() {
	//-------------------------------------------------------------------------
		return mProjects;
	}
	
	//-------------------------------------------------------------------------
	public int getNumProjects() {
		return mProjects.size();
	}
	//-------------------------------------------------------------------------
	public MAProject getProject(int index) {
		return mProjects.get(index);
	}
	//-------------------------------------------------------------------------
	public MAProject getmCurrentProject() {
		return mCurrentProject;
	}
	
	
	//-------------------------------------------------------------------------
	public static Bitmap createBitmapOfView(View theView) {
		if (theView.getWidth() == 0 || theView.getHeight() == 0) {
			Log.w("meta", "Trying to create bitmap of a view with 0 height or width :(");
		}
		theView.setDrawingCacheEnabled(true);
		theView.buildDrawingCache();
		Bitmap a = Bitmap.createBitmap(theView.getDrawingCache());
		theView.setDrawingCacheEnabled(false);
		
//		Bitmap b = Bitmap.createBitmap(theView.getWidth(),
//				theView.getHeight(), Bitmap.Config.ARGB_8888);
//		Canvas c = new Canvas(b);
//		theView.draw(c);
//		return b;
		
		return a;
	}
	//-------------------------------------------------------------------------
//	public static Bitmap createBitmapOfView(View theView, int width, int height) {
//		theView.setDrawingCacheEnabled(true);
//		theView.layout(0, 0, width, height);
//		theView.buildDrawingCache();
//		Bitmap a = Bitmap.createBitmap(theView.getDrawingCache());
//		theView.setDrawingCacheEnabled(true);
//		return a;
//	}
	
	//-------------------------------------------------------------------------
	public static ImageView createImageViewWithBitmapForContext(Bitmap bitmap, Context context) {
		ImageView image = new ImageView(context);
		image.setImageBitmap(bitmap);
		return image;
	}
	
}
