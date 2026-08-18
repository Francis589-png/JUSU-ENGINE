package com.jusu.engine

import com.jusu.engine.security.RiskLevel
import com.jusu.engine.security.SecurityEngine
import org.junit.Assert.assertEquals
import org.junit.Test

class SecurityEngineTest {
    @Test fun defaultRiskIsLow() {
        assertEquals(RiskLevel.LOW, SecurityEngine().currentRisk())
    }

    @Test fun repeatedFailuresRaiseRisk() {
        val engine = SecurityEngine()
        assertEquals(RiskLevel.HIGH, engine.evaluateSignals(5, false, false))
    }

    @Test fun sensitiveExposureRaisesRisk() {
        val engine = SecurityEngine()
        assertEquals(RiskLevel.MEDIUM, engine.evaluateSignals(0, false, true))
    }
}
