package org.frappa.leanquery.config;

import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.conf.RenderQuotedNames;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	/**
	 * Add custom jOOQ configuration.
	 * <p>
	 * The {@link DefaultConfigurationCustomizer} type has been added in Spring Boot
	 * 2.5 to facilitate customising the out of the box provided jOOQ
	 */
	@Bean
	public DefaultConfigurationCustomizer configurationCustomiser() {
		return configuration -> {
			 configuration.settings().withRenderQuotedNames(RenderQuotedNames.NEVER);
			 configuration.setExecuteListener(new StatisticsListener());
		};
	}
}