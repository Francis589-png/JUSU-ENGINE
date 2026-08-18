package com.jusu.engine.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jusu.engine.core.EngineCommand
import com.jusu.engine.core.EngineEvent
import com.jusu.engine.core.EngineEventBus
import com.jusu.engine.core.LocalCommandEngine
import com.jusu.engine.security.RiskLevel
import com.jusu.engine.security.SecurityEngine

@Composable
fun JusuEngineApp(showSplash: Boolean) {
    AnimatedVisibility(visible = showSplash, enter = fadeIn(tween(350)), exit = fadeOut(tween(500))) { SplashScreen() }
    AnimatedVisibility(visible = !showSplash, enter = fadeIn(tween(500)), exit = fadeOut(tween(250))) { HomeScreen() }
}

@Composable
private fun SplashScreen() {
    val transition = rememberInfiniteTransition(label = "splash")
    val pulse by transition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(1200, easing = FastOutSlowInEasing)),
        label = "pulse"
    )
    Box(
        modifier = Modifier.fillMaxSize().background(Brush.radialGradient(listOf(Color(0xFF173B5F), Color(0xFF05070B), Color.Black))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(150.dp).scale(pulse).background(
                    Brush.linearGradient(listOf(Color(0xFF5EE7FF), Color(0xFF7B61FF), Color(0xFFFF4FD8))), RoundedCornerShape(44.dp)
                ),
                contentAlignment = Alignment.Center
            ) { Text("J", fontSize = 72.sp, fontWeight = FontWeight.Black, color = Color.White) }
            Spacer(Modifier.height(28.dp))
            Text("JUSU ENGINE", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(8.dp))
            Text("Powered by JUSU tech team JTT", color = Color(0xFFB8C4D8), fontSize = 14.sp)
            Spacer(Modifier.height(28.dp))
            Text("OFFLINE • PRIVATE • ENGINEERING", color = Color(0xFF5EE7FF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun HomeScreen() {
    val bus = remember { EngineEventBus() }
    val commandEngine = remember { LocalCommandEngine(bus) }
    val security = remember { SecurityEngine() }
    val events = remember { mutableStateListOf<EngineEvent>() }
    var lastCommand by remember { mutableStateOf("Ready for a command") }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF05070B)) {
        LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("JUSU ENGINE", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black)
                        Text("Personal OS intelligence", color = Color(0xFF8E9AAF), fontSize = 13.sp)
                    }
                    StatusPill()
                }
            }
            item { HealthCard(security) }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF0C111A)), shape = RoundedCornerShape(24.dp)) {
                    Column(Modifier.padding(18.dp)) {
                        Text("COMMAND CORE", color = Color(0xFF5EE7FF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(lastCommand, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                val result = commandEngine.execute(EngineCommand.Diagnose)
                                lastCommand = result.message
                                events.add(EngineEvent("COMMAND", result.message))
                            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5EE7FF), contentColor = Color.Black)) { Text("Diagnose") }
                            TextButton(onClick = {
                                lastCommand = "Sandbox: no changes were made."
                                events.add(EngineEvent("SANDBOX", "Simulation completed safely."))
                            }) { Text("Simulate") }
                        }
                    }
                }
            }
            item { Text("ENGINE MODULES", color = Color(0xFF8E9AAF), fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            items(listOf(
                "System Forensics" to "Investigate crashes, restarts and health events",
                "Privacy Guardian" to "Protect sensitive content when risk is detected",
                "Gesture Learning" to "Build personal offline interaction patterns",
                "Recovery / Undo" to "Track supported changes before they become risky",
                "Security Alarm" to "Score suspicious activity before responding"
            )) { (title, description) -> ModuleCard(title, description) }
            item { Text("RECENT AUDIT", color = Color(0xFF8E9AAF), fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            items(events.takeLast(5)) { event -> Text("${event.type}  •  ${event.message}", color = Color(0xFFB8C4D8), fontSize = 12.sp) }
        }
    }
}

@Composable
private fun StatusPill() {
    val alpha by animateFloatAsState(1f, label = "status")
    Box(modifier = Modifier.alpha(alpha).background(Color(0xFF10251F), RoundedCornerShape(50)).padding(horizontal = 12.dp, vertical = 7.dp)) {
        Text("● OFFLINE", color = Color(0xFF65F2B5), fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun HealthCard(security: SecurityEngine) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF0C111A)), shape = RoundedCornerShape(28.dp)) {
        Column(Modifier.padding(20.dp)) {
            Text("ENGINE STATUS", color = Color(0xFF8E9AAF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text("Protected and ready", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Local-only architecture • No internet permission • User remains in control", color = Color(0xFFB8C4D8), fontSize = 13.sp)
            Spacer(Modifier.height(16.dp))
            val risk = security.currentRisk()
            Text("Security risk: ${risk.name}", color = when (risk) {
                RiskLevel.LOW -> Color(0xFF65F2B5)
                RiskLevel.MEDIUM -> Color(0xFFFFC857)
                RiskLevel.HIGH -> Color(0xFFFF6B6B)
            }, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ModuleCard(title: String, description: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0F17)), shape = RoundedCornerShape(20.dp)) {
        Column(Modifier.padding(17.dp)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(description, color = Color(0xFF8E9AAF), fontSize = 12.sp)
        }
    }
}
