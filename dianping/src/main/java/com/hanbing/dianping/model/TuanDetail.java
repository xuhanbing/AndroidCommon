package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  GroupPurchaseDetail.java
 * Author:  hzbc-01
 * Purpose: Defines the Class GroupPurchaseDetail
 ***********************************************************************/

import java.util.*;
import java.io.Serializable;

public class TuanDetail implements Serializable {
   //id 
   private String id;
   //标题 
   private String title;
   //描述 
   private String describe;
   //图片 
   private List<String> pictures;
   //优惠信息 
   private List<Object> discount;
   //标签 
   private List<String> tags;
   //好评度 
   private float praisePercent;
   //评价个数 
   private int evaluationCount;
   //适用商户个数 
   private int applyMerchantCount;
   //店铺信息 
   private ShopInfo shopInfo;
   //通知url 
   private String noticeUrl;
   //详情url 
   private String detailUrl;
   //价格 
   private float price;
   //原价 
   private float originalPrice;
   //抢购价 
   private float snapUpPrice = -1.0f;

      
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

      
   public List<String> getPictures()  
   {  
      return pictures;  
   }

   public void setPictures(List<String> pictures)  
   {  
      this.pictures = pictures;  
   }

      
   public List<Object> getDiscount()  
   {  
      return discount;  
   }

   public void setDiscount(List<Object> discount)  
   {  
      this.discount = discount;  
   }

      
   public List<String> getTags()  
   {  
      return tags;  
   }

   public void setTags(List<String> tags)  
   {  
      this.tags = tags;  
   }

      
   public float getPraisePercent()  
   {  
      return praisePercent;  
   }

   public void setPraisePercent(float praisePercent)  
   {  
      this.praisePercent = praisePercent;  
   }

      
   public int getEvaluationCount()  
   {  
      return evaluationCount;  
   }

   public void setEvaluationCount(int evaluationCount)  
   {  
      this.evaluationCount = evaluationCount;  
   }

      
   public int getApplyMerchantCount()  
   {  
      return applyMerchantCount;  
   }

   public void setApplyMerchantCount(int applyMerchantCount)  
   {  
      this.applyMerchantCount = applyMerchantCount;  
   }

      
   public ShopInfo getShopInfo()  
   {  
      return shopInfo;  
   }

   public void setShopInfo(ShopInfo shopInfo)  
   {  
      this.shopInfo = shopInfo;  
   }

      
   public String getNoticeUrl()  
   {  
      return noticeUrl;  
   }

   public void setNoticeUrl(String noticeUrl)  
   {  
      this.noticeUrl = noticeUrl;  
   }

      
   public String getDetailUrl()  
   {  
      return detailUrl;  
   }

   public void setDetailUrl(String detailUrl)  
   {  
      this.detailUrl = detailUrl;  
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

      
   public float getSnapUpPrice()  
   {  
      return snapUpPrice;  
   }

   public void setSnapUpPrice(float snapUpPrice)  
   {  
      this.snapUpPrice = snapUpPrice;  
   }

}