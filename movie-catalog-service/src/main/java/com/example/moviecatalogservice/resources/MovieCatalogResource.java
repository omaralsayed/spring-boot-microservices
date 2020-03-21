package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem; 
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		RestTemplate restTemplate = new RestTemplate();
	
		List<Rating> ratings = Arrays.asList(
				new Rating("Name_1", 5),
				new Rating("Name_2", 4)
		);
		
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Desc",  rating.getRating());
		})
		.collect(Collectors.toList());
		
	}
	
}
