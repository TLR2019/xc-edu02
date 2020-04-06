package cn.nuaa.pay.api;


import com.lly835.bestpay.enums.BestPayTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Api(value = "支付", description = "支付接口")
public interface PayControllerApi {
    @ApiOperation("支付")
    public ModelAndView create(String orderId,
                               BigDecimal amount,
                               BestPayTypeEnum bestPayTypeEnum
    );
    public String asyncNotify(String notifyData);

}


