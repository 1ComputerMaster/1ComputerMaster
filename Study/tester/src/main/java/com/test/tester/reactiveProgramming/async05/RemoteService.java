package com.test.tester.reactiveProgramming.async05;

import com.test.tester.TesterApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 외부 서버의 요청을 대기하기 때문에 계속 처리가 되지 않고 막히고 있다.
 */
@SpringBootApplication
public class RemoteService {
    @RestController
    public class MyController {
        @GetMapping("/service")
        public String rest(int req) throws InterruptedException {
            Thread.sleep(2000);
            return "rest " + req;
        }
        @GetMapping("/service2")
        public String rest2(String req) throws InterruptedException {
            Thread.sleep(2000);
            return "rest " + req;
        }

    }
    public static void main(String[] args) {
        System.setProperty("server.port","8081");
        System.setProperty("server.tomcat.threads.max","1000");
        SpringApplication.run(TesterApplication.class, args);

    }
}
