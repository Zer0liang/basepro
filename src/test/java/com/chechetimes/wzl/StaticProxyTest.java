package com.chechetimes.wzl;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 静态
 * author:WangZhaoliang
 * Date:2020/5/3 9:42
 */
public class StaticProxyTest {

    interface IStar {
        void sing();
    }

    class LDHStar implements IStar {
        @Override
        public void sing() {
            System.out.println("刘德华故事吧");
        }
    }

    class AgentStar implements IStar {

        private IStar iStar;

        public AgentStar(IStar iStar) {
            this.iStar = iStar;
        }

        @Override
        public void sing() {
            System.out.println("==开唱前准备，刘德华准备原唱");
            iStar.sing();
            System.out.println("==演唱会结束");
        }
    }

    /**
     * 静态代理模式：符合开闭原则；
     * 目标对象和代理对象都需要实现同一个接口， 如果一旦有很多代理类，很多方法，都需要维护
     */
    @Test
    public void agentSing() {
        IStar ldhStar = new LDHStar();
        AgentStar agentStar = new AgentStar(ldhStar);
        agentStar.sing();
    }

    /**
     * JDK动态代理，调用反射包里面的newProxyInstance方法生成代理对象
     * 只需要目标对象实现接口即可
     */
    class ActiveAgent {
        private Object object;

        public ActiveAgent(Object object) {
            this.object = object;
        }

        public Object getAgentObj() {
            return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                    object.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("==开始准备生产代理对象");
                        Object returnVal = method.invoke(object, args);
                        System.out.println("==演唱会结束");
                        return returnVal;
                    }
            );
        }
    }

    @Test
    public void testActiveAgent() {
        IStar iStar = new LDHStar();
        IStar agentStar = (IStar) new ActiveAgent(iStar).getAgentObj();
        agentStar.sing();
    }

    static class CglibStar {

        public void sing() {
            System.out.println("其实我是代唱----");
        }

    }

    class cGlibActiveAgent implements MethodInterceptor {

        public Object getAgentObj(Class clazz) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(new cGlibActiveAgent());
            return enhancer.create();
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("==cgLib动态代理开始");
            methodProxy.invokeSuper(obj, objects);
            System.out.println("==cglib动态代理结束");
            return null;
        }
    }

    @Test
    public void testCglibActiveAgent() {
        CglibStar agentStar = (CglibStar) new cGlibActiveAgent().getAgentObj(CglibStar.class);
        agentStar.sing();

    }

}
