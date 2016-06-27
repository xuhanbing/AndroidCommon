package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  ShopInfo.java
 * Author:  hzbc-01
 * Purpose: Defines the Class ShopInfo
 ***********************************************************************/

import java.io.Serializable;

public class ShopInfo implements Serializable {
   //id 
   private String id;
   //店名 
   private String name;
   //店铺图像 
   private String pictureUrl;
   //价格性息 
   private String priceInfo;
   //电话 
   private String tel;
   //评分 
   private Score score;
   //评分次数 
   private long gradeCount;
   //地址 
   private String addresss;
   //纬度 
   private double latitude;
   //经度 
   private double longitude;
   //距离 
   private double distance;
   //城市 
   private String city;
   //区域 
   private String area;
   //分类 
   private Category category;
   //标签 
   private String tag;
   //特惠 
   private String favorable;

   public class Score implements Serializable {
      //店铺id 
      private String shopId;
      //评分 
      private float total;
      //口味 
      private float taste;
      //环境 
      private float environment;
      //服务 
      private float service;
   
         
      public String getShopId()  
      {  
         return shopId;  
      }
   
      public void setShopId(String shopId)  
      {  
         this.shopId = shopId;  
      }
   
         
      public float getTotal()  
      {  
         return total;  
      }
   
      public void setTotal(float total)  
      {  
         this.total = total;  
      }
   
         
      public float getTaste()  
      {  
         return taste;  
      }
   
      public void setTaste(float taste)  
      {  
         this.taste = taste;  
      }
   
         
      public float getEnvironment()  
      {  
         return environment;  
      }
   
      public void setEnvironment(float environment)  
      {  
         this.environment = environment;  
      }
   
         
      public float getService()  
      {  
         return service;  
      }
   
      public void setService(float service)  
      {  
         this.service = service;  
      }
   
   }

      
   public String getId()  
   {  
      return id;  
   }

   public void setId(String id)  
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

      
   public String getPictureUrl()  
   {  
      return pictureUrl;  
   }

   public void setPictureUrl(String pictureUrl)  
   {  
      this.pictureUrl = pictureUrl;  
   }

      
   public String getPriceInfo()  
   {  
      return priceInfo;  
   }

   public void setPriceInfo(String priceInfo)  
   {  
      this.priceInfo = priceInfo;  
   }

      
   public String getTel()  
   {  
      return tel;  
   }

   public void setTel(String tel)  
   {  
      this.tel = tel;  
   }

      
   public Score getScore()
   {  
      return score;  
   }

   public void setScore(Score score)
   {  
      this.score = score;  
   }

      
   public long getGradeCount()  
   {  
      return gradeCount;  
   }

   public void setGradeCount(long gradeCount)  
   {  
      this.gradeCount = gradeCount;  
   }

      
   public String getAddresss()  
   {  
      return addresss;  
   }

   public void setAddresss(String addresss)  
   {  
      this.addresss = addresss;  
   }

      
   public double getLatitude()  
   {  
      return latitude;  
   }

   public void setLatitude(double latitude)  
   {  
      this.latitude = latitude;  
   }

      
   public double getLongitude()  
   {  
      return longitude;  
   }

   public void setLongitude(double longitude)  
   {  
      this.longitude = longitude;  
   }

      
   public double getDistance()  
   {  
      return distance;  
   }

   public void setDistance(double distance)  
   {  
      this.distance = distance;  
   }

      
   public String getCity()  
   {  
      return city;  
   }

   public void setCity(String city)  
   {  
      this.city = city;  
   }

      
   public String getArea()  
   {  
      return area;  
   }

   public void setArea(String area)  
   {  
      this.area = area;  
   }

      
   public Category getCategory()  
   {  
      return category;  
   }

   public void setCategory(Category category)  
   {  
      this.category = category;  
   }

      
   public String getTag()  
   {  
      return tag;  
   }

   public void setTag(String tag)  
   {  
      this.tag = tag;  
   }

      
   public String getFavorable()  
   {  
      return favorable;  
   }

   public void setFavorable(String favorable)  
   {  
      this.favorable = favorable;  
   }

}