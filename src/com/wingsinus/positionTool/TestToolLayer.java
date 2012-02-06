package com.wingsinus.positionTool;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class TestToolLayer extends CCColorLayer{
	
	private TestToolController testTool;
	
	
	public TestToolLayer(ccColor4B color){
		super(color);
	}
	
	public void init(){
		setIsTouchEnabled(true);
		
		loadTestTool();
	}
	
	public void loadTestTool(){
		testTool = new TestToolController();
		
		testTool.makeTestToolImage(this);
		testTool.makeToolLabel(this);
	}
	
	public boolean ccTouchesBegan(MotionEvent event){
		CGPoint convertedLocation = CCDirector.sharedDirector()
            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
		
		
		return testTool.checkDown(convertedLocation);
	}
	
	public boolean ccTouchesMoved(MotionEvent event){
		
		return false;
	}

	public boolean ccTouchesEnded(MotionEvent event){
	
		return false;
	}
}
