package cn.nuaa.pay.service.impl;


import cn.nuaa.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by 廖师兄
 */
@Slf4j
@Service
public class PayService implements IPayService {

	@Autowired
	private BestPayService bestPayService;

	/**
	 * 创建/发起支付
	 *
	 * @param orderId
	 * @param amount
	 */
	@Override
	public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
		if(bestPayTypeEnum != BestPayTypeEnum.WXPAY_NATIVE && bestPayTypeEnum != BestPayTypeEnum.ALIPAY_PC){
			throw new RuntimeException("暂不支持的支付类型");
		}
		//写入数据库
		PayRequest request = new PayRequest();
		request.setOrderName("7841763-最好的支付sdk");
		request.setOrderId(orderId);
		request.setOrderAmount(amount.doubleValue());
		request.setPayTypeEnum(bestPayTypeEnum);
		PayResponse response = bestPayService.pay(request);
		return response;

	}

	/**
	 * 异步通知处理
	 *
	 * @param notifyData
	 */
	@Override
	public String asyncNotify(String notifyData) {
		//1. 签名检验
		PayResponse payResponse = bestPayService.asyncNotify(notifyData);
		//2. 金额校验（从数据库查订单）
		//3. 修改订单支付状态
		if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
			//4. 告诉微信不要再通知了
			return "<xml>\n" +
					"  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
					"  <return_msg><![CDATA[OK]]></return_msg>\n" +
					"</xml>";
		}else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
			return "success";
		}

		throw new RuntimeException("异步通知中错误的支付平台");
	}
}
