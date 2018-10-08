/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.autoconfigure.metrics.web.jetty;

import java.util.Collections;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jetty.InstrumentedQueuedThreadPool;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.WebAppContext;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Jetty.
 *
 * @author Manabu Matsuzaki
 * @since 2.1.0
 */
@Configuration
@ConditionalOnClass({ Server.class, Loader.class, WebAppContext.class })
public class JettyMetricsAutoConfiguration {

//	public JettyMetricsAutoConfiguration(JettyServletWebServerFactory factory,
//			MeterRegistry registry, @Nullable JettyServerCustomizer jettyServerCustomizer) {
//
//		// Enable threadpool metrics
//		factory.setThreadPool(new InstrumentedQueuedThreadPool(registry, Collections.emptyList()));
//
//		// Enable statistics metrics
//		factory.addServerCustomizers((server) -> {
//			StatisticsHandler statisticsHandler = new StatisticsHandler();
//			statisticsHandler.setHandler(server.getHandler());
//			server.setHandler(statisticsHandler);
//		});
//
//		// Add a customizer created by user
//		if (jettyServerCustomizer != null) {
//			factory.addServerCustomizers(jettyServerCustomizer);
//		}
//	}

//	@Bean
//	@ConditionalOnMissingBean
//	public JettySestatistiregistrycsHandlerrvletWebServerFactory webServerFactory(MeterRegistry registry,
//			@Nullable JettyServerCustomizer jettyServerCustomizer) {
//		final JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
//
//		// Enable threadpool metrics
//		factory.setThreadPool(new InstrumentedQueuedThreadPool(registry, Collections.emptyList()));
//
//		// Enable statistics metrics
//		factory.addServerCustomizers((server) -> {
//			StatisticsHandler statisticsHandler = new StatisticsHandler();
//			statisticsHandler.setHandler(server.getHandler());
//			server.setHandler(statistiregistrycsHandler);
//		});
//
//		// Add a customizer created by user
//		if (jettyServerCustomizer != null) {
//			factory.addServerCustomizers(jettyServerCustomizer);
//		}
//
//		return factory;
//	}

	@Bean
	public StatisticsHandler statisticsHandler() {
		return new StatisticsHandler();
	}

	@Bean
	public WebServerFactoryCustomizer<JettyServletWebServerFactory> jettyServletWebServerFactory(
			MeterRegistry registry, StatisticsHandler statisticsHandler,
			@Nullable JettyServerCustomizer jettyServerCustomizer) {
//		return (factory) -> factory.setThreadPool(
//				new InstrumentedQueuedThreadPool(registry, Collections.emptyList()));
		return (factory) -> {
			factory.setThreadPool(new InstrumentedQueuedThreadPool(registry, Collections.emptyList()));

			factory.addServerCustomizers((server) -> {
//				StatisticsHandler statisticsHandler = new StatisticsHandler();
				statisticsHandler.setHandler(server.getHandler());
				server.setHandler(statisticsHandler);
			});

			if (jettyServerCustomizer != null) {
				factory.addServerCustomizers(jettyServerCustomizer);
			}
		};
	}
}
