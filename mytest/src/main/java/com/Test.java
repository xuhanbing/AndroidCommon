package com;

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

        Gson gson = new Gson();

        String string = "{'name':'hanbing', 'extra':{'age':'22'}}";


        User user = gson.fromJson(string, User.class);

        System.out.println(user);

    }

    public static <T> T parse() {


        return (T) new Gson().fromJson("", Test.class);
    }
}
