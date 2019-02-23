package lesson1.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        example4();
    }

    private static void example4() {
        NumberGenerator rg = new NumberGenerator();
        // Коллекция с элементами типа Integer
        // ОШИБКА !!! Мы можем передавать в fillCollection только коллекцию, параметризованную ПРЕДКОМ Number,
        // но не наследником!!!
        //rg.fillCollection(integerCollection, 10);
        // Коллекция с элементами типа Number
        Collection<Number> numberCollection = new ArrayList<>();
        rg.fillCollection(numberCollection,10); // OK !!!
        System.out.println(numberCollection);
        // Коллекция с элементами типа Object
        Collection<Object> objectCollection = new ArrayList<>();
        rg.fillCollection(objectCollection,10); // OK !!!
        System.out.println(objectCollection);
        // Использование метода printNumbers с extends
        Collection<Integer> integerCollection = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        rg.printNumbers(integerCollection);
    }


    private static void example3() {
        float f = 1.25f;
        double d = 1.25;
        BigDecimal bd = BigDecimal.valueOf(1.1);

        System.out.println(bd);

        //    N:      7      6      5     4    3   2   1   0
        //  2^N:    128     64     32    16    8   4   2   1
        // 87(десят.) 0      1      0     1    0   1   1   1

        //   N:           -1    -2     -3     -4       -5   -6  -7  -8
        // 2^N:           0.5   0.25   0.125   0,0625  .. .... .... ...
        // 1.1(десятичн)  1.00010010000010000001.... (двоичн) != 1.1 (десятичн)
    }

    private static void example2() {
        Stat<Long> stat = new Stat<>(1L, 2L, 3L, 4L, 5L);
        Stat<Long> stat1 = new Stat<>(0L, 3L, 3L, 4L, 5L);
        System.out.println(stat.sum());
        System.out.println(stat1.sum());
        System.out.println(stat.isSameSum(stat1));
        Stat<Float> fStat = new Stat<>(0.5f, 2f, 3f, 4f, 5.5f);
        System.out.println(stat.isSameSum(fStat));
        System.out.println(fStat.sum());
        Stat<Double> dStat = new Stat<>(new Double[]{1.5, 2d, 3., 4.0, 5.5});
        System.out.println(dStat.sum());
    }

    private static void example1() {
        Box<Integer, Float> iBox = new Box<>();
        Box<String, Object> sBox = new Box<>();
        String s = "hello";
        sBox.setObj(s);
        iBox.setObj(3);
        // iBox.setObj1(1.3f);
        System.out.println(iBox.getObj());
        System.out.println(sBox.getObj());
    }
}
