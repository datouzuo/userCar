package xin.mengzuo.user.car.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import xin.mengzuo.user.car.pojo.Userorder;
import xin.mengzuo.user.car.pojo.User;
import xin.mengzuo.user.car.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService oService;
	@Autowired
	private JedisCluster cluster;
	//json操作
	@Autowired
	private ObjectMapper obJeson;
	
	/**
	 * 买家预约添加
	 * @param order
	 * @param request
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping("/addOrder")
	public UsedCarResult addOrder(Userorder order, String tokenId, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {

		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar cal = Calendar.getInstance(zone);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    order.setCreatedAt(sim.format(cal.getTime()));// 获得东八区时间
		String us = cluster.get("tokenId:"+tokenId);
		User usr = obJeson.readValue(us, User.class);
		order.setUserId(usr.getUserid()); 
		
		order.setStatus(ApplStuts.NO_APPIY);
		
		return oService.addOrder(order);
	}
/**
 * 删除预约
 * @param id
 * @return
 */
	@RequestMapping("/cancelOrder")
	public UsedCarResult CancelOrder(Integer id) {
		
		return oService.CancelOrder(id);
	}
/**
 * 查看自己的预约
 * @param request
 * @return
 * @throws JsonParseException
 * @throws JsonMappingException
 * @throws IOException
 */
	@RequestMapping("/findByUserId")
	public UsedCarResult findByUserId(String tokenId) throws JsonParseException, JsonMappingException, IOException {
		String us = cluster.get("tokenId:"+tokenId);
			User usr = obJeson.readValue(us, User.class);
		return oService.findByUserId(1);
	}
}
