package xin.mengzuo.user.car.serviceImp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.dao.CollectionDao;
import xin.mengzuo.user.car.pojo.Collection;
import xin.mengzuo.user.car.pojo.CollectionToEsCar;
import xin.mengzuo.user.car.pojo.EsCar;
import xin.mengzuo.user.car.pojo.EsUser;
import xin.mengzuo.user.car.pojo.UserOrderTOEsCar;
import xin.mengzuo.user.car.service.CollectionService;
@Service
public class CollectServiceImp implements CollectionService{
    @Autowired
	private CollectionDao collect;
    @Autowired
   	private JestClient jest;
    
	public UsedCarResult addCollection(Collection col) {
	collect.save(col);
	//TODO 获得，col中 carId 对es中收藏加一
		return UsedCarResult.ok();
	}
    @Transactional
	@Override
	public UsedCarResult deleteCollection(Integer id) {
		collect.deleteById(id);
		return UsedCarResult.ok();
	}

	@Override
	public UsedCarResult findByUserId(Integer userId) throws IOException {
		List<CollectionToEsCar> list = new LinkedList<>();
		List<Collection> findByuserId = collect.findByUserId(userId);
		
		for(Collection collection : findByuserId) {
		SearchSourceBuilder search = new SearchSourceBuilder();
		 BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		 queryBuilder.must(QueryBuilders.matchQuery("carId",collection.getCarId()));
		System.out.println(collection.getCarId());
		 search.query(queryBuilder);
		 Search se = new Search.Builder(search.toString()).addIndex("escar").addType("escar").build();
		 SearchResult execute = jest.execute(se);
//		System.out.println(execute);
//		JsonObject jsonObject = execute.getJsonObject();
//		System.out.println(jsonObject);
//	    JsonElement jsonElement = jsonObject.get("hits").getAsJsonObject().get("hits").getAsJsonArray().get(0).getAsJsonObject().get("_source");
//	    System.out.println(jsonElement.toString());           
//	    System.out.println(jsonElement);
//	    list.add(jsonElement.toString());
		 Hit<EsCar,Void> firstHit = execute.getFirstHit(EsCar.class);
		CollectionToEsCar ct= new CollectionToEsCar();
		 
		 EsCar source = firstHit.source;
		 ct.setEsCar(source);
		 ct.setCollection(collection);
		 
		 list.add(ct);
		 

		}
		
		return UsedCarResult.ok(list);
	}

}
