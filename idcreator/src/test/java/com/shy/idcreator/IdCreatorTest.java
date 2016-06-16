package com.shy.idcreator;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 29517 on 2016-6-16.
 */
public class IdCreatorTest {

    @Test
    public void test(){
        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            pool.execute(new Thread(new GetIdThread("线程"+i)));
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
        }
    }

    class GetIdThread implements Runnable {
        private String threadName;

        public GetIdThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                System.out.println(threadName+"，id:"+IdCreator.getNumId());
            }
        }
    }
}
