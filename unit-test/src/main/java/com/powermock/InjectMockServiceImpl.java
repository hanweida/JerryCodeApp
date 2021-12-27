package com.powermock;

import com.powermock.util.MockWhenNewUtil;

public class InjectMockServiceImpl extends InjectMockServiceImplParent{
    private MockServiceBService serviceB;

    @Override
    public void test() {
        serviceB.testMock();
    }

    @Override
    public String testWhennew() {
        MockWhenNewUtil mock = new MockWhenNewUtil();
        return mock.getMockWhenNew();
    }

    @Override
    public String testParent() {
        return testParentMock();
    }

    @Override
    public String testStatic() {
        return staticMethod();
    }

    static String staticMethod() {
        return testStaticMock().getFileName();
    }

}
