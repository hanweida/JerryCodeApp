package powermock;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mock;

public class MockitoDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {
    private static final Map<Class,Object> mockObject = new HashMap<>();

    @Override
    protected void injectDependencies(final TestContext testContext) throws Exception {
        super.injectDependencies(testContext);
        init(testContext);
    }

    private void injectMock(Object bean) throws Exception {
        Field[] fields;
        /*找到所有的测试用例的字段*/
        if (AopUtils.isAopProxy(bean)){
            // 如果是代理的话，找到真正的对象
            if(AopUtils.isJdkDynamicProxy(bean)) {
                Class targetClass = AopTargetUtils.getTarget(bean).getClass();
                if (targetClass == null) {
                    // 可能是远程实现
                    return;
                }
                fields = targetClass.getDeclaredFields();
            } else { //cglib
                /*CGLIB的代理 不支持*/
                return;
            }

        }else {
            fields = bean.getClass().getDeclaredFields();
        }

        List<Field> injectFields = new ArrayList<>();

        /*判断字段上的注解*/
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation antt : annotations) {
                /*如果是Mock字段的,就直接注入Mock的对象*/
                if (antt instanceof org.mockito.Mock) {
                    // 注入mock实例
                    Object mockObj = mock(field.getType());
                    mockObject.put(field.getType(),mockObj);
                    field.setAccessible(true);
                    field.set(bean, mockObj);
                } else if (antt instanceof Autowired) {
                    /*需要把所有标注为autowired的找到*/
                    injectFields.add(field);
                }
            }
        }

        /*访问每一个被注入的实例*/
        for (Field field : injectFields) {
            field.setAccessible(true);

            /*找到每一个字段的值*/
            Object object = field.get(bean);
            Class targetClass = field.getType();
            if (!replaceInstance(targetClass,bean,field.getName())){
                //如果没有被mock过.那么这个字段需要再一次的做递归
                injectMock(object);
            }
        }
    }

    private boolean replaceInstance(Class targetClass, Object bean, String fieldName) throws Exception {
        boolean beMocked = false;
        for (Map.Entry<Class, Object> classObjectEntry : mockObject.entrySet()) {
            Class type = classObjectEntry.getKey();
            if (type.isAssignableFrom(targetClass)){
                //如果这个字段是被mock了的对象.那么就使用这个mock的对象来替换
                ReflectionTestUtils.setField(AopTargetUtils.getTarget(bean), fieldName, classObjectEntry.getValue());
                beMocked = true;
                break;
            }
        }
        return beMocked;
    }

    private void init(final TestContext testContext) throws Exception {
        Object bean = testContext.getTestInstance();
        injectMock(bean);
    }
}
