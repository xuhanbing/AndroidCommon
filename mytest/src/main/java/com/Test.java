package com;

import android.text.TextUtils;

import com.common.util.ReflectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by hanbing
 */
public class Test {

    static class User {
        String name;
        int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "name = " + name + ", age = " + age;
        }
    }

    static class MyAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

        @Override
        public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
            System.out.println("serialize");
            return null;
        }

        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            System.out.println("deserialize");

            User user = new User();

            if (json.isJsonObject()) {
                user.setName(((JsonObject) json).get("name").getAsString());

                JsonElement extra = ((JsonObject) json).get("extra");

                if (extra.isJsonObject()) {
                    user.setAge((int) ((JsonObject)extra).get("age").getAsDouble());
                }
            }

            return user;
        }
    }

    public static void main(String[] args) {

       B b = new B();

        int value = 20;


        try {

            Method method = getMethod(b.getClass(), "get", new Class[]{int.class});

            method.setAccessible(true);
            int newValue = (int) method.invoke(b, 222);

            b.print(newValue);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        {
//            String string = getValue(b, "name", "default name");
//
//            System.out.println("name1=" + string);
//
//            setValue(b, "name", "this is new name");
//
//            string = getValue(b, "name", "default name2");
//
//            System.out.println("name2=" + string);
//
//        }
//
//        {
//            int age = getValue(b, "age", 0);
//
//            System.out.println("age1=" + age);
//
//            setValue(b, "age", 22);
//
//            age = getValue(b, "age", 0);
//
//            System.out.println("age2=" + age);
//        }
    }


    public static <T> T getValue(Object object, String fieldName, T defaultValue) {

        if (null == object)
            return defaultValue;


        try {
            Field field = getField(object.getClass(), fieldName);

            boolean accessible = field.isAccessible();

            field.setAccessible(true);

            T value = (T) field.get(object);

            field.setAccessible(accessible);

            return value;

        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return defaultValue;
    }

    public static void setValue(Object object, String fieldName,  Object newValue) {

        if (null == object)
            return ;

        try {
            Field field = getField(object.getClass(), fieldName);

            boolean accessible = field.isAccessible();

            field.setAccessible(true);

            field.set(object, newValue);

            field.setAccessible(accessible);

        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static Field getField(Class cls, String fieldName) {

        if (null == cls)
            return null;

            try {
                return  cls.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return getField(cls.getSuperclass(), fieldName);
            }

    }

    public static Method getMethod(Class cls, String methodName, Class... classes) {

        if (null == cls)
            return null;

        try {
            return  cls.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            return getMethod(cls.getSuperclass(), methodName, classes);
        }

    }


    public static <T> T parse() {


        return (T) new Gson().fromJson("", Test.class);
    }

    static class A  {

        String name = "hello";
        int age = 10;

        private  int get(String string) {
            return 2110;
        }

        private  int get(int age) {
            return age;
        }

    }

    static class B extends A{


        public void print(int value) {
            System.out.println("" + value);
        }
    }
}