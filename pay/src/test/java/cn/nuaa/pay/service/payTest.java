package cn.nuaa.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class payTest {

    @Autowired
    IPayService payService;
    //创建索引库
    @Test
    public void testCreate() throws IOException {

        payService.create("123456", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }
}
