package com.powermock;

import com.powermock.util.MockWhenNewUtil;

public abstract class InjectMockServiceImplParent implements InjectMockService{
    public String testParentMock() {
        return "parent";
    };
}
