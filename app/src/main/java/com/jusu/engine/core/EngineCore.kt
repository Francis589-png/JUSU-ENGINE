package com.jusu.engine.core

class EngineEventBus {
    private val history = mutableListOf<EngineEvent>()
    fun publish(event: EngineEvent) { history += event }
    fun history(): List<EngineEvent> = history.toList()
}

data class EngineEvent(val type: String, val message: String)

sealed interface EngineCommand {
    data object Diagnose : EngineCommand
    data class Explain(val subject: String) : EngineCommand
    data class Simulate(val action: String) : EngineCommand
}

data class EngineResult(val success: Boolean, val message: String)

class LocalCommandEngine(private val bus: EngineEventBus) {
    fun execute(command: EngineCommand): EngineResult = when (command) {
        EngineCommand.Diagnose -> {
            val result = EngineResult(true, "Local diagnostic engine is ready. No internet connection is required.")
            bus.publish(EngineEvent("DIAGNOSE", result.message))
            result
        }
        is EngineCommand.Explain -> {
            val result = EngineResult(true, "Explanation requested for: ${command.subject}")
            bus.publish(EngineEvent("EXPLAIN", result.message))
            result
        }
        is EngineCommand.Simulate -> {
            val result = EngineResult(true, "Simulation completed without changing device state: ${command.action}")
            bus.publish(EngineEvent("SIMULATE", result.message))
            result
        }
    }
}
