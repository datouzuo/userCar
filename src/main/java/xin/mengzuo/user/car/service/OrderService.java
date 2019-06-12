package xin.mengzuo.user.car.service;

import java.io.IOException;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Userorder;

public interface OrderService {
  UsedCarResult addOrder(Userorder order);
  UsedCarResult CancelOrder(Integer id);
  UsedCarResult findByUserId(Integer userId) throws IOException ;
}
