package com.wingsinus.fron.mapitem;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;
import android.util.Log;

import com.wingsinus.fron.FronBitmapMaker;
import com.wingsinus.fron.layer.IInterfaceDelegate;

public class MapItemController implements IInterfaceDelegate{

	private CCSprite mainImage;
	private String fileName;
	
	private int posX;
	private int posY;
	private int sizeX;
	private int sizeY;
	private float defaultX;
	private float defaultY;
	private float anchorX;
	private float anchorY;
	private boolean isRotated = false;
	
	public MapItemController(String fileName, float initX, float initY) {
		this.fileName = fileName;
		defaultX = initX;
		defaultY = initY;
		makeImage();
	}
	
	private void makeImage() {
		Bitmap bitmap = FronBitmapMaker.makeBitmap(fileName);
		mainImage = new CCSprite(bitmap, fileName);
		mainImage.setPosition(defaultX, defaultY);
	}
	public void setRotateCenter(float x, float y) {
		anchorX = x;
		anchorY = y;
	}
	public void setSize(int x, int y) {
		sizeX = x;
		sizeY = y;
	}
	public void rotate() {
		isRotated = !isRotated;
		getImage().setScaleX(getImage().getScaleX()*-1);
	}
	public CCNode getImage() {
		if(mainImage == null)
			makeImage();
		return mainImage;
	}
	public void isoMoveTo(int x, int y) {
		posX = x;
		posY = y;
		mainImage.setPosition(30*x - 30*y+defaultX, 15*x+15*y+defaultY);
	}
	public void moveTo(float x, float y) {
		mainImage.setPosition(x+defaultX, y+defaultY);
	}
	public void moveBy(float x, float y) {
		CGPoint current = mainImage.getPositionRef();
		mainImage.setPosition(current.x + x,  current.y+y);
	}
	public boolean checkDown(CGPoint pt) {
		CGRect rect = mainImage.getBoundingBox();
		if(rect.contains(pt.x, pt.y)) {
			CGPoint bmpPt = CGPoint.make(pt.x - rect.origin.x, rect.size.height - (pt.y - rect.origin.y));
			Log.i("return binding true", "aa, bx= " + bmpPt.x + ", by= " + bmpPt.y);
			if(bmpPt.x < rect.size.width && bmpPt.x >= 0
					&& bmpPt.y < rect.size.height && bmpPt.y >= 0){
				if(FronBitmapMaker.checkPixel(this.fileName, Math.round(bmpPt.x), Math.round(bmpPt.y))) {
					Log.i("return ckpixel true", "aa");
					return true;
				}
			}else{
				Log.i("return ckpixel false", "aa");
			}
		}
		return false;
	}

	public boolean checkAndClick(CGPoint pt) {
		if(getImage().getBoundingBox().contains(pt.x, pt.y))
			return true;
		return false;
	}
}
