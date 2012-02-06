package com.wingsinus.fron.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.ccColor4B;

import com.wingsinus.positionTool.TestToolLayer;


public class SceneManager {

	private static SceneManager manager;
	
	private CCScene mainScene;
	public CCLayer mapZoomLayer;
	public MapLayer mapLayer;
	public InterfaceLayer interfaceLayer;
	public TestToolLayer testToolLayer;
	
	
	public SceneManager() {
		// TODO Auto-generated constructor stub
	}
	public static SceneManager getInstance() {
		if(manager == null)
			manager = new SceneManager();
		return manager;
	}
	
	public void onStart(CCGLSurfaceView view) {
       CCDirector.sharedDirector().attachInView(view);
       CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
       CCDirector.sharedDirector().setDisplayFPS(true);
       CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
       mainScene = CCScene.node();
       mapZoomLayer = CCLayer.node();
       mapLayer = new MapLayer(ccColor4B.ccc4(255, 255, 255, 0));
       mapLayer.setAnchorPoint(0 ,0);
       interfaceLayer = new InterfaceLayer(ccColor4B.ccc4(255, 255, 255, 0));
       testToolLayer = new TestToolLayer(ccColor4B.ccc4(255, 255, 255, 0));
       mainScene.addChild(mapZoomLayer);
       mapZoomLayer.addChild(mapLayer);
       mainScene.addChild(interfaceLayer);
       mainScene.addChild(testToolLayer);
       mapLayer.init();
       interfaceLayer.init();
       testToolLayer.init();
       
       CCDirector.sharedDirector().runWithScene(mainScene);
	}
	
	public void onPause() {
	    CCDirector.sharedDirector().pause();
	}
	
	public void onResume() {
	    CCDirector.sharedDirector().resume();
	}
	
	public void onDestroy() {
	    CCDirector.sharedDirector().end();
	}
}
