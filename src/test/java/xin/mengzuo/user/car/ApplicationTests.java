package xin.mengzuo.user.car;

import java.io.IOException;
import java.util.List;
import java.util.UUID;import org.apache.lucene.search.Query;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.ToXContent.Params;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.UpdateByQuery;
import xin.mengzuo.user.car.config.UsedCarResult;
import xin.mengzuo.user.car.pojo.EsUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Autowired
	private JestClient jedis;
	@Test
	public void contextLoads() throws IOException {
		
//		EsUser es = new EsUser();
//		es.setAge("23");
//		es.setName("zdasd");
//		es.setCarId("xxfd");
//		
//		Index in = new Index.Builder(es).index("car").type("esuser").build();
//		jedis.execute(in);
//		
		SearchSourceBuilder search = new SearchSourceBuilder();
		 BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		 queryBuilder.must(QueryBuilders.matchQuery("age", "23"));
		 Search se = new Search.Builder(search.toString()).addIndex("car").addType("esuser").build();
		
	
		try {
			SearchResult execute = jedis.execute(se);
			List<Hit<EsUser,Void>> hits = execute.getHits(EsUser.class);
			for(Hit<EsUser,Void> hit : hits) {
				EsUser source = hit.source;
				System.out.println(source.getName());
			}
			
		System.out.println(execute.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
@Test	
 public void updatetest() {
	SearchSourceBuilder search = new SearchSourceBuilder();
	 BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
	 queryBuilder.must(QueryBuilders.matchQuery("carId", "f2959f4f-5076-488c-827f-ef9bd557edeb"));
	 search.query(queryBuilder);
	 Search se = new Search.Builder(search.toString()).addIndex("car").addType("esuser").build();
	 try {
		SearchResult execute = jedis.execute(se);
		Hit<EsUser,Void> firstHit = execute.getFirstHit(EsUser.class);
		EsUser score = firstHit.source;
		score.setIdCard("1234567");
		Index in = new Index.Builder(score).index("car").type("esuser").build();
		jedis.execute(in);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
 }
}
