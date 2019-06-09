package xin.mengzuo.user.car.service;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Car;

public interface SellCarService {
	UsedCarResult applySellCar(Car car);
	UsedCarResult CancelApply(Integer id);
	UsedCarResult findAllApply(Integer userId);

}
