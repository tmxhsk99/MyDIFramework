package com.kjh.di;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ContainerServiceTest {
    
    @Test
    @DisplayName("BookRepository 객체 생성 성공 테스트")
    public void getObject_BookRepository(){
        BookRespository bookRepository = ContainerService.getObject(BookRespository.class);
        Assertions.assertNotNull(bookRepository);
    }

    @Test
    @DisplayName("BookService 및 내부 BookRepository 객체 생성 성공 테스트")
    public void getObject_BookService(){
        Field[] fields = BookService.class.getFields();
        System.out.println("fields.lengths = " + fields.length);
        BookService bookService = ContainerService.getObject(BookService.class);
        Assertions.assertNotNull(bookService);
        Assertions.assertNotNull(bookService.bookRespository);
    }
    
}
