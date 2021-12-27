package com.powermock.util;

public class MockWhenNewUtil {
    private String mockWhenNew;

    public MockWhenNewUtil() {
        this.mockWhenNew = "whenNew";
    }

    public MockWhenNewUtil(String mockWhenNew) {
        this.mockWhenNew = mockWhenNew;
    }

    public String getMockWhenNew() {
        return mockWhenNew;
    }

    public void setMockWhenNew(String mockWhenNew) {
        this.mockWhenNew = mockWhenNew;
    }
}
