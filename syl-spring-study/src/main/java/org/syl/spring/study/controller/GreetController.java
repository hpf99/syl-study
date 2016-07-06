package org.syl.spring.study.controller;

import mx.bi.hbase.api.Example;
import mx.bi.hbase.api.PhoenixClient;
import mx.bi.hbase.utils.FastJsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.syl.spring.study.model.Greeting;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Mtime on 2016/6/23.
 */

@RestController
public class GreetController {

    private static final String template = "hello , %s";

    private final AtomicLong counter = new AtomicLong();


    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name",defaultValue = "world") String name){
        PhoenixClient instance = PhoenixClient.getInstance();
        List<Example> select = instance.select("select * from example", Example.class);
        System.out.println(FastJsonUtil.bean2Json(select));


        return new Greeting(counter.incrementAndGet(),String.format(template,name));
    }

}
