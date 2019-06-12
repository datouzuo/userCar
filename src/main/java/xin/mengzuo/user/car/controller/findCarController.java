package xin.mengzuo.user.car.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.EsCar;
import xin.mengzuo.user.car.pojo.EsMoreCar;
import xin.mengzuo.user.car.pojo.EsUser;
import xin.mengzuo.user.car.service.findCarService;

@RestController
@RequestMapping("/es")
public class findCarController {
	@Autowired
	private findCarService fincar;
	@RequestMapping("/filtrCar")
	public UsedCarResult filtrCar(EsCar esCar,Integer page) throws IOException{
		return fincar.filtrCar(esCar, page);
	}
	@RequestMapping("/findByCarId")
	public UsedCarResult findByCarId(String carId)  throws IOException{
  
      return fincar.findByCarId(carId);
	}
}
