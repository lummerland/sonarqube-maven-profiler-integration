package org.lummerland.sonarqube.integration.mavenprofiler;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.utils.log.Profiler;

/**
 * A Sensor is executed once per analysis.
 */
public class ProfilerSensor implements Sensor {

	private static final Logger LOGGER = Loggers.get(ProfilerSensor.class);

	private final FileSystem fileSystem;

	// default directory the profiler puts the report to
	private static final String REPORTDIRECTORY = "/.profiler";

	public ProfilerSensor(final FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	@Override
	public void describe(final SensorDescriptor sensorDescriptor) {
	}

	@Override
	public void execute(final SensorContext sensorContext) {
		final Profiler profiler = Profiler.create(LOGGER);
		profiler.startInfo("Process buildtime report");

		// find the profiler file
		// parse the file and extract the buildtime
		// add the time as measure metric
		sensorContext.newMeasure().forMetric(ProfilerMetrics.BUILDTIME).on(sensorContext.module()).withValue(10000).save();
		profiler.stopInfo();
	}
}
