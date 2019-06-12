package xin.mengzuo.user.car.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Userorder;

public interface OrderDao extends JpaRepository<Userorder, Integer>{
 List<Userorder> findByUserId(Integer userId);
 UsedCarResult findByStatus(Integer status);
 
}
