package ru.otus.lessons.home05;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        ArrayList<String> l;
        SimpleArrayList<String> list = new SimpleArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Four");

        for (String s : list) {
            System.out.println(s);
        }

        printSeparateLine();
        list.add(2, "Three");
        for (String s : list) {
            System.out.println(s);
        }

        printSeparateLine();
        list.remove("Two");
        for (String s : list) {
            System.out.println(s);
        }

        printSeparateLine();
        list.remove(0);
        for (String s : list) {
            System.out.println(s);
        }

        printSeparateLine();
        ArrayList<String> newList = new ArrayList<>();
        newList.add("Ten");
        newList.add("Ten");
        newList.add("Ten");
        newList.add("Ten");
        newList.add("Ten");

        list.addAll(newList);
        for (String s : list) {
            System.out.println(s);
        }
    }

    private static void printSeparateLine() {
        System.out.println("===========");
    }
}
