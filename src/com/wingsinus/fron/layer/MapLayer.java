package com.wingsinus.fron.layer;

import java.util.ArrayList;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import com.wingsinus.fron.FronBitmapMaker;
import com.wingsinus.fron.FronUtil;
import com.wingsinus.fron.GameStatus;
import com.wingsinus.fron.ServerData;
import com.wingsinus.fron.mapitem.MapItemController;
import com.wingsinus.fron.mapitem.MapItemFactory;
import com.wingsinus.test.R;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class MapLayer extends CCColorLayer {

	private ArrayList<MapItemController> list;
	private MapItemController currentItem;
	private boolean isMoving = false;
	private CGPoint beforePt;
	private CGPoint firstPt;
	private CGPoint multi1;
	private CGPoint multi2;
	
	public MapLayer(ccColor4B color) {
		super(color);
	}

	public MapLayer(ccColor4B color, float w, float h) {
		super(color, w, h);
	}
	
	public void init() {
		ServerData.mapSize = 4;
		makeGround();
		list = new ArrayList<MapItemController>();
		
		MapItemController item = null;
		
		item = MapItemFactory.makeMapItem(MapItemFactory.CHERRY_PINK);
		list.add(item);
		addChild(item.getImage());
		item.isoMoveTo(0,  0);
		
		
		int i;
		for(i=1002; i<=1002; i++){
			item = MapItemFactory.makeMapItem(i);
			list.add(item);
			addChild(item.getImage());
			item.isoMoveTo(0,  0);
			item.getImage().setVisible(false);
		}
		
//		item = MapItemFactory.makeMapItem(MapItemFactory.CHERRY_PINK);
//		list.add(item);
//		addChild(item.getImage());
//		item.isoMoveTo(6,  6);
		GameStatus.isMapEditMode = true;
		
		this.setIsTouchEnabled(true);
		
	}
	
	private void makeGround() {
		Bitmap bitmap = FronBitmapMaker.getInstance().loadBitmap(R.drawable.land_03_line);
		Bitmap target = Bitmap.createBitmap(60*ServerData.mapSize, 30*ServerData.mapSize+20, Config.ARGB_8888);
		Canvas can = new Canvas(target);
		CGPoint pt;
		for(int i = 0; i < ServerData.mapSize; i++) {
			for(int j = 0; j < ServerData.mapSize; j++) {
				pt = FronUtil.isoToScreen(CGPoint.ccp(i,  j));
//				Log.i("position", pt.x+","+pt.y);
				can.drawBitmap(bitmap, pt.x+((ServerData.mapSize-1)*30), pt.y, null);
			}
		}
		CCSprite ground = new CCSprite(target, "land_03");
		ground.setPosition(0,  -(ServerData.mapSize*15));
		addChild(ground);
		
		this.setPosition(400,  300);
		bitmap.recycle();
	}
	
	private float getDistance(CGPoint pt1, CGPoint pt2) {
		float x = pt1.x - pt2.x;
	    float y = pt1.y - pt2.y;
	    return (float) Math.sqrt(x*x + y*y);
	}
	private void changeScale(float diff) {
		float scale = getParent().getScale();
		float original = scale;
		if(diff > 0) {
			if(scale < 3) {
				scale += diff/500;
				if(scale >= 3) {
					scale  = 3;
				}
			}
		}
		else {
			if(scale > 0.5f) {
				scale += diff/500;
				if(scale <= 0.5f)
					scale = 0.5f;
			}
		}
		if(scale != original) {
			getParent().setScale(scale);
		}
	}
	public void roundOkClicked() {
		if(currentItem != null) {
			
		}
		currentItem = null;
	}
	public void roundCancelClicked() {
		if(currentItem != null) {
			
		}
		currentItem = null;
	}
	public void roundRotateClicked() {
		if(currentItem != null) {
			currentItem.rotate();
		}
	}
	public void roundSellClicked() {
		if(currentItem != null) {
			
		}
		currentItem = null;
	}
	public void roundStorageClicked() {
		if(currentItem != null) {
			
		}
		currentItem = null;		
	}


	public boolean ccTouchesBegan(MotionEvent event){
		Log.i("maplayer", "touch");
		isMoving = false;
		if(event.getPointerCount() > 1) {
			multi1 = CGPoint.make(event.getX(0), event.getY(0));
			multi2 = CGPoint.make(event.getX(1), event.getY(1));
		}
		else {
			beforePt = CCDirector.sharedDirector()
	            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
			firstPt = CGPoint.make(beforePt.x, beforePt.y);
			//item check. set selected item.
			if(currentItem != null) {
				CGPoint nodeConvert = this.convertToNodeSpace(beforePt);
				if(currentItem.checkDown(nodeConvert)) {
					isMoving = true;
				}
			}
		}
		return CCTouchDispatcher.kEventHandled;
	}
	
    public boolean ccTouchesMoved(MotionEvent event) {
    	if(event.getPointerCount() > 1) {
    		currentItem = null;
    		CGPoint current1 = CGPoint.make(event.getX(0), event.getY(0));
			CGPoint current2 = CGPoint.make(event.getX(1), event.getY(1));
			float before = getDistance(multi1, multi2);
			float current = getDistance(current1, current2);
			changeScale(current-before);
			multi1 = current1;
			multi2 = current2;
    	}
    	else {
    		
    		if(firstPt != null) {
		    	CGPoint convertedLocation = CCDirector.sharedDirector()
		            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
		    	CGPoint current = getPosition();
		    	if(currentItem != null && GameStatus.isShowRoundMenu && isMoving) {
		    		currentItem.moveBy(convertedLocation.x - beforePt.x, convertedLocation.y - beforePt.y);
		    		SceneManager.getInstance().interfaceLayer.moveByRoundMenu(
		    				convertedLocation.x - beforePt.x, convertedLocation.y - beforePt.y);
		    	}
		    	else {
		    		setPosition(current.x + (convertedLocation.x - beforePt.x), current.y + (convertedLocation.y - beforePt.y));
		    		if(GameStatus.isShowRoundMenu) {
		    			SceneManager.getInstance().interfaceLayer.moveByRoundMenu(
			    				convertedLocation.x - beforePt.x, convertedLocation.y - beforePt.y);
		    		}
		    	}
		    	beforePt = convertedLocation;
    		}
    	}
    	return true;
	}

    public boolean ccTouchesEnded(MotionEvent event) {
    	if(firstPt != null) {
    		if(!isMoving) {
		    	CGPoint pt = CCDirector.sharedDirector()
		    	.convertToGL(CGPoint.make(event.getX(), event.getY()));
		    	if((Math.abs(pt.x - firstPt.x) < 5) && (Math.abs(pt.y - firstPt.y) < 5)) {
		    		CGPoint nodeConvert = this.convertToNodeSpace(pt);
		    		touched(nodeConvert);
		    	}
    		}
	    	firstPt = null;
    	}
    	return true;
	}
    private void touched(CGPoint pt) {
    	MapItemController item = null;
    	int len = list.size();
    	
    	for(int i = 0; i < len; i++) {
    		item = list.get(i);
    		if(item.checkDown(pt)) {
    			currentItem = item;
    			break;
    		}
    	}
    	if(currentItem != null) {
    		if(GameStatus.isMapEditMode) {
    			CGPoint worldConvert = CGPoint.zero();
    			CGPoint itempt = currentItem.getImage().getPositionRef();
    			this.convertToWorldSpace(itempt.x, itempt.y, worldConvert);
    			Log.i("maplayer_touched", "check");
    			SceneManager.getInstance().interfaceLayer.showRoundMenu(worldConvert);
    		}
    		else {
    			//if deco -> show tooltip
    			//else if crop -> harvest.
    		}
    	}
    }
}
