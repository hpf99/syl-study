package org.syl.spring.study.test;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.syl.spring.study.service.GreetService;
import org.syl.spring.study.service.HelloService;
import org.syl.spring.study.service.HelloServiceImpl;

/**
 * Created by Mtime on 2016/6/23.
 */
public class GreetTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        GreetService bean = app.getBean(GreetService.class);
        String hello = bean.sayHello();
        System.out.println(hello);
        ConfigurableListableBeanFactory beanFactory = app.getBeanFactory();
        beanFactory.registerSingleton("helloService", HelloServiceImpl.class);
        HelloService h = (HelloService) app.getBean("helloService");
        h.sayGoodbye();

    }

}
