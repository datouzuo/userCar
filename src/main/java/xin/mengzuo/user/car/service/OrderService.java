package xin.mengzuo.user.car.service;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Order;

public interface OrderService {
  UsedCarResult addOrder(Order order);
  UsedCarResult CancelOrder(Integer id);
  UsedCarResult findByUserId(Integer userId);
}
