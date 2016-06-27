package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  UserInfo.java
 * Author:  hzbc-01
 * Purpose: Defines the Class UserInfo
 ***********************************************************************/

import java.io.Serializable;

public class UserInfo implements Serializable {
   //id 
   private String id;
   //昵称 
   private int name;
   //性别 
   private int gender;
   //头像 
   private String portraitUrl;
   //等级 
   private int level;
   //是否vip 
   private boolean vip;
   //城市 
   private String city;
   //生日 
   private String birthday;
   //星座 
   private String constellation;
   //粉丝数 
   private int fansCount;
   //关注数 
   private int attentionCount;
   //是否关注 
   private boolean attended;

      
   public String getId()  
   {  
      return id;  
   }

   public void setId(String id)  
   {  
      this.id = id;  
   }

      
   public int getName()  
   {  
      return name;  
   }

   public void setName(int name)  
   {  
      this.name = name;  
   }

      
   public int getGender()  
   {  
      return gender;  
   }

   public void setGender(int gender)  
   {  
      this.gender = gender;  
   }

      
   public String getPortraitUrl()  
   {  
      return portraitUrl;  
   }

   public void setPortraitUrl(String portraitUrl)  
   {  
      this.portraitUrl = portraitUrl;  
   }

      
   public int getLevel()  
   {  
      return level;  
   }

   public void setLevel(int level)  
   {  
      this.level = level;  
   }

      
   public boolean isVip()  
   {  
      return vip;  
   }

   public void setVip(boolean vip)  
   {  
      this.vip = vip;  
   }

      
   public String getCity()  
   {  
      return city;  
   }

   public void setCity(String city)  
   {  
      this.city = city;  
   }

      
   public String getBirthday()  
   {  
      return birthday;  
   }

   public void setBirthday(String birthday)  
   {  
      this.birthday = birthday;  
   }

      
   public String getConstellation()  
   {  
      return constellation;  
   }

   public void setConstellation(String constellation)  
   {  
      this.constellation = constellation;  
   }

      
   public int getFansCount()  
   {  
      return fansCount;  
   }

   public void setFansCount(int fansCount)  
   {  
      this.fansCount = fansCount;  
   }

      
   public int getAttentionCount()  
   {  
      return attentionCount;  
   }

   public void setAttentionCount(int attentionCount)  
   {  
      this.attentionCount = attentionCount;  
   }

      
   public boolean isAttended()  
   {  
      return attended;  
   }

   public void setAttended(boolean attended)  
   {  
      this.attended = attended;  
   }

}