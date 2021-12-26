package com.powermock;

public class InjectMockServiceImpl implements InjectMockService{
    private MockServiceBService serviceB;

    @Override
    public void test() {
        serviceB.testMock();
    }
}
