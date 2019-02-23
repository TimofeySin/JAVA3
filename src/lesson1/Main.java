package lesson1;

import lesson1.fruits.Apple;
import lesson1.fruits.Orange;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        int cell1 = 2;
        int cell2 = 4;
        Object[] arrayExercise = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("Было: " + Arrays.toString(arrayExercise));
        exercise1(arrayExercise, cell1, cell2);
        System.out.println("Стало: " + Arrays.toString(arrayExercise));
        ArrayList<Object> list = exercise2(arrayExercise);
        System.out.println("А теперь это ArrayList: " + list.toString());

        //3. Большая задача:
        Box<Apple> boxApple1 = new Box<>();
        Box<Orange> boxOrange1 = new Box<>();
        Box<Apple> boxApple2 = new Box<>();
        Box<Orange> boxOrange2 = new Box<>();
        for (int i = 0; i < 3; i++) {
            boxApple1.addFruit(new Apple());
            boxApple2.addFruit(new Apple());
        }
        for (int i = 0; i < 2; i++) {
            boxOrange1.addFruit(new Orange());
            boxOrange2.addFruit(new Orange());
        }

        System.out.println("Вес коробки с яблоками: " + boxApple1.getWeight());
        System.out.println("Вес коробки с апельсинами: " + boxOrange1.getWeight());
        System.out.println("Коробки равны: " + boxOrange1.compare(boxApple1));
        boxApple1.putTooMuch(boxApple2);
        boxOrange1.putTooMuch(boxOrange2);
        System.out.println("Вес коробки 1 с яблоками: " + boxApple1.getWeight());
        System.out.println("Вес коробки 1 с апельсинами: " + boxOrange1.getWeight());
        System.out.println("Вес коробки 2 с яблоками: " + boxApple2.getWeight());
        System.out.println("Вес коробки 2 с апельсинами: " + boxOrange2.getWeight());
    }

    // 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа)
    private static void exercise1(Object[] arrayExercise1, int cell1, int cell2) {
        Object temp = arrayExercise1[cell1];
        arrayExercise1[cell1] = arrayExercise1[cell2];
        arrayExercise1[cell2] = temp;
    }

    ////2. Написать метод, который преобразует массив в ArrayList;
    private static ArrayList<Object> exercise2(Object[] arrayExercise1) {
        return new ArrayList<>(Arrays.asList(arrayExercise1));
    }

}