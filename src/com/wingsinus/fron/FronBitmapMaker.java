package com.wingsinus.fron;

import java.io.InputStream;
import java.security.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import org.cocos2d.actions.CCTimer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class FronBitmapMaker {

	private static FronBitmapMaker maker;
	private Context context;
	private static Hashtable<String, boolean[][]> name2PixelHash;
	
	private FronBitmapMaker() {
		
	}
	public static Bitmap makeBitmap(String fileName) {
		try {
			InputStream is = CCDirector.sharedDirector().getActivity().getAssets().open(fileName);
	    	Bitmap bmp = BitmapFactory.decodeStream(is);
	    	boolean[][] imagePixel = new boolean[bmp.getHeight()][bmp.getWidth()];
			is.close();
			
			name2PixelHash = new Hashtable<String, boolean[][]>();
			
			if(name2PixelHash.get(fileName) == null) {
				//make boolean array.
				int i, j;
				
				long time = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dd = new Date(time);
				//return sdf.format(dd);
				
				Log.i("getPixel start", String.valueOf(sdf.format(dd)));
				
				for(i=0;i<bmp.getHeight();i++){
					for(j=0;j<bmp.getWidth();j++){
						if(bmp.getPixel(j, i) != 0)
							imagePixel[i][j] = true;
						else
							imagePixel[i][j] = false;
					}
				}
				time = System.currentTimeMillis();
				dd = new Date(time);
				Log.i("getPixel end", String.valueOf(sdf.format(dd)));
				name2PixelHash.put(fileName, imagePixel);
			}
			
			return bmp;
		}
		catch(Exception e) {
			Log.e(e.getStackTrace()[0].getClassName(), e.toString());
		}
		return null;
	}
	public static FronBitmapMaker getInstance() {
		if(maker == null)
			maker = new FronBitmapMaker();
		return maker;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public Bitmap loadBitmap(int id) {
		Bitmap ret = null;
		if(context != null)
			ret = BitmapFactory.decodeResource(context.getResources(), id);
		return ret;
	}
	
	public static CGPoint pt2BmpPt(CGPoint clickPt, CGSize bmpSize){
		
		Log.i("pt2bmpPt", "cx= " + clickPt.x + ", cy= " + clickPt.y + ", bx= " + (bmpSize.width/2) + ", by= " + (bmpSize.height/2));
		Log.i("pt2bmpPt calc", "x = " + (clickPt.x + (bmpSize.width/2)) + ", y = " + ((-clickPt.y) - (bmpSize.height/2)));
		return CGPoint.ccp(clickPt.x + (bmpSize.width/2), (-clickPt.y) - (bmpSize.height/2));
	}
	
	public static boolean checkPixel(String fileName, int x, int y) {
		boolean[][] array = name2PixelHash.get(fileName);
		if(array == null)
			return true;
		try {
			if(array[y][x] == true)
				return true;
			else
				return false;
		}
		catch(Exception e) {
			Log.w(e.getStackTrace()[0].getClassName(), e.toString());
		}
//		 
		return true;
	}
}
