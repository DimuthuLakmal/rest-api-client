package dev.assignment.enactor.util;

import com.github.javafaker.Faker;
import dev.assignment.enactor.config.Constants;

import java.util.ArrayList;

public class FakeDataGenerator {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> emails = new ArrayList<>();

    public FakeDataGenerator(int requestCount) {
        Faker faker = new Faker();
        System.out.println(Constants.START_GENERATING_DATA);
        for(int i=0; i<requestCount; i++) {
            String name = faker.name().name();
            String email = faker.name().firstName().replaceAll("\\s+","") + "@gmail.com";
            names.add(name);
            emails.add(email);
            System.out.println(String.format("Name: %s, Email: %s", name, email));
        }
        System.out.println(Constants.END_GENERATING_DATA);
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }
}
