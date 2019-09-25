package com.rybickim.javaquiz;

import com.rybickim.javaquiz.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class JavaQuizApplication {

//    private static final Logger logger = LoggerFactory.getLogger(JavaQuizApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JavaQuizApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner quizEntities(@Qualifier("quizRepo") QuizEntityRepository repository){
//        return args -> {
//            repository.save(new QuizEntity("What is Encapsulation?","Encapsulation provides objects with the ability to hide their internal characteristics and behavior. Each object provides a number of methods, which can be accessed by other objects and change its internal data. In Java, there are three explicit access modifiers: public, private and protected. Each modifier imposes different access rights to other classes, either in the same or in external packages. Some of the advantages of using encapsulation are listed below:\n\n\u2022 the internal state of every object is protected by hiding its attributes\n\u2022 it increases usability and maintenance of code, because the behavior of an object can be independently changed or extended\n\u2022 it improves modularity by preventing objects from interacting with each other in an undesired way"));
//            repository.save(new QuizEntity("What is Polymorphism?","Polymorphism is the ability of programming languages to present the same interface for differing underlying data types. A polymorphic type is a type whose operations can also be applied to values of some other type."));
//            repository.save(new QuizEntity("What is Inheritance?","Inheritance provides an object with the ability to acquire the fields and methods of another class, called the base class. Inheritance supplies re\u2011usability of code and can be used to add additional features to an existing class, without modifying it."));
//            repository.save(new QuizEntity("What is Abstraction?","Abstraction is the process of separating ideas from specific instances and thus, of developing classes in terms of their own functionality, instead of their implementation details. Java supports the creation and existence of abstract classes that expose interfaces, without including the actual implementation of all methods. The abstration technique aims to separate the implementation details of a class from its behavior."));
//
//            logger.debug("Quizzes found with findAll():");
//            logger.debug("-------------------------------");
//            repository.findAll().forEach(quiz -> logger.debug(quiz.toString()));
//            logger.debug("");
//        };
//    }
}
