package com.chechetimes.wzl;

/**
 * author:WangZhaoliang
 * Date:2020/7/22 10:03
 */
public class InnerClassSingleTon {

    private static class SingleTonHolder {
        private static InnerClassSingleTon innerClassSingleTon = new InnerClassSingleTon();
    }

    private InnerClassSingleTon() {}

    public InnerClassSingleTon getInnerSingleTon() {
        return SingleTonHolder.innerClassSingleTon;
    }

}
