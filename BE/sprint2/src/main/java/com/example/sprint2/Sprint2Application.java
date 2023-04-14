package com.example.sprint2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sprint2Application {

    public static void main(String[] args) {
        SpringApplication.run(Sprint2Application.class, args);
//        for (int i = 0; i < 100; i++) {
//            int fib = fibonacci(i);
//            if (fib >= 100) {
//                break;
//            }
//            System.out.print(fib + " ");
//        }
    }


//        public static int fibonacci(int n) {
//            if (n == 0) {
//                return 0;
//            } else if (n == 1) {
//                return 1;
//            } else {
//                return fibonacci(n - 1) + fibonacci(n - 2);
//            }
//        }


}
