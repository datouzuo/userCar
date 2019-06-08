package xin.mengzuo.user.car.config;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisCluster;
import xin.mengzuo.user.car.pojo.User;


/**
 * 拦截器只有管理员有权限
 * @author 左利伟
 *
 */
public class SessionInterceptor implements HandlerInterceptor {

	@Autowired
	private JedisCluster cluster;
	//json操作
	@Autowired
	private ObjectMapper obJeson;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String value = CookieUtils.getCookieValue(request, "tokenId");
		if(value!=null&&value.equals("")) {
			cluster.expire("tokenId:"+value, 1800);
			return true;
			
		}
		return false;
	}
}
