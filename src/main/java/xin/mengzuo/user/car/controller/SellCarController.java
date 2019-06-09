package xin.mengzuo.user.car.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisCluster;
import xin.mengzuo.user.car.config.ApplStuts;
import xin.mengzuo.user.car.config.CookieUtils;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Car;
import xin.mengzuo.user.car.pojo.User;
import xin.mengzuo.user.car.service.SellCarService;

@RestController
@RequestMapping("/car")
public class SellCarController {
	@Autowired
	private SellCarService scs;
	@Autowired
	private JedisCluster cluster;
	//json操作
	@Autowired
	private ObjectMapper obJeson;
	
	@RequestMapping("/applySellCar")
	public UsedCarResult applySellCar(Car car, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar cal = Calendar.getInstance(zone);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		car.setCreatedAt(sim.format(cal.getTime()));// 获得东八区时间
	String value = CookieUtils.getCookieValue(request, "tokenId");
		String us = cluster.get("tokenId:"+value);
		User usr = obJeson.readValue(us, User.class);
	
	 car.setUserId(usr.getUserid());// 设置用户Id
	
		 car.setStatus(ApplStuts.NO_APPIY); //设置订单状态未处理
		
		return scs.applySellCar(car);
	}

	/**
	 *取消申请
	 */
	@RequestMapping("/calcelApply")
	public UsedCarResult CancelApply(Integer id) {
	
		return scs.CancelApply(id);
	}
	/**
	 * 查看自己得所有得申请
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/findApplyBYuserId")
	public UsedCarResult findAllApply(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		String value = CookieUtils.getCookieValue(request, "tokenId");
		String us = cluster.get("tokenId:"+value);
		User usr = obJeson.readValue(us, User.class);

	
		return scs.findAllApply(usr.getUserid());
	}
}
