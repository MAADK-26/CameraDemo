package com.pes.camerademo

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pes.camerademo.ui.theme.CameraDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {


    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var status by rememberSaveable { mutableStateOf("Preview Demo") }


    val previewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            previewBitmap = bitmap
            status = "Preview captured . This bitmap is in memory and is not automatically saved."
        } else {
            status = "Preview capture cancelled or no preview returned."
        }
    }

    Column( modifier = modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Hello $name!",

        )
        Button(onClick = { previewLauncher.launch(null) }) { Text("Take preview photo") }
        Text("Status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(status, modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.bodyMedium)

        if (previewBitmap != null) {

            Spacer(Modifier.height(12.dp))
            Image(
                bitmap = previewBitmap!!.asImageBitmap(),
                contentDescription = "Preview photo captured by camera",
                modifier = Modifier.size(240.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CameraDemoTheme {
        Greeting("Android")
    }
}