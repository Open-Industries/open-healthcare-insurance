package org.openindustries.healthcare.functions.adequacy;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares provider-network gap counts and coverage percentage against targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String networkId, int measuredGapCount, int targetGapCount,
                      double measuredCoveragePct, double targetCoveragePct) {
    }

    public record Verdict(String networkId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict networkAdequacyEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredGapCount() > kpi.targetGapCount()) {
            findings.add("gapCount " + kpi.measuredGapCount() + " exceeds target " + kpi.targetGapCount());
        }
        if (kpi.measuredCoveragePct() < kpi.targetCoveragePct()) {
            findings.add("coveragePct " + kpi.measuredCoveragePct() + " below target " + kpi.targetCoveragePct());
        }
        return new Verdict(kpi.networkId(), findings.isEmpty(), findings);
    }
}
