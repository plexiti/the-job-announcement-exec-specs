package cucumber.runtime.formatter;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;

import java.io.File;

/**
 * This is a Gehrkin formatter that is capable of passing current Feature and Scenario
 * to the @Before and @After hooks.
 *
 * See https://groups.google.com/forum/#!topic/cukes/uxXjyk6dqr4/discussion
 * for background
 *
 */
public class JUnitFeatureAndScenarioAwareFormatter extends JUnitFormatter implements Formatter, Reporter {

    private static ThreadLocal<Feature> theCurrentFeature = new ThreadLocal<Feature>();
    private static ThreadLocal<Scenario> theCurrentScenario = new ThreadLocal<Scenario>();

    public JUnitFeatureAndScenarioAwareFormatter(File out) {
        super(out);
    }

    @Override
    public void feature(Feature feature) {
        super.feature(feature);
        theCurrentFeature.set(feature);
        //System.err.println("  In formatter (feature): " + feature.getName());
    }

    @Override
    public void scenario(Scenario scenario) {
        super.scenario(scenario);
        theCurrentScenario.set(scenario);
        //System.err.println("  In formatter (scenario): " + scenario.getName());
    }

    public static Feature getCurrentFeature() {
        return theCurrentFeature.get();
    }

    public static Scenario getCurrentScenario() {
        return theCurrentScenario.get();
    }

    public static Scenario popCurrentScenario() {
        Scenario scenario =  theCurrentScenario.get();
        theCurrentScenario.set(null);
        return scenario;
    }
}
