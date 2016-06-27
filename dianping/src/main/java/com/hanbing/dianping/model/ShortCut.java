package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  ShortCut.java
 * Author:  hzbc-01
 * Purpose: Defines the Class ShortCut
 ***********************************************************************/

import java.io.Serializable;

public class ShortCut implements Serializable {
   //id 
   private int id;
   //名称 
   private String name;
   //分类 
   private Category category;
   //图标url 
   private String iconUrl;
   //链接url 
   private String linkUrl;
   //标签 
   private String tag;

      
   public int getId()  
   {  
      return id;  
   }

   public void setId(int id)  
   {  
      this.id = id;  
   }

      
   public String getName()  
   {  
      return name;  
   }

   public void setName(String name)  
   {  
      this.name = name;  
   }

      
   public Category getCategory()  
   {  
      return category;  
   }

   public void setCategory(Category category)  
   {  
      this.category = category;  
   }

      
   public String getIconUrl()  
   {  
      return iconUrl;  
   }

   public void setIconUrl(String iconUrl)  
   {  
      this.iconUrl = iconUrl;  
   }

      
   public String getLinkUrl()  
   {  
      return linkUrl;  
   }

   public void setLinkUrl(String linkUrl)  
   {  
      this.linkUrl = linkUrl;  
   }

      
   public String getTag()  
   {  
      return tag;  
   }

   public void setTag(String tag)  
   {  
      this.tag = tag;  
   }

}