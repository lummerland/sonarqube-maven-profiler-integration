package org.lummerland.sonarqube.integration.mavenprofiler.report;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ReportFile {

	private static final Logger LOGGER = Loggers.get(ReportFile.class);
	private File report;

	private ReportFile(final File report) {
		this.report = report;
	}

	static ReportFile of(final String path) {
		return new ReportFile(new File(path));
	}

	public static ReportFile in(final File directory) {
		LOGGER.info(">>>>>> Try to find report file in {}", directory.getPath());
		final File[] files = directory.listFiles((dir,  name) -> name.startsWith("profiler-report"));
		final Optional<File> file = (files != null) ? Arrays.stream(files).findFirst() : Optional.empty();
		return new ReportFile(file.get());
	}

	public Optional<Integer> buildTime() {
		if (report == null) {
			return Optional.empty();
		}

		try {
			final String content;
			content = FileUtils.readFileToString(report, "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
			String time = jsonObject.get("time").getAsString().replaceAll("[^0-9]", "");
			int timenumber = Integer.parseInt(time);
			return Optional.of(timenumber);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public String toString() {
		return (report == null) ? "[ no report file found ]" : report.getAbsolutePath();
	}
}
