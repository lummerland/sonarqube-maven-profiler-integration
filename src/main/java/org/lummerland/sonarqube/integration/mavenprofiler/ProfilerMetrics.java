package org.lummerland.sonarqube.integration.mavenprofiler;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public final class ProfilerMetrics implements Metrics {

	private static final String DOMAIN 	= "Maven-Profiler";
	private static final String KEY 		= "maven_profiler";
	private static final String NAME 		= "Buildtime";
	private static final String DESC 		= "Buildtime measured by the maven profiler extension";

	static final Metric<Serializable> BUILDTIME = new Metric.Builder(KEY, NAME, Metric.ValueType.INT)
			.setDescription(DESC)
			.setDirection(Metric.DIRECTION_WORST) // the more the worse
			.setQualitative(true)
			.setDomain(DOMAIN)
			.create();

	@Override
	public List<Metric> getMetrics() {
		return Arrays.asList(
				ProfilerMetrics.BUILDTIME
		);
	}
}
