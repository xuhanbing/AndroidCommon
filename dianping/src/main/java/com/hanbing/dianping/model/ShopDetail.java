package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  ShopDetail.java
 * Author:  hzbc-01
 * Purpose: Defines the Class ShopDetail
 ***********************************************************************/

import java.util.*;
import java.io.Serializable;

public class ShopDetail implements Serializable {
   //店铺基本信息 
   private ShopInfo shopInfo;
   //网友推荐 
   private String recommend;
   //网友点评数 
   private int commentCount;
   //来过人数 
   private int beenCount;
   //排行榜 
   private String rankListTitle;
   //排行榜名次 
   private int rankListRank;
   //排行榜url 
   private int rankListUrl;
   //榜单 
   private String listTitle;
   //榜单url 
   private String listUrl;
   //分店数 
   private int branchCount;
   //附近 
   private List<ShopNearby> nearbys;
   //商户信息 
   private MerchantInfo merchantInfo;
   //推广 
   private List<ShopInfo> popularizes;
   //标签 
   private List<Mark> marks;
   //是否点赞 
   private boolean parise;
   //是否签到 
   private boolean signIn;

   public class Mark implements Serializable {
   }
   
   public class MerchantInfo implements Serializable {
      //id 
      private String id;
      //服务设施 
      private List<String> settings;
      //信息 
      private List<Info> infos;
      //资质证照 
      private String picture;
   
      public class Info implements Serializable {
         //标题 
         private String title;
         //内容 
         private String content;
      
            
         public String getTitle()  
         {  
            return title;  
         }
      
         public void setTitle(String title)  
         {  
            this.title = title;  
         }
      
            
         public String getContent()  
         {  
            return content;  
         }
      
         public void setContent(String content)  
         {  
            this.content = content;  
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
   
         
      public List<String> getSettings()  
      {  
         return settings;  
      }
   
      public void setSettings(List<String> settings)  
      {  
         this.settings = settings;  
      }
   
         
      public List<Info> getInfos()  
      {  
         return infos;  
      }
   
      public void setInfos(List<Info> infos)  
      {  
         this.infos = infos;  
      }
   
         
      public String getPicture()  
      {  
         return picture;  
      }
   
      public void setPicture(String picture)  
      {  
         this.picture = picture;  
      }
   
   }
   
   public class Booking extends Mark implements Serializable {
      //数量 
      private int count;
      //标签 
      private List<String> tags;
   
         
      public int getCount()  
      {  
         return count;  
      }
   
      public void setCount(int count)  
      {  
         this.count = count;  
      }
   
         
      public List<String> getTags()  
      {  
         return tags;  
      }
   
      public void setTags(List<String> tags)  
      {  
         this.tags = tags;  
      }
   
   }
   
   public class Queue extends Mark implements Serializable {
      //状态 
      private String state;
   
         
      public String getState()  
      {  
         return state;  
      }
   
      public void setState(String state)  
      {  
         this.state = state;  
      }
   
   }
   
   public class Favorable extends Mark implements Serializable {
      //提示 
      private String tips;
      //已购数量 
      private int count;
      //信息 
      private List<Info> infos;
   
      public class Info implements Serializable {
         //信息 
         private String info;
         //说明 
         private String explain;
         //状态 
         private String state;
      
            
         public String getInfo()  
         {  
            return info;  
         }
      
         public void setInfo(String info)  
         {  
            this.info = info;  
         }
      
            
         public String getExplain()  
         {  
            return explain;  
         }
      
         public void setExplain(String explain)  
         {  
            this.explain = explain;  
         }
      
            
         public String getState()  
         {  
            return state;  
         }
      
         public void setState(String state)  
         {  
            this.state = state;  
         }
      
      }
   
         
      public String getTips()  
      {  
         return tips;  
      }
   
      public void setTips(String tips)  
      {  
         this.tips = tips;  
      }
   
         
      public int getCount()  
      {  
         return count;  
      }
   
      public void setCount(int count)  
      {  
         this.count = count;  
      }
   
         
      public List<Info> getInfos()  
      {  
         return infos;  
      }
   
      public void setInfos(List<Info> infos)  
      {  
         this.infos = infos;  
      }
   
   }
   
   public class Coupon extends Mark implements Serializable {
      //信息 
      private List<Info> infos;
   
      public class Info implements Serializable {
         //价格 
         private float price;
         //原价 
         private float originalPrice;
         //已售数量 
         private int count;
         //提示 
         private String tips;
      
            
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
      
            
         public int getCount()  
         {  
            return count;  
         }
      
         public void setCount(int count)  
         {  
            this.count = count;  
         }
      
            
         public String getTips()  
         {  
            return tips;  
         }
      
         public void setTips(String tips)  
         {  
            this.tips = tips;  
         }
      
      }
   
         
      public List<Info> getInfos()  
      {  
         return infos;  
      }
   
      public void setInfos(List<Info> infos)  
      {  
         this.infos = infos;  
      }
   
   }
   
   public class GroupPurchase extends Mark implements Serializable {
      //提示 
      private String tips;
      //信息 
      private List<Info> infos;
   
      public class Info implements Serializable {
         //id 
         private String id;
         //标题 
         private String title;
         //数量 
         private int count;
         //价格 
         private float price;
         //原价 
         private float originalPrice;
         //图片 
         private String pictureUrl;
      
            
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
      
            
         public int getCount()  
         {  
            return count;  
         }
      
         public void setCount(int count)  
         {  
            this.count = count;  
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
      
            
         public String getPictureUrl()  
         {  
            return pictureUrl;  
         }
      
         public void setPictureUrl(String pictureUrl)  
         {  
            this.pictureUrl = pictureUrl;  
         }
      
      }
   
         
      public String getTips()  
      {  
         return tips;  
      }
   
      public void setTips(String tips)  
      {  
         this.tips = tips;  
      }
   
         
      public List<Info> getInfos()  
      {  
         return infos;  
      }
   
      public void setInfos(List<Info> infos)  
      {  
         this.infos = infos;  
      }
   
   }
   
   public class Banquet extends Mark implements Serializable {
   }
   
   public class Card extends Mark implements Serializable {
      //标题 
      private String title;
   
         
      public String getTitle()  
      {  
         return title;  
      }
   
      public void setTitle(String title)  
      {  
         this.title = title;  
      }
   
   }
   
   public class Sale extends Mark implements Serializable {
      //标题 
      private String title;
      //数量 
      private int count;
   
         
      public String getTitle()  
      {  
         return title;  
      }
   
      public void setTitle(String title)  
      {  
         this.title = title;  
      }
   
         
      public int getCount()  
      {  
         return count;  
      }
   
      public void setCount(int count)  
      {  
         this.count = count;  
      }
   
   }
   
   public class ShopNearby implements Serializable {
      //分类 
      private Category category;
      //数量 
      private int count;
   
         
      public Category getCategory()  
      {  
         return category;  
      }
   
      public void setCategory(Category category)  
      {  
         this.category = category;  
      }
   
         
      public int getCount()  
      {  
         return count;  
      }
   
      public void setCount(int count)  
      {  
         this.count = count;  
      }
   
   }

      
   public ShopInfo getShopInfo()  
   {  
      return shopInfo;  
   }

   public void setShopInfo(ShopInfo shopInfo)  
   {  
      this.shopInfo = shopInfo;  
   }

      
   public String getRecommend()  
   {  
      return recommend;  
   }

   public void setRecommend(String recommend)  
   {  
      this.recommend = recommend;  
   }

      
   public int getCommentCount()  
   {  
      return commentCount;  
   }

   public void setCommentCount(int commentCount)  
   {  
      this.commentCount = commentCount;  
   }

      
   public int getBeenCount()  
   {  
      return beenCount;  
   }

   public void setBeenCount(int beenCount)  
   {  
      this.beenCount = beenCount;  
   }

      
   public String getRankListTitle()  
   {  
      return rankListTitle;  
   }

   public void setRankListTitle(String rankListTitle)  
   {  
      this.rankListTitle = rankListTitle;  
   }

      
   public int getRankListRank()  
   {  
      return rankListRank;  
   }

   public void setRankListRank(int rankListRank)  
   {  
      this.rankListRank = rankListRank;  
   }

      
   public int getRankListUrl()  
   {  
      return rankListUrl;  
   }

   public void setRankListUrl(int rankListUrl)  
   {  
      this.rankListUrl = rankListUrl;  
   }

      
   public String getListTitle()  
   {  
      return listTitle;  
   }

   public void setListTitle(String listTitle)  
   {  
      this.listTitle = listTitle;  
   }

      
   public String getListUrl()  
   {  
      return listUrl;  
   }

   public void setListUrl(String listUrl)  
   {  
      this.listUrl = listUrl;  
   }

      
   public int getBranchCount()  
   {  
      return branchCount;  
   }

   public void setBranchCount(int branchCount)  
   {  
      this.branchCount = branchCount;  
   }

      
   public List<ShopNearby> getNearbys()  
   {  
      return nearbys;  
   }

   public void setNearbys(List<ShopNearby> nearbys)  
   {  
      this.nearbys = nearbys;  
   }

      
   public MerchantInfo getMerchantInfo()
   {  
      return merchantInfo;  
   }

   public void setMerchantInfo(MerchantInfo merchantInfo)
   {  
      this.merchantInfo = merchantInfo;  
   }

      
   public List<ShopInfo> getPopularizes()  
   {  
      return popularizes;  
   }

   public void setPopularizes(List<ShopInfo> popularizes)  
   {  
      this.popularizes = popularizes;  
   }

      
   public List<Mark> getMarks()  
   {  
      return marks;  
   }

   public void setMarks(List<Mark> marks)  
   {  
      this.marks = marks;  
   }

      
   public boolean isParise()  
   {  
      return parise;  
   }

   public void setParise(boolean parise)  
   {  
      this.parise = parise;  
   }

      
   public boolean isSignIn()  
   {  
      return signIn;  
   }

   public void setSignIn(boolean signIn)  
   {  
      this.signIn = signIn;  
   }

}