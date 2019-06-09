package xin.mengzuo.user.car.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xin.mengzuo.user.car.pojo.Car;

public interface SellCarDao extends JpaRepository<Car, Integer> {
             List<Car> findByUserId(Integer userId);
}
