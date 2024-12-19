package com.enterprise.androidview

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.enterprise.androidview.ui.theme.AndroidViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidViewTheme {
                AndroidViewApp()
            }
        }
    }
}

@Composable
fun AndroidViewApp() {


    val textOfTextView = rememberSaveable { mutableStateOf("Test Successful!") }
    val counter = rememberSaveable { mutableStateOf(0) }

    Scaffold(modifier = Modifier.systemBarsPadding().fillMaxSize()) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Text("To reset, click Success text")

            Spacer(modifier = Modifier.height(10.dp))

            AndroidView(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(), // Occupy the max size in the Compose UI tree
                factory = { context ->
                    // Creates view
                    TextView(context).apply {

                        textSize = 25F
                        setTextColor(context.getColor(R.color.teal_200))

                        // Sets up listeners for View -> Compose communication
                        setOnClickListener {
                            text = ""
                            counter.value = 0
                        }

                    }
                },
                update = { view ->
                    // View's been inflated or state read in this block has been updated
                    // Add logic here if necessary

                    // As selectedItem is read here, AndroidView will recompose
                    // whenever the state changes
                    // Example of Compose -> View communication
                    view.text = textOfTextView.value

                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                onClick = {

                counter.value = counter.value + 1
                textOfTextView.value =  "Success! " + counter.value

            }) {

                Text("Increment")

            }




        }
    }

}

