package com.example.officeroom;

import java.util.Random;

public class UniqueIdGenerator {

    private final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    public String generateID(){
        StringBuilder result = new StringBuilder();
        for(int i=0;i<8;i++){
            result.append(letters.charAt(new Random().nextInt(letters.length())));
        }
        return result.toString();
    }
}
