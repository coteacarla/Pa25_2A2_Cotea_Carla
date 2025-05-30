package org.example.lab11client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class Lab11ClientApplication implements CommandLineRunner {

	private static final String BASE_URL = "http://localhost:9090/api/cities";

	private final RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(Lab11ClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ResponseEntity<City[]> response = restTemplate.getForEntity(BASE_URL, City[].class);
		City[] cities = response.getBody();
		System.out.println("All cities:");
		if (cities != null) {
			Arrays.stream(cities).forEach(System.out::println);
		}

		City newCity = new City(null, "Sample City", "Sample Country");
		City createdCity = restTemplate.postForObject(BASE_URL, newCity, City.class);
		System.out.println("Created city: " + createdCity);

		if (createdCity != null) {
			createdCity.setName("Updated City Name");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<City> entity = new HttpEntity<>(createdCity, headers);

			ResponseEntity<City> updatedCityResponse = restTemplate.exchange(
					BASE_URL + "/" + createdCity.getId(),
					HttpMethod.PUT,
					entity,
					City.class);

			System.out.println("Updated city: " + updatedCityResponse.getBody());
		}

		// 4. DELETE the city
		if (createdCity != null) {
			restTemplate.delete(BASE_URL + "/" + createdCity.getId());
			System.out.println("Deleted city with id: " + createdCity.getId());
		}
	}
	
	public static class City {
		private Integer id;
		private String name;
		private String country;

		public City() {}

		public City(Integer id, String name, String country) {
			this.id = id;
			this.name = name;
			this.country = country;
		}

		public Integer getId() { return id; }
		public void setId(Integer id) { this.id = id; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getCountry() { return country; }
		public void setCountry(String country) { this.country = country; }

		@Override
		public String toString() {
			return "City{id=" + id + ", name='" + name + "', country='" + country + "'}";
		}
	}
}
