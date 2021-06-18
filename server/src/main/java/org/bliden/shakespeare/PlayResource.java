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
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;

@Path("/plays")
public class PlayResource {
	
	@Context
	private ServletContext sc;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopPlays() {
		esService es = (esService) sc.getAttribute("elasticsearch");
		TransportClient client = es.getClient();
		
        if (client == null) {  
            return Response.serverError().build();
        }
        
		SearchResponse res = client.prepareSearch("shakespeare")
				.setQuery(QueryBuilders.matchAllQuery())
				.setSize(0)
				.addAggregation(
						AggregationBuilders.terms("plays")
							.field("play_name.keyword")
							.size(36)
							.subAggregation(AggregationBuilders.terms("speakers")
									.field("speaker.keyword")
									.size(500)
							)
				)
				.get();

		Terms plays = res.getAggregations().get("plays");

		List<Map<String, Object>> mapped = plays.getBuckets().stream().map((Terms.Bucket b) -> {
			Integer count = ((Terms) b.getAggregations().get("speakers")).getBuckets().size();
			Map<String, Object> map = new HashMap<>();
			map.put("play", b.getKeyAsString());
			map.put("lineCount", b.getDocCount());
			map.put("speakerCount", count);
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
        		.setSize(5000)
        		.setQuery(QueryBuilders.matchQuery("play_name", name))
        		.get();
        
        List<SearchHit> hits = Arrays.asList(res.getHits().getHits());
        		
        List<Map<String, Object>> resHits = hits.stream()
        		.map(h -> h.getSourceAsMap())
        		.collect(Collectors.toList());
		
		return Response.ok().entity(resHits).build();
	}
}
