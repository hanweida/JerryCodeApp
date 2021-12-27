package com.powermock;

import com.powermock.util.FileSystem;
import com.powermock.util.HDFSFileSystemUtil;

public abstract class InjectMockServiceImplParent implements InjectMockService{
    public String testParentMock() {
        return "parent";
    };

    static FileSystem testStaticMock(){
        return HDFSFileSystemUtil.getHDFSFile();
    }
}
