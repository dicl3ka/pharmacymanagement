package org.mavensample;

import org.apache.commons.lang3.StringUtils;

public class MavenDemo {

    public static void main(String[] args) {

        String testString = "";

        if (StringUtils.isEmpty(testString)) {
            System.out.println("The string is empty");
        } else {
            System.out.println("The string is not empty");
        }

    }
}