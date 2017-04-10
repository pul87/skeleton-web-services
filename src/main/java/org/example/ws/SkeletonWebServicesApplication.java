package org.example.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement // Abilita la gestione delle transazioni e ricerca le annotazioni @Transacional ecc..
@EnableCaching // Abilita il caching e cerca le annotazioni correlate
@EnableScheduling // Abilita la ricerca di annotazioni legate ai processi schedulati
@EnableAsync // Abilita la ricerca di processi che gireranno su un thread separato, non il main thread ( Al contrario degli @Scheduled ).
@SpringBootApplication
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
