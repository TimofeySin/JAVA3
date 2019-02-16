package lesson1;

import lesson1.fruits.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> inputBox = new ArrayList<>();

    ////g. Не забываем про метод добавления фрукта в коробку.
    public void addFruit(Fruit newFruit) {
        inputBox.add((T) newFruit);
    }

    //   d. Сделать метод getWeight() который высчитывает вес коробки,
    public float getWeight() {
        return inputBox.size() > 0 ? inputBox.size() * inputBox.get(0).getWeight() : 0f;
    }

    //e. Внутри класса коробка сделать метод compare,
    public boolean compare(Box box) {
        return getWeight() - box.getWeight() < 0.00001;
    }

    public ArrayList<T> getInputBox() {
        return inputBox;
    }

    public void setInputBox(ArrayList<T> inputBox) {
        this.inputBox = inputBox;
    }

    //f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
    public void putTooMuch(Box outbox) {
        inputBox.addAll(outbox.getInputBox());
        outbox.setInputBox(new ArrayList<>());
    }
}
