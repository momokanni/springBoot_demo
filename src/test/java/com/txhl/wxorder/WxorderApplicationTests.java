package com.txhl.wxorder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxorderApplicationTests {

	private final Logger logger = LoggerFactory.getLogger(WxorderApplicationTests.class);
	@Test
	public void contextLoads() {
		logger.warn("warning...");
	}

}
