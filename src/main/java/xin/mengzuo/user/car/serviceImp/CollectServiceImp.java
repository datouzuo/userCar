package xin.mengzuo.user.car.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.dao.CollectionDao;
import xin.mengzuo.user.car.pojo.Collection;
import xin.mengzuo.user.car.service.CollectionService;
@Service
@Transactional
public class CollectServiceImp implements CollectionService{
    @Autowired
	private CollectionDao collect;
	@Override
	public UsedCarResult addCollection(Collection col) {
	collect.save(col);
	//TODO 获得，col中 carId 对es中收藏加一
		return UsedCarResult.ok();
	}

	@Override
	public UsedCarResult deleteCollection(String id) {
		collect.deleteById(id);
		return UsedCarResult.ok();
	}

	@Override
	public UsedCarResult findByUserId(Integer userId) {
		List<Collection> findByuserId = collect.findByuserId(userId);
		//TODO 根据每个carId由田惠川去查看es 返回展示内容
		return null;
	}

}
