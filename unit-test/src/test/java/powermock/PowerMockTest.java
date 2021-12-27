package powermock;

import com.powermock.InjectMockServiceImpl;
import com.powermock.MockServiceBServiceImpl;
import com.powermock.util.MockWhenNewUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.agent.PowerMockAgent;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.util.Assert;

/**
 * @Description: 本类整理单测各种方法，将单测的各种情况都Mock到；争取做到通用Mock
 *
 * <p>说明：注：在使用@PrepareForTest注解时，会和jacoco产生冲突导致出现覆盖率为0的情况，
 *  因此需要使用以下方法（去掉@RunWith(PowerMockRunner.class)注解，使用@Rule注解，强烈建议这样使用
 *  </p>
 *
 * <p>一个编译期常量，跟ThreadLocal，是没办法跟powermock对象一起创建的
 *    private static final ThreadLocal<Shot> dataFomat = new ThreadLocal<Shot>
 * </p>
 *
 * <p>
 *     不推荐使用@InjectMocks注解
 *     不推荐使用@InjectMocks注解实现Mock/Spy对象的自动注入，当需要使用与@InjectMocks注解类似的功能时，可以使用Whitebox.setInternalState()等方法通过反射的方式对成员变量进行替换
 *
 *      使用不方便:对于@InjectMocks注解指定的类的实例，需要使用@Mock/@Spy注解指定每个成员变量，未指定的成员变量值会为null；
 *      执行成员变量的真实方法不方便:对于@InjectMocks注解指定的类的实例，注入的成员变量为Mock/Spy对象，当需要执行成员变量的真实方法时不方便。
 * </p>
 *
 * <p>
 *     MockitoAnnotations.initMocks()方法
 *     在测试类级别使用@PrepareForTest注解时，若未执行MockitoAnnotations.initMocks()方法，测试代码执行时使用了@InjectMocks、@Mock注解的对象为null，
 *     使用@Spy注解的对象不是null；在测试代码中执行MockitoAnnotations.initMocks()方法后，@InjectMocks、@Mock注解对应的对象会变为非null。
 *     若未在测试类中使用@InjectMocks、@Mock注解，则不需要调用MockitoAnnotations.initMocks()方法。
 * </p>
 *
 * <p>
 *     创建构造函数为私有的类的实例
 *     对于构造函数为私有的类，当需要创建实例时，可以调用Whitebox.newInstance()或Whitebox.invokeConstructor()方法
 *     TestPrivateConstructor2 testPrivateConstructor2 = Whitebox.newInstance(TestPrivateConstructor2.class);
 *     TestPrivateConstructor2 testPrivateConstructor2 = Whitebox.invokeConstructor(TestPrivateConstructor2.class);
 * </p>
 *
 * <p>
 *     执行私有方法
 *     当需要执行私有方法时，可使用Whitebox.invokeMethod()方法，支持执行静态私有方法及非静态私有方法
 * </p>
 *
 * <p>
 *     当使用PowerMockito.whenNew方法时，必须加注解@PrepareForTest和@RunWith。注解@PrepareForTest里写的类是需要mock的new对象代码所在的类。
 *     当需要mock final方法的时候，必须加注解@PrepareForTest和@RunWith。注解@PrepareForTest里写的类是final方法所在的类
 *     当需要mock静态方法的时候，必须加注解@PrepareForTest和@RunWith。注解@PrepareForTest里写的类是静态方法所在的类
 *     当需要mock私有方法的时候, 只是需要加注解@PrepareForTest，注解里写的类是私有方法所在的类
 *     当需要mock系统类的静态方法的时候，必须加注解@PrepareForTest和@RunWith。注解里写的类是需要调用系统方法所在的类
 * </p>
 *
 * <p>
 *     spy:Spy spy = PowerMockito.spy(new Spy());
 *         PowerMockito.doReturn(false).when(spy).isTrue_1();
 *         spy public方法时需要使用PowerMockito.spy(方法所在类的实例)获取spy出来的对象，这里称之为spy实例，不对spy实例进行任何操作的情况下，
 *         spy实例与真实实例是完全一样的。同时由于spy实例与真实实例完全一样，因此在对spy实例进行打桩时使用doReturn()和thenReturn()是存在差别的：
 *         使用doReturn(返回值)时不会执行真实方式，直接返回返回值；使用thenReturn(返回值)时会先执行一遍真实方法，然后返回返回值。
 *         通常情况下spy需要配合doReturn()使用，用于抑制真实方法的执行，防止执行真实方法时报错
 * </p>
 * @Author:weidahan  
 * @Date: 2021/12/26
 */

/**
 * 在使用@PrepareForTest注解时，会和jacoco产生冲突导致出现覆盖率为0的情况，因此需要使用以下方法（去掉@RunWith(PowerMockRunner.class)注解，使用@Rule注解，强烈建议这样使用）
 * @RunWith(PowerMockRunner.class)
 * 缺点
 */
//@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*", "javax.swing.*"})
@PrepareForTest(InjectMockServiceImpl.class)
/**
 * 即为Mock初始化的监听器。它会查询这个测试用例的所有的成员变量。找到被标记为@Mock的变量，然后模拟出来。而后，又找到所有被标注为@Autowired的成员变量，判断变量类型是否是需要被Mock的。
 */
//@TestExecutionListeners({ MockitoDependencyInjectionTestExecutionListener.class})
/**
 * 将powermock整合到spring容器中
 */
//@PowerMockRunnerDelegate(SpringRunner.class)
public class PowerMockTest {
    /**
     * @InjectMocks
     * 缺点：
     */
    @InjectMocks
    InjectMockServiceImpl injectMockService;

    /**
     * 这里的@InjectMocks和@Autowired功能完完全全一样，唯一不同的是，@InjectMocks可以使oneDependency这个Mock对象自动注入到someHandler这个对象中。
     * 注意：①@InjectMocks所表示的对象及someHandler是一个普通的对象 ②Mock所表示的对象及oneDependency是一个Mock对象
     */
    @Mock
    // Mock注解，配合 MockitoAnnotations.initMocks(this); 或者 @InjectMocks
    MockServiceBServiceImpl mockServiceBService;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    /**
     * 在某些情况下，可能需要在运行测试之前手动启动代理
     */
    static {
        PowerMockAgent.initializeIfNeeded();
    }

    @Before
    public void init() {
        // 两种方法对 Mock对象进行模拟
        // 1. 对注解了@Mock的对象进行模拟
        MockitoAnnotations.initMocks(this);
        // 2. 使用手动的方式进行Mock
        //mockServiceBService = Mockito.mock(MockServiceBServiceImpl.class);

        /**
         * 数据打桩
         */

        /**
         * 参数的入参
         * any()
         * Mockito.matches(".*User$"))、开头结尾验证endsWith(String suffix) startsWith(String prefix)、判空验证isNotNull() isNull()
         * ArgumentMatcher只有一个方法boolean matches(T argument);
         */

        /**
         * 表示行为的方法
         * thenAnswer(Answer<?> answer);、thenCallRealMethod();
         * when().thenThrow()
         */
//        Mockito.when(userDao.updateUser(Mockito.argThat(argument -> argument.getName().equals("admin")))).thenThrow(IllegalArgumentException.class);
        /*对void的方法设置模拟*/

//        Mockito.doAnswer(invocationOnMock -> {
//            System.out.println("进入了Mock");
//            return null;
//        }).when(fileRecordDao).insert(Mockito.any());


        /**
         * 与SpingTest结合
         * ReflectionTestUtils:反射处理测试类的工具，通过这个，我们可以替换某一个被spring所管理的bean的成员变量。把他换成我们Mock出来的模拟对象
         *
         */
        // ReflectionTestUtils.setField(AopTargetUtils.getTarget(injectMockService), ”mockServiceBService“, mockServiceBService);
    }

    /**
     * 测试 外调 service方法
     */
    @Test
    public void test1() {
        injectMockService.test();
    }

    /**
     * Mock 私有方法
     */
    @Test
    public void test2() {

    }

    /**
     * Mock 静态方法
     */
    @Test
    public void test3() {

    }

    /**
     * Mock 被测类本身的公共方法
     */
    @Test
    public void test4() {

    }

    /**
     * Mock方法多次调用返回不同的值
     */
    @Test
    public void test5() {

    }

    /**
     * Mock返回为空的方法
     */
    @Test
    public void test6() {

    }

    /**
     * Mock模拟抛出异常
     */
    @Test
    public void test7() {

    }

    /**
     * Mock被测类中new出来的对象
     */
    @Test
    public void test8() {

    }

    /**
     * 测试私有方法
     */
    @Test
    public void test9() {

    }

    /**
     * MOCK 静态变量
     */
    @Test
    public void test10() {

    }

    /**
     * 测试抽象类中被子类重写的方法
     */
    @Test
    public void test11() {

    }

    /**
     * MOCK 静态方法
     */
    @Test
    public void test12() {

    }

    /**
     * 防止模拟类初始化静态代码块
     */
    @Test
    public void test13() {

    }

    /**
     * 调用私有方法、获取私有成员变量方法
     */
    @Test
    public void test14() {

    }

    /**
     * 调用静态类，并且父类也是静态类，Mock父类的静态方法
     */
    @Test
    public void test15() {

    }

    /**
     * Mock替换掉父类方法
     */
    @Test
    public void test16() {

    }

    /**
     * Mock Connection等方法
     */
    @Test
    public void test17() {

    }

    /**
     * Mock whennew MOCK的变量
     */
    @Test
    public void test18() throws Exception {
        MockWhenNewUtil beforeMock = new MockWhenNewUtil();
        System.out.println("-------beforeMock---------");
        System.out.println(beforeMock.getMockWhenNew());
        MockWhenNewUtil mockWhenNewUtil = PowerMockito.mock(MockWhenNewUtil.class);
        PowerMockito.whenNew(MockWhenNewUtil.class).withAnyArguments().
                thenReturn(mockWhenNewUtil);
        PowerMockito.when(mockWhenNewUtil.getMockWhenNew()).thenReturn("mockWhenNew");

        System.out.println("-------afterMock---------");
        System.out.println(mockWhenNewUtil.getMockWhenNew());
        System.out.println("-------afterMock injectMockService---------");
        System.out.println(injectMockService.testWhennew());
        Assert.isTrue("mockWhenNew".equals(injectMockService.testWhennew()));
    }
}
