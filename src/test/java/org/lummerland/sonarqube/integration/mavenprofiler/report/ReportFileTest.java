package org.lummerland.sonarqube.integration.mavenprofiler.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class ReportFileTest {

	@Test
	void builtTime() {
		final String existingFile = getClass().getResource("/profiler-report.json").getPath();

		final ReportFile reportFile = ReportFile.of(existingFile);

		assertEquals(Optional.of(1896), reportFile.buildTime());

	}
}
