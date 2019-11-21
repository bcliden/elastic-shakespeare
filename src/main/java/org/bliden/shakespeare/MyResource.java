
package org.bliden.shakespeare;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/speakers")
public class MyResource {
	
	@Context ServletContext sc;
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() throws UnknownHostException {
		
		esService es = (esService) sc.getAttribute("elasticsearch");
		TransportClient client = es.getClient();
		
        if (client != null) {  
            System.out.println("Database is connected");  
        } else {  
            System.out.println("Database is not connected");  
        }  
		
		SearchResponse res = client.prepareSearch("shakespeare").setSize(0).setQuery(QueryBuilders.matchAllQuery())
				.addAggregation(AggregationBuilders.terms("speakers").field("speaker").size(10)).get();

		Terms speakers = res.getAggregations().get("speakers");

		List<? extends Bucket> buckets = speakers.getBuckets();

		Map<String, Long> mapBucket = new HashMap<>();
		buckets.stream().forEach(b -> {
			mapBucket.put(b.getKeyAsString(), b.getDocCount());
		});
		
		return Response.ok().entity(mapBucket).build();

	}
}
