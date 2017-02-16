package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.omg.CORBA.OBJ_ADAPTER;

import java.io.IOException;
import java.lang.reflect.Type;

public class MyClass {


    public  static void main(String[] args) {


        String jsonString = "{\"name\":\"hanbing\", \"age\":\"123\", \"info\":[]}";
        String jsonString2 = "{\"name\":\"hanbing\", \"age\":\"123\", \"phone\":\"150\", \"info\":{\"address\":\"123\"}}";


        GsonBuilder gsonBuilder = new GsonBuilder();



        gsonBuilder.registerTypeHierarchyAdapter(Object.class,  new TypeAdapter<Object>() {
            @Override
            public void write(JsonWriter out, Object value) throws IOException {

            }

            @Override
            public Object read(JsonReader in) throws IOException {

                JsonToken peek = in.peek();

                TypeAdapter objectTypeAdapter = ObjectTypeAdapter.FACTORY.create(new Gson(), new TypeToken<Object>(){});

                if (peek == JsonToken.BEGIN_ARRAY) {
                    return null;
                }

                return objectTypeAdapter.read(in);

            }
        });

        final Gson gson = gsonBuilder.create();

        User user = null;

        user = gson.fromJson(jsonString2, User.class);
        print(user);

        user= gson.fromJson(jsonString, User.class);
        print(user);
    }


    public static void print(Object string) {
        System.out.println(string);
    }

    public static class User {
        public String name;
        public int age;
        public String phone;
        public Info info;

        public class Info {
            public String address;

            @Override
            public String toString() {
                return ""+address;
            }
        }
        @Override
        public String toString() {
            return String.format("name:%s, age:%d, phone:%s, info:%s", name, age, phone, info);
        }
    }
}
