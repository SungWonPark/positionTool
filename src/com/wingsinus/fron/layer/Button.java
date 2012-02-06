package com.wingsinus.fron.layer;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.util.Log;

public class Button extends CCSprite implements IInterfaceDelegate {

	private Boolean isPushed = false;
	public Button() {
		// TODO Auto-generated constructor stub
	}

	public Button(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	
	private void scaleUp() {
		if(!isPushed) {
			isPushed = true;
			float scale = getScale();
			setScale(scale+0.2f);
		}
	}
	
	private void scaleDown() {
		if(isPushed) {
			isPushed = false;
			float scale = getScale();
			setScale(scale-0.2f);
		}
	}
	public void initialize() {
		scaleDown();
	}
	public boolean checkDown(CGPoint pt) {
		
//		CGPoint nodePt = getParent().convertToNodeSpace(pt);
//		Log.i("box cordi", getBoundingBox().origin.x+", "+getBoundingBox().origin.y);
//		Log.i("parent convert cordi", nodePt.x +", "+nodePt.y);
//		Log.i("touch  cordi", pt.x +", "+pt.y);
//		Log.i("parent cordi", getParent().getPositionRef().x + ", " + getParent().getPositionRef().y);
		if(getBoundingBox().contains(pt.x, pt.y)) {
			scaleUp();
			return true;
		}
		
		return false;
	}
	
	public boolean checkAndClick(CGPoint pt) {
//		CGPoint nodePt = this.convertToNodeSpace(pt);
		if(getBoundingBox().contains(pt.x, pt.y)) {
			scaleDown();
			return true;
		}
		return false;
	}
}
