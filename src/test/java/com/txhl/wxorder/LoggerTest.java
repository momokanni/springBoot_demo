package com.txhl.wxorder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 日志测试
 *
 * @author Administrator
 * @create 2018-03-27 18:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    //private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        String name = "abc";
        String pwd = "123";
        try {
            int[] i = new int[]{0,2,3};
            int temp = i[5];
        }catch (Exception e){
            log.error(e.toString());
        }
        log.debug("debug...");
        log.info("name: {}, pwd:{} ", name,pwd);
        log.error("erorr...");
        log.warn("warn...");
    }
}
