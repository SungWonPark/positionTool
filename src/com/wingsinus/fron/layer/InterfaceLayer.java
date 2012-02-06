package com.wingsinus.fron.layer;

import java.util.ArrayList;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import android.util.Log;
import android.view.MotionEvent;

public class InterfaceLayer extends CCColorLayer {

	private RoundMenu roundMenu;
	private ArrayList<Object> buttonArray;
	private CGPoint firstPt;
	
	public InterfaceLayer(ccColor4B color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	public InterfaceLayer(ccColor4B color, float w, float h) {
		super(color, w, h);
		// TODO Auto-generated constructor stub
	}

	public void init() {
		setIsTouchEnabled(true);
		makeRoundMenu();
//		roundMenu.show(CGPoint.make(300, 300));
	}
	private void makeRoundMenu() {
		roundMenu = new RoundMenu();
		roundMenu.init(this);
	}
	
	public void showRoundMenu(CGPoint pt) {
		roundMenu.show(pt);
	}
	public void moveByRoundMenu(float x, float y) {
		roundMenu.moveBy(x,  y);
	}
	public boolean ccTouchesBegan(MotionEvent event){
		Log.i("interfacelayer", "touch");
		//check touch.
		CGPoint pt = CCDirector.sharedDirector().convertToGL(CGPoint.make(event.getX(), event.getY()));
		if(roundMenu.checkDown(pt)) {
			firstPt = pt;
			return true;
		}
		//other button check.
		return false;
	}

    public boolean ccTouchesMoved(MotionEvent event) {
//    	Log.i("interfacelayer", "move");
    	return false;
	}

    public boolean ccTouchesEnded(MotionEvent event) {
    	if(firstPt != null) {
	    	CGPoint pt = CCDirector.sharedDirector().convertToGL(CGPoint.make(event.getX(), event.getY()));
	    	if((Math.abs(pt.x - firstPt.x) < 5) && (Math.abs(pt.y - firstPt.y) < 5)) {
	    		if(roundMenu.checkAndClick(pt)) {
	    			firstPt = null;
	    			return true;
	    		}
	    		//other button check. buttonarray
	    	}
	    	else {
	    		//all button initialize.
	    		firstPt = null;
	    		return false;
	    	}
    	}
    	return false;
	}
}
