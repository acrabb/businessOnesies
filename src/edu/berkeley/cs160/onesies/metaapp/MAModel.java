/**
 * 
 */
package edu.berkeley.cs160.onesies.metaapp;

import java.util.ArrayList;

/**
 * @author andre
 *
 */
public class MAModel {

	private static MAModel			instance = null;
	private ArrayList<MAProject>	mProjects = null;
	private MAProject				mCurrentProject = null;
	
	private MAModel() {
		mProjects = new ArrayList<MAProject>();
	}
	
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
	
	public void addProject(MAProject project) {
		mProjects.add(project);
		mCurrentProject = project;
	}
	
	
	public int getNumProjects() {
		return mProjects.size();
	}
	public MAProject getmCurrentProject() {
		return mCurrentProject;
	}
	
}
