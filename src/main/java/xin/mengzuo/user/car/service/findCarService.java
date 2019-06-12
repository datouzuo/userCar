package xin.mengzuo.user.car.service;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.EsCar;
import xin.mengzuo.user.car.pojo.EsMoreCar;
import xin.mengzuo.user.car.pojo.EsUser;

public interface findCarService {
	
	public UsedCarResult filtrCar(EsCar esCar,Integer page) throws IOException;
	
}
