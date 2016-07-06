package org.syl.spring.study.service;

/**
 * Created by Mtime on 2016/6/23.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayGoodbye() {
        System.out.println("goodbye!");
    }
}
