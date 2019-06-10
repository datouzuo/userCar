package xin.mengzuo.user.car.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{
 List<Order> findByUserId(Integer userId);
 UsedCarResult findByStatus(Integer status);
}
