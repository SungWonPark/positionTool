package com.wingsinus.fron.mapitem;

public class MapItemFactory {

	//작물 100~200
	//나무 201~300
	//동물 301~400
	//건물 401~1000
	//데코 1001~2000
	//리워드 ,재료, 선물등 2001~4000
	//기타 4001~
	public static int CHERRY_PINK = 1001;
	public static int WATERFEED = 1002;
	
	public static MapItemController makeMapItem(int type) {
		MapItemController item = null;
		switch(type)
		{
		case 1001:
			item = new MapItemController("cherry_pink.png", -0.24f, 64);
			item.setRotateCenter(0.5f, 0.3f);
			break;
		case 1002:
			item = new MapItemController("water_feed.png", -10, -5);
			item.setRotateCenter(0.7f, 0.7f);
			break;
		}
		return item;
	}
}
