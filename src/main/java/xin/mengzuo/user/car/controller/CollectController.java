package xin.mengzuo.user.car.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisCluster;
import xin.mengzuo.user.car.config.CookieUtils;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Collection;
import xin.mengzuo.user.car.pojo.User;
import xin.mengzuo.user.car.service.CollectionService;

@RestController
@RequestMapping("/collect")
public class CollectController {
	@Autowired
	private CollectionService cs;
	@Autowired
	private JedisCluster cluster;
	//json操作
	@Autowired
	private ObjectMapper obJeson;
	/**
	 * 添加收藏，传入车得UUID
	 * @param col
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/addCollection")
	public UsedCarResult addCollection(String carId, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		String value = CookieUtils.getCookieValue(request, "tokenId");
			String us = cluster.get("tokenId:"+value);
			User usr = obJeson.readValue(us, User.class); // 从redis获得用户
		Collection col = new Collection();
		col.setCarId(carId);
		col.setUserId(usr.getUserid());
			return cs.addCollection(col);
		}
/**
 *传入收藏id 取消收藏
 * @param id
 * @return
 */
	@RequestMapping("/deleteCollection")
		public UsedCarResult deleteCollection(String id) {
	          
			return cs.deleteCollection(id);
		}
/**
 * 获得该用户所有收藏
 * @param userId
 * @return
 * @throws IOException 
 * @throws JsonMappingException 
 * @throws JsonParseException 
 */
	@RequestMapping("/findCollection")
		public UsedCarResult findByUserId(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		String value = CookieUtils.getCookieValue(request, "tokenId");
		String us = cluster.get("tokenId:"+value);
		User usr = obJeson.readValue(us, User.class); // 从redis获得用户

			return cs.findByUserId(usr.getUserid());
		}
}
