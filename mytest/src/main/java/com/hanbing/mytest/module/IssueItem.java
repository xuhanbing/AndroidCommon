package com.hanbing.mytest.module;

public class IssueItem {

	
	
	public static final int ITEM_SIZE = 5;
	
	public int startNumber;
	public int totalCount;
	
	
	public String getTitle()
	{
		String prefix = "Vol.";
		
		String title = prefix + startNumber;
		
		if (totalCount > 1)
		{
			title = title + "\n-\n"
					+ prefix + (startNumber + totalCount - 1);
		}
		
		return title;
		
	}

}
