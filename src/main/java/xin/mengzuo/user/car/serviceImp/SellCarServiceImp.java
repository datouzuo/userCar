package xin.mengzuo.user.car.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xin.mengzuo.user.car.config.ApplStuts;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.dao.SellCarDao;
import xin.mengzuo.user.car.pojo.Car;
import xin.mengzuo.user.car.service.SellCarService;
/**
 *	卖家业务操作
 * @author 左利伟
 *
 */
@Service
@Transactional
public class SellCarServiceImp implements SellCarService {
	@Autowired
	private SellCarDao sellCarDao;
	/**
	 * 插入申请表
	 */
	@Override
	public UsedCarResult applySellCar(Car car) {
		sellCarDao.save(car);
		return UsedCarResult.ok();
	}
	/**
	 *取消申请
	 */
	@Override
	public UsedCarResult CancelApply(Integer id) {
	Optional<Car> optional = sellCarDao.findById(id);
	Car car = optional.get();
	if(car.getStatus()==1)
		return UsedCarResult.build(400, "申请已经受理不能取消");
	
	car.setStatus(ApplStuts.CANNEL);
		return UsedCarResult.ok();
	}
	/**
	 * 查看自己得所有得申请
	 */
	@Override
	public UsedCarResult findAllApply(Integer userId) {
		List<Car> list =  sellCarDao.findByUserId(userId);
		return UsedCarResult.ok(list);
	}


}
