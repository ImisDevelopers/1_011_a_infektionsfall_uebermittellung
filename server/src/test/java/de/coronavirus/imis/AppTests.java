package de.coronavirus.imis;

import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gcp.storage.GoogleStorageProtocolResolver;
import org.springframework.cloud.gcp.storage.GoogleStorageProtocolResolverSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

@SpringBootTest
class AppTests {

	@Test
	void contextLoads() {
	}

	/**
	 * Configuration for the tests.
	 */
	@Configuration
	@Import(GoogleStorageProtocolResolver.class)
	static class StorageApplication {

		@Bean
		public static Storage mockStorage() throws Exception {
			return mock(Storage.class);
		}

		@Bean
		public static GoogleStorageProtocolResolverSettings googleStorageProtocolResolverSettings() {
			return new GoogleStorageProtocolResolverSettings();
		}
	}

}
