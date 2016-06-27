package com.hanbing.dianping.model;
/***********************************************************************
 * Module:  Category.java
 * Author:  hzbc-01
 * Purpose: Defines the Class Category
 ***********************************************************************/

import java.util.*;
import java.io.Serializable;

public class Category implements Serializable {
   //id 
   private int id;
   //名称 
   private int name;
   //索引 
   private int index;
   //父分类 
   private Category parent;
   //子分类 
   private List<Category> childs;

      
   public int getId()  
   {  
      return id;  
   }

   public void setId(int id)  
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

      
   public int getIndex()  
   {  
      return index;  
   }

   public void setIndex(int index)  
   {  
      this.index = index;  
   }

      
   public Category getParent()  
   {  
      return parent;  
   }

   public void setParent(Category parent)  
   {  
      this.parent = parent;  
   }

      
   public List<Category> getChilds()  
   {  
      return childs;  
   }

   public void setChilds(List<Category> childs)  
   {  
      this.childs = childs;  
   }

}