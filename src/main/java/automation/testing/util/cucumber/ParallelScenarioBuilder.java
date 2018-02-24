package automation.testing.util.cucumber;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParallelScenarioBuilder implements Formatter, Reporter {

    public static File dir = new File(System.getProperty("java.io.tmpdir") + File.separator + "features");
    private Feature feature;
    private int featureCounter = 0, scenarioCounter = 0;
    private boolean isScenarioStarted = false;
    private List<Step> steps = new ArrayList<>();

    public ParallelScenarioBuilder() {
        if (dir.exists()) {
            dir.delete();
        }
        dir.mkdirs();
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void uri(String uri) {

    }

    @Override
    public void feature(Feature feature) {
        ++featureCounter;
        scenarioCounter = 0;
        this.feature = feature;
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {

    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {
        isScenarioStarted = true;
        ++scenarioCounter;
    }

    @Override
    public void step(Step step) {
        if (isScenarioStarted)
            steps.add(step);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        try {
            File scen = new File(dir + File.separator + "F" + featureCounter + "_S" + scenarioCounter + ".feature");
            if (!scen.exists())
                scen.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(scen));
            writer.write(feature.getKeyword() + ": " + feature.getName());
            writer.newLine();
            writer.write(feature.getDescription());
            writer.newLine();
            writer.newLine();
//            writer.write(scenario.getKeyword() + ": " + scenario.getName());
            writer.write("Scenario: " + scenario.getName());
            writer.newLine();
            writer.write(scenario.getDescription());
            for (Step step :
                    steps) {
                String stepName = step.getName();
                if (step.getRows() != null) {
                    StringBuilder table = new StringBuilder();
                    for (DataTableRow row :
                            step.getRows()) {
                        table.append("|").append(StringUtils.join(row.getCells(), "|")).append("|");
                    }
                    stepName += "<br>" + table;
                } else if (step.getDocString() != null) {
                    stepName += " " + step.getDocString().getValue();
                }
                writer.write(step.getKeyword() + stepName);
                writer.newLine();
            }
            steps.clear();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isScenarioStarted = false;
    }

    @Override
    public void done() {

    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {

    }

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {

    }

    @Override
    public void after(Match match, Result result) {

    }

    @Override
    public void match(Match match) {

    }

    @Override
    public void embedding(String mimeType, byte[] data) {

    }

    @Override
    public void write(String text) {

    }
}