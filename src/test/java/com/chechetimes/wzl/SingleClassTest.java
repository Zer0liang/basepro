package com.chechetimes.wzl;

/**
 * author:WangZhaoliang
 * Date:2020/5/1 10:53
 */
public class SingleClassTest {

    /**
     * 单例模式-饿汉
     */
    static class EhanSingle {
        private static final EhanSingle ehanSingle = new EhanSingle();

        private EhanSingle() {
        }

        public static EhanSingle getEhanSingle() {
            return ehanSingle;
        }
    }

    static class LhanSingle {
        private static volatile LhanSingle lhanSingle = null;

        private LhanSingle() {
        }

        public static LhanSingle getLhanSingle() {
            if (lhanSingle == null) {
                synchronized (LhanSingle.class) {
                    if (lhanSingle == null) {
                        lhanSingle = new LhanSingle();
                    }
                }
            }
            return lhanSingle;
        }
    }

    public static void main(String[] args) {
        EhanSingle ehanSingle = EhanSingle.getEhanSingle();
        System.out.println(ehanSingle);
        EhanSingle ehanSingle2 = EhanSingle.getEhanSingle();
        System.out.println(ehanSingle2);

        LhanSingle lhanSingle = LhanSingle.getLhanSingle();
        System.out.println(lhanSingle);
    }

}
