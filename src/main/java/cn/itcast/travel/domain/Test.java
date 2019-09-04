package cn.itcast.travel.domain;

/**
 * @author fly
 * @date 2019/9/2
 */
public class Test {
    public static void main(String[] args) {
        int a = fun();
        System.out.println("a = " + a);
    }

    public static int fun() {
        int a = 0;
        try {
            a = 3/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
