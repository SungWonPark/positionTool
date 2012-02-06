package com.wingsinus.positionTool;

import java.util.List;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.util.Log;

import com.wingsinus.fron.layer.IInterfaceDelegate;
import com.wingsinus.fron.layer.SceneManager;

public class TestToolController implements IInterfaceDelegate{
	private CCSprite positionLeft;
	private CCSprite positionRight;
	private CCSprite positionUp;
	private CCSprite positionDown;
	private CCSprite editMode;
	private CCSprite rotateSprite;
	private CCSprite nextImageSprite;
	private CCLabel position;
	private CCLabel anchor;
	private CCLabel editModeLabel;
	private int imageNum = 1;
	private boolean editPosAndAnc = true;
	private boolean isRotated = false;
	
	public void makeTestToolImage(TestToolLayer layer){
		float screenX = CCDirector.sharedDirector().displaySize().width;
		float screenY = CCDirector.sharedDirector().displaySize().height;
		
		positionLeft = new CCSprite("button/toolbutton_ok.png");
		positionRight = new CCSprite("button/toolbutton_ok.png");
		positionUp = new CCSprite("button/toolbutton_ok.png");
		positionDown = new CCSprite("button/toolbutton_ok.png");
		editMode = new CCSprite("button/toolbutton_ininventory.png");
		rotateSprite = new CCSprite("button/toolbutton_rotate.png");
		nextImageSprite = new CCSprite("button/toolbutton_cancel.png");
		
		positionLeft.setPosition(40f, screenY / 2.0f);
		positionRight.setPosition(screenX - 40f, screenY / 2.0f);
		positionUp.setPosition(screenX / 2f, screenY - 40f);
		positionDown.setPosition(screenX / 2f, 40f);
		editMode.setPosition(screenX -110f, 40f);
		rotateSprite.setPosition(screenX -40f, 40f);
		nextImageSprite.setPosition(screenX - 40f, screenY - 40f);
		
		positionLeft.setRotation(90f);
		positionRight.setRotation(270f);
		positionUp.setRotation(180f);
		
		layer.addChild(positionLeft);
		layer.addChild(positionRight);
		layer.addChild(positionUp);
		layer.addChild(positionDown);
		layer.addChild(editMode);
		layer.addChild(rotateSprite);
		layer.addChild(nextImageSprite);
	}
	
	public void makeToolLabel(TestToolLayer layer){
		CCNode mapLayerItem = SceneManager.getInstance().mapLayer.getChildren().get(1);
		float spriteX = mapLayerItem.getPosition().x;
		float spriteY = mapLayerItem.getPosition().y;
		float anchorX = mapLayerItem.getAnchorPoint().x;
		float anchorY = mapLayerItem.getAnchorPoint().y;
		
		position = CCLabel.makeLabel("Pos x= " + spriteX + ", y= " + spriteY
				, "Marker Felt", 16f);
		anchor = CCLabel.makeLabel("Anc x= " + anchorX + ", y= " + anchorY
				, "Marker Felt", 16f);
		editModeLabel = CCLabel.makeLabel("Pos", "Marker Felt", 16f);
		
		position.setAnchorPoint(0f, 0f);
		anchor.setAnchorPoint(0f, 0f);
		editModeLabel.setAnchorPoint(0f, 0f);
		position.setPosition(5f, 21f);
		anchor.setPosition(5f, 35f);
		editModeLabel.setPosition(5f, 49f);
		
		layer.addChild(position);
		layer.addChild(anchor);
		layer.addChild(editModeLabel);
	}
	
	public void rotate(CCNode node) {
		isRotated = !isRotated;
		node.setScaleX(node.getScaleX()*-1);
	}
	
	public void nextImage(int mapItemSize){
		SceneManager.getInstance().mapLayer.getChildren().get(imageNum).setVisible(false);
		
		if(mapItemSize > (imageNum + 1))
			imageNum++;
		else
			imageNum = 1;

		SceneManager.getInstance().mapLayer.getChildren().get(imageNum).setVisible(true);
	}
	
	public boolean checkDown(CGPoint convertedLocation){
		int i;
		List<CCNode> toolChildrenList = SceneManager.getInstance().testToolLayer.getChildren();
		List<CCNode> mapChildrenList = SceneManager.getInstance().mapLayer.getChildren();
		
		for(i=0;i<4;i++){
			if(editPosAndAnc == true
					&& toolChildrenList.get(i).getBoundingBox().contains(convertedLocation.x, convertedLocation.y)){
				//CGPoint
				float spriteX = mapChildrenList.get(imageNum).getPosition().x;
				float spriteY = mapChildrenList.get(imageNum).getPosition().y;
				
				switch(i){
					case 0:
						spriteX--;
						break;
					case 1:
						spriteX++;
						break;
					case 2:
						spriteY++;
						break;
					case 3:
						spriteY--;
						break;
				}
				
				position.setString("Pos x= " + spriteX + ", y= " + spriteY);
				
				mapChildrenList.get(imageNum).setPosition(spriteX, spriteY);
				
				return true;
			}else if(editPosAndAnc == false
					&& toolChildrenList.get(i).getBoundingBox().contains(convertedLocation.x, convertedLocation.y)){
				//CGPoint
				float anchorX = mapChildrenList.get(imageNum).getAnchorPoint().x;
				float anchorY = mapChildrenList.get(imageNum).getAnchorPoint().y;
				float spriteX = mapChildrenList.get(imageNum).getPosition().x;
				float spriteY = mapChildrenList.get(imageNum).getPosition().y;
				float spriteWidth = mapChildrenList.get(imageNum).getBoundingBox().size.width;
				float spriteHeight = mapChildrenList.get(imageNum).getBoundingBox().size.height;
				float moveAnchor = 0.02f;
				
				switch(i){
					case 0:
						anchorX -= moveAnchor;
						spriteX -= moveAnchor * spriteWidth;
						break;
					case 1:
						anchorX += moveAnchor;
						spriteX += moveAnchor * spriteWidth;
						break;
					case 2:
						anchorY += moveAnchor;
						spriteY += moveAnchor * spriteHeight;
						break;
					case 3:
						anchorY -= moveAnchor;
						spriteY -= moveAnchor * spriteHeight;
						break;
				}

				anchor.setString("Anc x= " + anchorX + ", y= " + anchorY);
				position.setString("Pos x= " + spriteX + ", y= " + spriteY);
				
				mapChildrenList.get(imageNum).setAnchorPoint(anchorX, anchorY);
				mapChildrenList.get(imageNum).setPosition(spriteX, spriteY);
				
				return true;
			}
			
		}
		
		if(toolChildrenList.get(4).getBoundingBox().contains(convertedLocation.x, convertedLocation.y)){
			editPosAndAnc = !editPosAndAnc;
			
			if(editPosAndAnc)
				editModeLabel.setString("Pos");
			else
				editModeLabel.setString("Anc");
			return true;
		}else if(toolChildrenList.get(5).getBoundingBox().contains(convertedLocation.x, convertedLocation.y)){
			
			rotate(mapChildrenList.get(imageNum));
			return true;
		}else if(toolChildrenList.get(6).getBoundingBox().contains(convertedLocation.x, convertedLocation.y)){
			nextImage(mapChildrenList.size());
			return true;
		}
		
		return false;
	}
	
	public boolean checkAndClick(CGPoint pt){
		return true;
	}
}
