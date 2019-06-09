package xin.mengzuo.user.car.service;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.Collection;

public interface CollectionService {

	UsedCarResult addCollection(Collection col);
	UsedCarResult deleteCollection(String id);
	UsedCarResult findByUserId(Integer userId);
}
