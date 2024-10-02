package com.app.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.karate.RuntimeHook;
import com.intuit.karate.Suite;
import com.intuit.karate.core.FeatureRuntime;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.core.Step;
import com.intuit.karate.core.StepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KarateHook implements RuntimeHook {

    private static final Logger logger = LoggerFactory.getLogger(KarateHook.class);

    // Global list to collect test results
    private static final List<Map<String, Object>> testResults = new ArrayList<>();

    public KarateHook() {
        // Initialize the hook
    }

    @Override
    public boolean beforeScenario(ScenarioRuntime sr) {
        System.err.println("Started Test: " + sr.scenario.getName());
        return true;
    }

    @Override
    public void afterScenario(ScenarioRuntime sr) {
        System.err.println("Finished Test: " + sr.scenario.getName() + " | Is Failed: " + sr.result.isFailed());

        // Collect test result data
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("name", sr.scenario.getName());
        resultData.put("status", sr.result.isFailed() ? "Failed" : "Passed");
        resultData.put("timestamp", LocalDateTime.now().toString());

        // Add result to the global list
        testResults.add(resultData);
    }

    @Override
    public boolean beforeFeature(FeatureRuntime fr) {
        try {
            // Code to handle actions before feature starts
        } catch (Exception e) {
            logger.error("beforeFeature exception: {}", e.getMessage(), e);
        }
        return true;
    }

    @Override
    public void afterFeature(FeatureRuntime fr) {
        try {
            // Code to handle actions after feature ends
        } catch (Exception e) {
            logger.error("afterFeature exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public void beforeSuite(Suite suite) {
        try {
            // Code to handle actions before suite starts
        } catch (Exception e) {
            logger.error("beforeSuite exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public void afterSuite(Suite suite) {
        try {
            System.err.println("Suite finished. Total Features: " + suite.futures.size());

            // Write the test results to a JSON file after the suite is done
            writeResultsToJson();
        } catch (Exception e) {
            logger.error("afterSuite exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public boolean beforeStep(Step step, ScenarioRuntime sr) {
        return true;
    }

    @Override
    public void afterStep(StepResult result, ScenarioRuntime sr) {
        // No-op for afterStep
    }

    // Method to write results to JSON
    private void writeResultsToJson() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("testResults.json");

        try {
            mapper.writeValue(file, testResults);
            //TODO : user can choose to generate an html file, or write to a db for result persistance, or post to a reporting system
            System.err.println("Test results written to: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error writing test results to JSON: {}", e.getMessage(), e);
        }
    }
}
