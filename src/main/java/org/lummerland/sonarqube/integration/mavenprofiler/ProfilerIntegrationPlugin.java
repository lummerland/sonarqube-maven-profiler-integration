package org.lummerland.sonarqube.integration.mavenprofiler;

import org.sonar.api.Plugin;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class ProfilerIntegrationPlugin implements Plugin {

  @Override
  public void define(final Context context) {
    context.addExtensions(
        ProfilerSensor.class,
        ProfilerMetrics.class
    );
  }
}
