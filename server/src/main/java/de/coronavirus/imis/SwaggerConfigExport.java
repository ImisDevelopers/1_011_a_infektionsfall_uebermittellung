package de.coronavirus.imis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Responsible for generating a swagger configuration file in JSON format. This component is
 * triggered by an application argument of `--swagger-export outfile`
 */
@Component
@Order(-1)
@Slf4j
@RequiredArgsConstructor
public class SwaggerConfigExport implements ApplicationRunner {
	private final ConfigurableApplicationContext appContext;

	private final DocumentationCache swaggerDocCache;
	private final ServiceModelToSwagger2Mapper swaggerDocMapper;
	private final ObjectMapper jsonMapper;

	public void run(ApplicationArguments args) {
		if (!args.containsOption("swagger-export")) return;

		String swaggerBasePath = System.getProperty(
				"springfox.documentation.swagger.v2.path",
				Swagger2Controller.DEFAULT_URL);

		for (String exportOutputPath : args.getOptionValues("swagger-export")) {
			// Find out which swagger group is to be exported; may be specified with a
			// path prefix, separated from the output path by '='
			String[] groupWithPath = exportOutputPath.split("\\s+=\\s+");

			String group = null;
			String path = null;
			int pathIdx = 0;
			switch (groupWithPath.length) {
				case 1:
					break;
				case 2:
					pathIdx = 1;
					break;
				default:
					System.err.println("'" + exportOutputPath + "' is no valid export path " +
							"specification: Contains too many '='");
			}
			group = pathIdx > 0 ? groupWithPath[pathIdx - 1] : Docket.DEFAULT_GROUP_NAME;
			path = groupWithPath[pathIdx];

			// Retrieve the config file content
			Documentation doc = this.swaggerDocCache.documentationByGroup(group);
			Swagger swaggerDoc = this.swaggerDocMapper.mapDocumentation(doc);
			swaggerDoc.basePath(swaggerBasePath);

			// Serialize as UTF-8 JSON to file
			try (BufferedWriter writer =
						 new BufferedWriter(
						 new OutputStreamWriter(
						 new FileOutputStream(path),
						 "UTF-8"))) {
				// Write to File
				this.jsonMapper.writeValue(writer, swaggerDoc);
				log.info("Wrote Swagger documentation to '" + path + "'");

			} catch (IOException ioe) {
				log.error("Writing Swagger documentation to '" +
						path + "' failed: " + ioe.getMessage());
			}
		}

		if (args.containsOption("swagger-only")) {
			SpringApplication.exit(this.appContext);
		}
	}
}
