package lesson1.example;

public class Box<T, K> {
    private T obj;
    private K obj1;

    public void setObj1(K o) {
        obj1 = o;
    }

    public K getObj1() {
        return obj1;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
