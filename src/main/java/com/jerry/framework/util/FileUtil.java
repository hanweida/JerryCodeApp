package com.jerry.framework.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtil {
    public static Path classpath(String name) {
        try {
            final URL url = FileUtil.class.getResource(name);
            return (url != null) ? Paths.get(url.toURI()) : null;
        } catch (URISyntaxException e) {
            return null;
        }
    }
 
    private FileUtil() {
    }
}