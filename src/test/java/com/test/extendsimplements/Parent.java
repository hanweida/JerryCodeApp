package com.test.extendsimplements;

public class Parent {
  void invoke(){
    invokeByParent();
  };

  void invokeByParent(){
    System.out.println("Parent invokeByParent");
  }
}
