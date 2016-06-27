package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  MayFavorite.java
 * Author:  hzbc-01
 * Purpose: Defines the Class MayFavorite
 ***********************************************************************/

import java.util.*;
import java.io.Serializable;

public class MayFavorite implements Serializable {
   //id 
   private String id;
   //标题 
   private String title;
   //描述 
   private String describe;
   //图片地址 
   private String pictureUrl;



   //链接地址
   private String linkUrl;
   //价格 
   private float price;
   //原价 
   private float originalPrice;
   //标签 
   private List<String> tags;
   //已售 
   private int saleCount;
   //浏览 
   private int viewCount;
   //类型 
   private int type;
   //状态 
   private int status;

      
   public String getId()  
   {  
      return id;  
   }

   public void setId(String id)  
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

      
   public String getPictureUrl()  
   {  
      return pictureUrl;  
   }

   public void setPictureUrl(String pictureUrl)  
   {  
      this.pictureUrl = pictureUrl;  
   }

      
   public float getPrice()  
   {  
      return price;  
   }

   public void setPrice(float price)  
   {  
      this.price = price;  
   }

      
   public float getOriginalPrice()  
   {  
      return originalPrice;  
   }

   public void setOriginalPrice(float originalPrice)  
   {  
      this.originalPrice = originalPrice;  
   }

      
   public List<String> getTags()  
   {  
      return tags;  
   }

   public void setTags(List<String> tags)  
   {  
      this.tags = tags;  
   }

      
   public int getSaleCount()  
   {  
      return saleCount;  
   }

   public void setSaleCount(int saleCount)  
   {  
      this.saleCount = saleCount;  
   }

      
   public int getViewCount()  
   {  
      return viewCount;  
   }

   public void setViewCount(int viewCount)  
   {  
      this.viewCount = viewCount;  
   }

      
   public int getType()  
   {  
      return type;  
   }

   public void setType(int type)  
   {  
      this.type = type;  
   }

      
   public int getStatus()  
   {  
      return status;  
   }

   public void setStatus(int status)  
   {  
      this.status = status;  
   }

   public String getLinkUrl() {
      return linkUrl;
   }

   public void setLinkUrl(String linkUrl) {
      this.linkUrl = linkUrl;
   }

}