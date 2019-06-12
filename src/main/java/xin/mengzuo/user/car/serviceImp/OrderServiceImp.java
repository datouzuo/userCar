package xin.mengzuo.user.car.serviceImp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import xin.mengzuo.user.car.config.ApplStuts;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.dao.OrderDao;
import xin.mengzuo.user.car.pojo.CollectionToEsCar;
import xin.mengzuo.user.car.pojo.EsCar;
import xin.mengzuo.user.car.pojo.UserOrderTOEsCar;
import xin.mengzuo.user.car.pojo.Userorder;
import xin.mengzuo.user.car.service.OrderService;
@Service
@Transactional
public class OrderServiceImp implements OrderService{
    @Autowired
	private OrderDao oDao;
    @Autowired
   	private JestClient jest;
	@Override
	public UsedCarResult addOrder(Userorder order) {
	oDao.save(order);
		return UsedCarResult.ok();
	}

	@Override
	public UsedCarResult CancelOrder(Integer id) {
	
		Optional<Userorder> findById = oDao.findById(id);
		Userorder order = findById.get();
		if(order.getStatus()==ApplStuts.APPIY) {
			return UsedCarResult.build(400, "预约已处理");
		}else {	
	     order.setStatus(ApplStuts.CANNEL);
	     
		return UsedCarResult.ok();
		}
	}

	@Override
	public UsedCarResult findByUserId(Integer userId) throws IOException {
		List<Userorder> list = oDao.findByUserId(userId);
		List<UserOrderTOEsCar> li = new LinkedList<>();

		for(Userorder uo : list) {
			
			SearchSourceBuilder search = new SearchSourceBuilder();
			 BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			 queryBuilder.must(QueryBuilders.matchQuery("carId",uo.getCarId()));
			System.out.println(uo.getCarId());
			 search.query(queryBuilder);
			 Search se = new Search.Builder(search.toString()).addIndex("escar").addType("escar").build();
			 SearchResult execute = jest.execute(se);
			 Hit<EsCar,Void> firstHit = execute.getFirstHit(EsCar.class);
			 UserOrderTOEsCar ct= new UserOrderTOEsCar();
				 
				 EsCar source = firstHit.source;
				 ct.setEsCar(source);
				 ct.setUserOrder(uo);;
				 
				 li.add(ct);
		}
		
		
		return UsedCarResult.ok(li);
	}

}
