package org.example.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // Abilita la gestione delle transazioni e ricerca le annotazioni @Transacional ecc..
@EnableCaching // Abilita il caching e cerca le annotazioni correlate
public class SkeletonWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkeletonWebServicesApplication.class, args);
	}
	
	/*
	 * Importando le dipendenze di spring cache e caffeine viene già inserito un cacheManager Bean e configurato con il nome 
	 * della proprietà spring.cache.cache-names="greetings"
	 * 
	@Bean
	public CacheManager cacheManager() {
		// Non adatto per la producione, in produzione meglio usare Guava o Caffeine
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");
	}
	*/
}
