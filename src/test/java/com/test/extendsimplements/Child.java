package com.test.extendsimplements;

import org.junit.Test;

public class Child extends Parent{

  void invokeChild(){
    invoke();
  }

  void invokeByParent(){
    System.out.println("invoke Child");
  }

  @Test
  public void test(){
    Child child = new Child();
    child.invokeChild();
  }

}
