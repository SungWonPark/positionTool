package com.wingsinus.fron.layer;

import org.cocos2d.types.CGPoint;

public interface IInterfaceDelegate {

	public boolean checkDown(CGPoint pt);
	
	public boolean checkAndClick(CGPoint pt);
}
