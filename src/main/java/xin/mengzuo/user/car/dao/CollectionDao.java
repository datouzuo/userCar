package xin.mengzuo.user.car.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xin.mengzuo.user.car.pojo.Collection;



public interface CollectionDao extends JpaRepository<Collection, String>{

	List<Collection> findByuserId(Integer userId);
}
