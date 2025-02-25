package com.example.instalog

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instalog.ui.theme.Green100
import com.example.instalog.ui.theme.InstalogTheme
import com.example.instalog.ui.theme.Purple40
import com.instalog.mobile.Instalog
import com.instalog.mobile.InstalogAlertDialogHandler
import com.instalog.mobile.models.InstalogLogModel
import com.instalog.mobile.ui.Gap
import com.instalog.mobile.ui.InstalogAlert
import com.instalog.mobile.ui.InstalogAlertData
import com.instalog.mobile.ui.InstalogButton
import com.instalog.mobile.ui.InstalogColors
import com.instalog.mobile.ui.InstalogTextField

class MainActivity : ComponentActivity(), InstalogAlertDialogHandler {
    private val instalog = Instalog.getInstance()

    private var showDialog by mutableStateOf(false) // State to control dialog visibility
    private var dialogData by mutableStateOf(InstalogAlertData("", "", "") {})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instalog.initialize(
            key = "instalog_9b2d95c905d643a683d255242d1c46cc",
            context = this,
            handler = this
        )

        instalog.identifyUser("chiziaruhoma@gmail.com")

        setContent {
            InstalogTheme {
                // A surface container using the 'background' color from the theme
                Home(instalog)

                // Show the dialog if `showDialog` is true
                if (showDialog) {
                    InstalogAlert.Show(
                        dialogData.copy(
                            onDismiss = { showDialog = false }
                        )
                    )
                }
            }
        }
    }

    override fun show(data: InstalogAlertData) {
        showDialog = true
        dialogData = data
    }
}

@Composable
fun Home(instalog: Instalog) {

    val padding = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp)

    var text by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Instalog",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold
            ),
        )
        Gap(height = 2.dp)
        Text(
            text = "Welcome to Instalog's Android example",
            color = InstalogColors.TextGrey
        )
        Gap(height = 30.dp)

        InstalogTextField(
            modifier = padding,
            value = text,
            onValueChange = { text = it },
            placeholder = "Enter api key ..."
        )

        Gap(height = 100.dp)

        InstalogButton(
            text = "Feedback",
            modifier = padding,
            buttonColor = Green100,
            onClick = {
                instalog.showFeedbackModal(context)
            }
        )

        Gap(height = 20.dp)

        InstalogButton(
            text = "Send Event",
            modifier = padding,
            buttonColor = Color.Black,
            onClick = {
                handleLogEvent(context, instalog)
            }
        )

        Gap(height = 20.dp)

        InstalogButton(
            text = "Simulate Crash",
            modifier = padding,
            buttonColor = Purple40,
            onClick = {
                Instalog.crash.simulateExceptionCrash()
            }
        )
    }
}

fun handleLogEvent(context: Context, instalog: Instalog) {
    val log = InstalogLogModel(
        event = "hello_world",
        params = mapOf(
            "eventName" to "UserLogin",
            "timestamp" to System.currentTimeMillis().toString(),
            "userId" to "98765",
            "source" to "web",
            "location" to "New York, USA",
            "device" to "Android Emulator"
        )
    )
    instalog.logEvent(context, log = log)
}