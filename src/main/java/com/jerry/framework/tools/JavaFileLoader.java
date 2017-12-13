package com.jerry.framework.tools;


import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * java源文件加载器
 * Created with IntelliJ IDEA.
 * Class: JavaFileLoader
 * User: likang
 * Date: 15-11-24
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class JavaFileLoader {

    /**
     * 编译java源码，返回编译好的class
     * @param fullClassName
     * @param fileSrc
     * @return
     */
    public static Class javaSrcToClass(String fullClassName,final String fileSrc) {
        Class clazz = null;
        try {
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".")+1,fullClassName.length());
            JavaFileObject javaFile = new SimpleJavaFileObject(new URI(simpleClassName + ".java"), JavaFileObject.Kind.SOURCE) {
                @Override
                public CharSequence getCharContent(boolean arg)
                        throws IOException {
                    return fileSrc;
                }
            };
            String tempDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "tmp";
            File file = new File(tempDir);
            if(!file.exists()){
                file .mkdir();
            }
            //System.out.println(file);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", tempDir), null, Arrays.asList(javaFile));
            boolean success = task.call();
            if (success) {
                URL[] urls = new URL[]{new URL("file:/" + tempDir)};
                URLClassLoader classLoader = new URLClassLoader(urls);
                clazz = classLoader.loadClass(fullClassName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Method method = classl.getDeclaredMethod("main", String[].class);
//        String[] argsl = {null};
//        method.invoke(classl.newInstance(), argsl);



        return clazz;
    }
}
