package com.txhl.wxorder;

import com.txhl.wxorder.service.impl.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 邮件测试
 *
 * @author Administrator
 * @create 2018-03-28 17:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailService mailService;

    private String to = "740949744@qq.com";
    private String subject = "HelloWorld";
    private String content = "...........";

    @Test
    public void sendHtmlMail() {
        mailService.sendSimpleMail(to,subject,content);
    }
}
