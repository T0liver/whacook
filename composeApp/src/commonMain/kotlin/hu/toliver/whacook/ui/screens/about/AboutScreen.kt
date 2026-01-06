package hu.toliver.whacook.ui.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import hu.toliver.whacook.ui.components.BackButton
import hu.toliver.whacook.ui.components.BodyText
import hu.toliver.whacook.ui.components.BodyTextHeader
import hu.toliver.whacook.ui.components.BodyTextHyperlink

class AboutScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton()
                Spacer(modifier = Modifier.width(8.dp))
                BodyTextHeader("About")
            }
            Spacer(modifier = Modifier.height(32.dp))
            BodyText("This application was made for the 2026 KotlinConf application contest.")
            Spacer(modifier = Modifier.height(16.dp))
            BodyTextHyperlink(
                "Icons by Icons8.",
                "https://icons8.com"
            )
            Spacer(modifier = Modifier.height(16.dp))
            BodyText("2025 © T0liver")
            Spacer(Modifier.height(120.dp))
        }
    }
}

// _|_
//  |
//  |