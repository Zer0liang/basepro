package com.chechetimes.wzl;

import org.junit.Test;

/**
 * author:WangZhaoliang
 * Date:2020/7/19 16:45
 */
public class ExtendTest {

    class Father {

        public Father() {
            methodA();
        }

        public void methodA() {
            System.out.println("父类methodA（）方法");
        }

    }

    class Son extends Father {

        public Son() {
            methodA();
        }

        @Override
        public void methodA() {
            System.out.println("子类methodA（）方法");
        }

    }

    @Test
    public void testExtend() {
        Father son = new Son();
    }

}
