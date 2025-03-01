package com.unn.regex;

import com.unn.regex.utils.RegexUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VkrApplication {

    public static void main(String[] args) {
        System.out.println(RegexUtils.simplify("a**")); // a*
        System.out.println(RegexUtils.simplify("(a*)*")); // a*
        System.out.println(RegexUtils.simplify("a|a")); // a
        System.out.println(RegexUtils.simplify("a|ε")); // a|ε
        System.out.println(RegexUtils.simplify("εa")); // a
        System.out.println(RegexUtils.simplify("aε")); // a
        System.out.println(RegexUtils.simplify("(a|b)|c")); // a|b|c
        System.out.println(RegexUtils.simplify("a??")); // a?
        System.out.println(RegexUtils.simplify("a++")); // a+
        System.out.println(RegexUtils.simplify("(a)")); // a
        SpringApplication.run(VkrApplication.class, args);


    }

}
