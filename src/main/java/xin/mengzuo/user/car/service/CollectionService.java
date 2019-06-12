package xin.mengzuo.user.car.service;

import java.io.IOException;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Collection;
 
public interface CollectionService {

	UsedCarResult addCollection(Collection col);
	UsedCarResult deleteCollection(Integer id);
	UsedCarResult findByUserId(Integer userId)throws IOException;
}
