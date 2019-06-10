package xin.mengzuo.user.car.serviceImp;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xin.mengzuo.user.car.config.ApplStuts;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.dao.OrderDao;
import xin.mengzuo.user.car.pojo.Order;
import xin.mengzuo.user.car.service.OrderService;
@Service
@Transactional
public class OrderServiceImp implements OrderService{
    @Autowired
	private OrderDao oDao;
	@Override
	public UsedCarResult addOrder(Order order) {
	oDao.save(order);
		return UsedCarResult.ok();
	}

	@Override
	public UsedCarResult CancelOrder(Integer id) {
	
		Optional<Order> findById = oDao.findById(id);
		Order order = findById.get();
		if(order.getStatus()==ApplStuts.APPIY) {
			return UsedCarResult.build(400, "预约已处理");
		}else {	
	     order.setStatus(ApplStuts.CANNEL);
	     
		return UsedCarResult.ok();
		}
	}

	@Override
	public UsedCarResult findByUserId(Integer userId) {
		List<Order> list = oDao.findByUserId(userId);
		return UsedCarResult.ok(list);
	}

}
