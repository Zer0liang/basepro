package com.chechetimes.wzl;

/**
 * author:WangZhaoliang
 * Date:2020/5/2 10:23
 */
public class FactoryTest {

    class JavaDevlopmenter extends FactoryInterface{

        @Override
        public void work() {
            System.out.println("Java 开发工程师");
        }
    }

    class CplusPlusDevelopmenter extends FactoryInterface {

        @Override
        public void work() {
            System.out.println("C++开发工程师");
        }
    }

    class PythonDevelopmenter extends FactoryInterface {

        @Override
        public void work() {
            System.out.println("python 开发工程师");
        }
    }

    class Factory {

        FactoryInterface getInstance(String objectName) {
            FactoryInterface factoryInterface = null;
            if (objectName.equals("java")) {
                factoryInterface = new JavaDevlopmenter();
            } else if (objectName.equals("c++")) {
                factoryInterface = new CplusPlusDevelopmenter();
            } else {
                factoryInterface = new PythonDevelopmenter();
            }
            return factoryInterface;
        }
    }

}
