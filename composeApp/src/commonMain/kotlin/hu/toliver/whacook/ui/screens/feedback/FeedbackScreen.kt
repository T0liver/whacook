package hu.toliver.whacook.ui.screens.feedback

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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import hu.toliver.whacook.ui.components.BackButton
import hu.toliver.whacook.ui.components.BodyText
import hu.toliver.whacook.ui.components.BodyTextHeader

class FeedbackScreen : Screen {
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
                BodyTextHeader("Feedback")
            }
            Spacer(modifier = Modifier.height(32.dp))
            BodyText(text = "Any feedback is appreciated!")
            Spacer(modifier = Modifier.height(16.dp))
            BodyText(
                "If you have any suggestion with this\napplication, any problem, or any little\nthing how this could be improved just\nsend an email to",
            )
            Spacer(modifier = Modifier.height(16.dp))
            BodyTextHeader("whacook@toliver.hu")
            Spacer(modifier = Modifier.height(16.dp))
            BodyText("or open an issue/pull request on Github!")
        }
    }
}
