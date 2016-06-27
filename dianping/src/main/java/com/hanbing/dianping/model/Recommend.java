package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  Recommend.java
 * Author:  hzbc-01
 * Purpose: Defines the Class Recommend
 ***********************************************************************/

import java.io.Serializable;

public class Recommend implements Serializable {
   //id 
   private int id;
   //标题 
   private String title;
   //描述 
   private String describe;
   //图标url 
   private String iconUrl;
   //链接url 
   private String linkUrl;

      
   public int getId()  
   {  
      return id;  
   }

   public void setId(int id)  
   {  
      this.id = id;  
   }

      
   public String getTitle()  
   {  
      return title;  
   }

   public void setTitle(String title)  
   {  
      this.title = title;  
   }

      
   public String getDescribe()  
   {  
      return describe;  
   }

   public void setDescribe(String describe)  
   {  
      this.describe = describe;  
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

}