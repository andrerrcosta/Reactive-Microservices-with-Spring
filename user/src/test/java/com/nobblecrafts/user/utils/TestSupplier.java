package com.nobblecrafts.user.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.nobblecrafts.user.models.User;

public class TestSupplier {

    private static List<String> names;

    static {
        names = new ArrayList<>();
        names.add("Abel");
        names.add("Bernardo");
        names.add("Bianca");
        names.add("Daniele");
        names.add("Francine");
        names.add("Igor");
        names.add("Julieta");
        names.add("Mateus");
        names.add("Pamela");
        names.add("Tadeu");
    }

    public static User createRandomValidUser() {
        var random = new Random();
        var i = random.nextInt(9);
        return User.builder()
            .id(UUID.randomUUID().toString())
            .name(TestSupplier.names.get(i).toLowerCase())
            .email(TestSupplier.names.get(i) + "@nobblecrafts.com")
            .build();
    }

    public static User createInvalidRandomUser() {
        var random = new Random();
        var i = random.nextInt(9);
        return User.builder()
            .name(TestSupplier.names.get(i).toLowerCase())
            .email(TestSupplier.names.get(i) + "nobblecrafts.com")
            .build();
    }

    public static List<User> createListOfRandomValidUsers(int users) {
        var output = new ArrayList<User>();
        for (var i = 0; i < users; i++) {
            output.add(TestSupplier.createRandomValidUser()
                .withId(UUID.randomUUID().toString()));
        }
        return output;
    }

}
