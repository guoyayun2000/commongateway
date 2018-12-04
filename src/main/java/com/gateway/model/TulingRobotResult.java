package com.gateway.model;

import java.util.List;

public class TulingRobotResult {
	private Intent intent;
	private List<Results> results;

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

}
