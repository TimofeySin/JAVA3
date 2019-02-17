package lesson1;

import lesson1.fruits.Fruit;

import java.util.ArrayList;
import java.util.Arrays;

class Box<T extends Fruit> {
    private ArrayList<T> inputBox = new ArrayList<>();

    ////g. Не забываем про метод добавления фрукта в коробку.
    void addFruit(T newFruit) {
        inputBox.add(newFruit);
    }

    //   d. Сделать метод getWeight() который высчитывает вес коробки,
    float getWeight() {
        return inputBox.size() > 0 ? inputBox.size() * inputBox.get(0).getWeight() : 0f;
    }

    //e. Внутри класса коробка сделать метод compare,
    boolean compare(Box box) {
        return getWeight() - box.getWeight() < 0.00001;
    }

    private ArrayList<T> getInputBox() {
        return inputBox;
    }

    private void setInputBox(ArrayList<T> inputBox) {
        this.inputBox = inputBox;
    }

    //f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
    void putTooMuch(Box<T> outbox) {
        if (inputBox.size() > 0 && outbox.getInputBox().size() > 0 && inputBox.get(0).getClass() == outbox.getInputBox().get(0).getClass()) {
            inputBox.addAll(outbox.getInputBox());
            outbox.setInputBox(new ArrayList<>());
        }
    }
}
