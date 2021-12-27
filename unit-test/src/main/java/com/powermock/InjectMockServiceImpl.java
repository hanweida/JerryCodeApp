package com.powermock;

import com.powermock.util.MockWhenNewUtil;

public class InjectMockServiceImpl implements InjectMockService{
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


}
