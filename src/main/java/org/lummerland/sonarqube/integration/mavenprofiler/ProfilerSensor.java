package org.lummerland.sonarqube.integration.mavenprofiler;

import java.io.File;
import java.util.Optional;

import org.lummerland.sonarqube.integration.mavenprofiler.report.ReportFile;
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

	@Override
	public void describe(final SensorDescriptor sensorDescriptor) {
	}

	@Override
	public void execute(final SensorContext sensorContext) {
		final FileSystem fileSystem = sensorContext.fileSystem();

		final Profiler profiler = Profiler.create(LOGGER);
		final ReportFile reportFile = ReportFile.in(new File(fileSystem.baseDir().getPath() + "/target"));

		profiler.startInfo("Process buildtime report, try to find report in " + reportFile);

		final Optional<Integer> buildTime = reportFile.buildTime();
		if (!buildTime.isPresent()) {
			LOGGER.error(">>>>>> Built time not found, nothing to do.");
		} else {
			// add the time as measure metric
			sensorContext.newMeasure().forMetric(ProfilerMetrics.BUILDTIME).on(sensorContext.module()).withValue(buildTime.get()).save();
		}
		profiler.stopInfo();
	}
}
