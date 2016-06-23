package org.syl.cat.study;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Mtime on 2016/6/21.
 */
public class CatTest {


    public static void main(String[] args) {
        Transaction t = Cat.newTransaction("TEST", "test.method");

        Cat.logEvent("Method_test", "good");
        Cat.logEvent("Method_event", "bad");

        int nextInt = new Random().nextInt(3);

        if (nextInt % 2 == 0) {
            t.setStatus(Transaction.SUCCESS);
        } else {
            t.setStatus(String.valueOf(nextInt));
        }

        t.complete();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
