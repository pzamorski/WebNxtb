package xtb;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.ToString;

import java.io.FileNotFoundException;
import java.io.FileReader;

@ToString
public class User {


    private long id;
    private String password;

    private User getUserFromGson() {
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("user.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(reader, User.class);
    }

//    public long getId() {
//        return getUserFromGson().id;
//    }
//
//    public String getPassword() {
//        return getUserFromGson().password;
//    }


    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public User(long id, String password) {
        this.id = id;
        this.password = password;
    }
}
