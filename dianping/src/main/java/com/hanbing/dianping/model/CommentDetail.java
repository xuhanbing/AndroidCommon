package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  CommentDetail.java
 * Author:  hzbc-01
 * Purpose: Defines the Class CommentDetail
 ***********************************************************************/

import java.util.*;
import java.io.Serializable;

public class CommentDetail implements Serializable {
   //id 
   private String id;
   //用户信息 
   private UserInfo userInfo;
   //打分 
   private ShopInfo.Score score;
   //时间 
   private long date;
   //内容 
   private String content;
   //图片 
   private List<String> pictures;
   //浏览次数 
   private int viewCount;
   //赞数量 
   private int praiseCount;
   //是否点赞 
   private boolean praise;
   //赞过的人 
   private List<UserInfo> praiseUsers;
   //子评论 
   private List<CommentDetail> comments;
   //店铺性息 
   private ShopInfo shopInfo;
   //是否优质 
   private boolean highlight;

      
   public String getId()  
   {  
      return id;  
   }

   public void setId(String id)  
   {  
      this.id = id;  
   }

      
   public UserInfo getUserInfo()  
   {  
      return userInfo;  
   }

   public void setUserInfo(UserInfo userInfo)  
   {  
      this.userInfo = userInfo;  
   }

      
   public ShopInfo.Score getScore()  
   {  
      return score;  
   }

   public void setScore(ShopInfo.Score score)  
   {  
      this.score = score;  
   }

      
   public long getDate()  
   {  
      return date;  
   }

   public void setDate(long date)  
   {  
      this.date = date;  
   }

      
   public String getContent()  
   {  
      return content;  
   }

   public void setContent(String content)  
   {  
      this.content = content;  
   }

      
   public List<String> getPictures()  
   {  
      return pictures;  
   }

   public void setPictures(List<String> pictures)  
   {  
      this.pictures = pictures;  
   }

      
   public int getViewCount()  
   {  
      return viewCount;  
   }

   public void setViewCount(int viewCount)  
   {  
      this.viewCount = viewCount;  
   }

      
   public int getPraiseCount()  
   {  
      return praiseCount;  
   }

   public void setPraiseCount(int praiseCount)  
   {  
      this.praiseCount = praiseCount;  
   }

      
   public boolean isPraise()  
   {  
      return praise;  
   }

   public void setPraise(boolean praise)  
   {  
      this.praise = praise;  
   }

      
   public List<UserInfo> getPraiseUsers()  
   {  
      return praiseUsers;  
   }

   public void setPraiseUsers(List<UserInfo> praiseUsers)  
   {  
      this.praiseUsers = praiseUsers;  
   }

      
   public List<CommentDetail> getComments()  
   {  
      return comments;  
   }

   public void setComments(List<CommentDetail> comments)  
   {  
      this.comments = comments;  
   }

      
   public ShopInfo getShopInfo()  
   {  
      return shopInfo;  
   }

   public void setShopInfo(ShopInfo shopInfo)  
   {  
      this.shopInfo = shopInfo;  
   }

      
   public boolean isHighlight()  
   {  
      return highlight;  
   }

   public void setHighlight(boolean highlight)  
   {  
      this.highlight = highlight;  
   }

}