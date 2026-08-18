package com.jusu.engine.security

enum class RiskLevel { LOW, MEDIUM, HIGH }

class SecurityEngine {
    private var risk = RiskLevel.LOW

    fun currentRisk(): RiskLevel = risk

    fun evaluateSignals(failedAuth: Int, unusualPermissionChange: Boolean, sensitiveScreenExposed: Boolean): RiskLevel {
        risk = when {
            failedAuth >= 5 || (unusualPermissionChange && sensitiveScreenExposed) -> RiskLevel.HIGH
            failedAuth >= 2 || unusualPermissionChange || sensitiveScreenExposed -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
        return risk
    }
}
