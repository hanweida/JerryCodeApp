package powermock;

import com.powermock.InjectMockServiceImpl;
import com.powermock.MockServiceBServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    @InjectMocks
    InjectMockServiceImpl injectMockService;

    @Mock
    MockServiceBServiceImpl mockServiceBService;

    //@Test
    public void test(){
//        injectMockService.test();
            //tEST
    }
}
