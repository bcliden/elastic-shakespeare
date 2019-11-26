
package org.bliden.shakespeare;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/speakers")
public class SpeakerResource {
	
	@Context ServletContext sc;
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		
		esService es = (esService) sc.getAttribute("elasticsearch");
		TransportClient client = es.getClient();
		
        if (client == null) {  
            return Response.serverError().build();
        }
		
		SearchResponse res = client.prepareSearch("shakespeare").setSize(0).setQuery(QueryBuilders.matchAllQuery())
				.addAggregation(AggregationBuilders.terms("speakers").field("speaker").size(100)).get();

		Terms speakers = res.getAggregations().get("speakers");
		
		List<Map<String, ?>> mapped = speakers.getBuckets().stream().map((Terms.Bucket b) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("speaker", b.getKeyAsString());
			map.put("lines", b.getDocCount());
			return map;
		}).collect(Collectors.toList());
		
		return Response.ok().entity(mapped).build();
	}
	
	@GET
	@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOne(@PathParam("name") String name) {
		
		esService es = (esService) sc.getAttribute("elasticsearch");
		TransportClient client = es.getClient();
		
        if (client == null) {  
            return Response.serverError().build();
        }
        
        SearchResponse res = client.prepareSearch("shakespeare")
        		.setSize(2000)
        		.setQuery(QueryBuilders.matchQuery("speaker", name.toUpperCase())) // REMOVE THIS UPPERCASE ONCE DB IS LOWERCASE
        		.get();
        
        List<SearchHit> hits = Arrays.asList(res.getHits().getHits());
        		
        List<Map<String, Object>> resHits = hits.stream()
        		.map(h -> h.getSourceAsMap())
        		.collect(Collectors.toList());
		
		return Response.ok().entity(resHits).build();
	}
}
