package org.openindustries.healthcare.functions.claimfraud;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores claim risk signals (velocity, mismatch, upcoding indicators) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String claimId, int velocityIndex, int mismatchIndex, int upcodingIndex) {
    }

    public record Score(String claimId, int score, String decision, String rationale) {
    }

    @Funq
    public Score claimFraudScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.velocityIndex() >= 70) {
            score += 40;
            why.append("velocityIndex >= 70; ");
        }
        if (event.mismatchIndex() >= 60) {
            score += 30;
            why.append("mismatchIndex >= 60; ");
        }
        if (event.upcodingIndex() >= 50) {
            score += 30;
            why.append("upcodingIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.claimId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
