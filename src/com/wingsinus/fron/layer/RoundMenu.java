package com.wingsinus.fron.layer;

import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.wingsinus.fron.GameStatus;

import android.util.Log;

public class RoundMenu implements IInterfaceDelegate {

	private CCSprite bg;
	
	private Button okButton;
	private Button cancelButton;
	private Button rotateButton;
	private Button sellButton;
	private Button storageButton;
	
	public boolean isShown = false;
	
	public RoundMenu() {
		// TODO Auto-generated constructor stub
		bg = new CCSprite("button/toolbutton_bg.png");
		okButton = new Button("button/toolbutton_ok.png");
		cancelButton = new Button("button/toolbutton_cancel.png");
		sellButton = new Button("button/toolbutton_sale.png");
		storageButton = new Button("button/toolbutton_ininventory.png");
		rotateButton = new Button("button/toolbutton_rotate.png");
	}
	
	public void init(InterfaceLayer layer) {
		int angle = 72;
		int radius = 115;
		int offsetX = 128;
		int offsetY = 128;
		int angleDiff = 0;
		bg.addChild(okButton);
		bg.addChild(cancelButton);
		bg.addChild(sellButton);
		bg.addChild(storageButton);
		bg.addChild(rotateButton);
		int counter = 0;
		rotateButton.setPosition((float)Math.sin(Math.toRadians(angle*counter-angleDiff))*radius+offsetX, 
				(float)Math.cos(Math.toRadians(angle*counter-angleDiff))*radius+offsetY);
		counter++;
		okButton.setPosition((float)Math.sin(Math.toRadians(angle*counter-angleDiff))*radius+offsetX, 
				(float)Math.cos(Math.toRadians(angle*counter-angleDiff))*radius+offsetY);
		counter++;
		storageButton.setPosition((float)Math.sin(Math.toRadians(angle*counter-angleDiff))*radius+offsetX, 
				(float)Math.cos(Math.toRadians(angle*counter-angleDiff))*radius+offsetY);
		counter++;
		sellButton.setPosition((float)Math.sin(Math.toRadians(angle*counter-angleDiff))*radius+offsetX, 
				(float)Math.cos(Math.toRadians(angle*counter-angleDiff))*radius+offsetY);
		counter++;
		cancelButton.setPosition((float)Math.sin(Math.toRadians(angle*counter-angleDiff))*radius+offsetX, 
				(float)Math.cos(Math.toRadians(angle*counter-angleDiff))*radius+offsetY);
		layer.addChild(bg);
		
		Log.i("cos 0 sin 0" , Math.cos(0)+","+Math.sin(0));
		Log.i("cos 90 sin 90" , Math.cos(90)+","+Math.sin(90));
		Log.i("cos 180 sin 180" , Math.cos(180)+","+Math.sin(180));
		bg.setVisible(false);
		
	}
	
	
	
	private void okClicked() {
		hide();
		SceneManager.getInstance().mapLayer.roundOkClicked();
	}
	private void cancelClicked() {
		hide();
		SceneManager.getInstance().mapLayer.roundCancelClicked();
	}
	private void sellClicked() {
		hide();
		SceneManager.getInstance().mapLayer.roundSellClicked();
	}
	
	private void storageClicked() {
		hide();
		SceneManager.getInstance().mapLayer.roundStorageClicked();
	}
	private void rotateClicked() {
		SceneManager.getInstance().mapLayer.roundRotateClicked();
	}
	public void moveBy(float x, float y) {
		CGPoint current = bg.getPositionRef();
		bg.setPosition(current.x + x, current.y + y);
	}
	public void show(CGPoint pt) {
		if(!isShown) {
			bg.setScale(0.2f);
			bg.setRotation(-45);
			bg.setPosition(pt);
			bg.setVisible(true);
			bg.runAction(CCScaleTo.action(0.1f, 1.0f));
			bg.runAction(CCRotateTo.action(0.1f, 0));
			isShown = true;
			GameStatus.isShowRoundMenu = true;
		}
	}
	
	public void hide() {
		if(isShown) {
			okButton.initialize();
			sellButton.initialize();
			cancelButton.initialize();
			storageButton.initialize();
			rotateButton.initialize();
			bg.setVisible(false);
			isShown = false;
			GameStatus.isShowRoundMenu = false;
		}
	}
	public boolean checkDown(CGPoint pt) {
		if(!isShown)
			return false;
		CGPoint converted = bg.convertToNodeSpace(pt);
		if(okButton.checkDown(converted)) {
			return true;
		}
		else if(cancelButton.checkDown(converted)) {
			return true;
		}
		else if(sellButton.checkDown(converted)) {
			return true;
		}
		else if(storageButton.checkDown(converted)) {
			return true;
		}
		else if(rotateButton.checkDown(converted)) {
			return true;
		}
		
		return false;
	}
	public boolean checkAndClick(CGPoint pt) {
		CGPoint converted = bg.convertToNodeSpace(pt);
		if(okButton.checkAndClick(converted)) {
			okClicked();
			return true;
		}
		else if(sellButton.checkAndClick(converted)) {
			sellClicked();
			return true;
		}
		else if(cancelButton.checkAndClick(converted)) {
			cancelClicked();
			return true;
		}
		else if(storageButton.checkAndClick(converted)) {
			storageClicked();
			return true;
		}
		else if(rotateButton.checkAndClick(converted)) {
			rotateClicked();
			return true;
		}
		return false;
	}
}
