package xin.mengzuo.user.car.serviceImp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.EsCar;
import xin.mengzuo.user.car.pojo.EsCarToEsMoreCar;
import xin.mengzuo.user.car.pojo.EsMoreCar;
import xin.mengzuo.user.car.pojo.EsUser;
import xin.mengzuo.user.car.pojo.EsUserToEsCar;
import xin.mengzuo.user.car.pojo.UserOrderTOEsCar;
import xin.mengzuo.user.car.service.findCarService;
@Service
public class findServiceImp implements findCarService {
    @Autowired
	private JestClient jest;

	@Override
	public UsedCarResult filtrCar(EsCar esCar,Integer page) throws IOException {
		
		SearchSourceBuilder search = new SearchSourceBuilder();
		 BoolQueryBuilder query = QueryBuilders.boolQuery();
		 query.must(QueryBuilders.matchQuery("status", 0));
		 if(esCar.getBrand()!=null) {
			 query.must(QueryBuilders.matchQuery("brand", esCar.getBrand()));
		 }
		 if(esCar.getModel()!=null) {
			 query.must(QueryBuilders.matchQuery("model", esCar.getModel()));
		 }
		 if(esCar.getRang_price()!=null) {
			 String[] split = esCar.getRang_price().split(",");
			 query.must(QueryBuilders.rangeQuery("old_price").gte(split[0]));
			 query.must(QueryBuilders.rangeQuery("old_price").gte(split[1]));
		 }
		 if(esCar.getCarModel()!=null) {
			 query.must(QueryBuilders.matchQuery("carModel", esCar.getCarModel()));

		 }
		 if(esCar.getRangeDisplacment()!=null) {
			 String[] split = esCar.getRangeDisplacment().split(",");
			 query.must(QueryBuilders.rangeQuery("displacment").gte(split[0]));
			 query.must(QueryBuilders.rangeQuery("displacment").gte(split[1]));
		 }
		 if(esCar.getLetOut()!=null) {
			 query.must(QueryBuilders.matchQuery("letOut",esCar.getLetOut()));
 
		 }
		 if(esCar.getColor()!=null) {
			 query.must(QueryBuilders.matchQuery("color",esCar.getColor()));
		 }
		 if(esCar.getDrivingModel()!=null) {
			 query.must(QueryBuilders.matchQuery("drivingModel",esCar.getDrivingModel()));
		 }
		 if(esCar.getLightspot()!=null) {
			 query.must(QueryBuilders.matchQuery("lightspot",esCar.getLightspot()));

		 }
		 if(esCar.getArea()!=null) {
			 query.must(QueryBuilders.matchQuery("area",esCar.getArea()));

		 }
		 if(esCar.getGearbox()!=null) {
			 query.must(QueryBuilders.matchQuery("gearbox",esCar.getGearbox()));
		 }
		search.from((page-1)*12);
		search.size(12);
		search.query(query);
		Search se = new Search.Builder(search.toString()).addIndex("escar").addType("escar").build();
		SearchResult execute = jest.execute(se);
		List<Hit<EsCar,Void>> hits = execute.getHits(EsCar.class);
		List<EsCar> list = new LinkedList<EsCar>();
		for(Hit<EsCar,Void> hit : hits) {
			EsCar source = hit.source;
			list.add(source);
		}
		return UsedCarResult.ok(list);
	}

	@Override
	public UsedCarResult findByCarId(String carId) throws IOException {
		
		
		SearchSourceBuilder search = new SearchSourceBuilder();
		 BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		 queryBuilder.must(QueryBuilders.matchQuery("carId",carId));
		 search.query(queryBuilder);
		 Search se = new Search.Builder(search.toString()).addIndex("escar").addType("escar").build();
		 SearchResult execute = jest.execute(se);
		 Hit<EsCar,Void> firstHit = execute.getFirstHit(EsCar.class); 
		 EsCar source = firstHit.source;
		 
		 Search se1 = new Search.Builder(search.toString()).addIndex("esmorecar").addType("esmorecar").build();
		 SearchResult execute1 = jest.execute(se1);
		 
		 Hit<EsMoreCar, Void> firstHit1 = execute1.getFirstHit(EsMoreCar.class); 
		 EsMoreCar source1 = firstHit1.source;
		 
		 EsCarToEsMoreCar es = new EsCarToEsMoreCar();
		 es.setEsCar(source);
		 es.setEsMoreCar(source1);		 
        	return UsedCarResult.ok(es);
		


	}

}
