package com.example.graphqlandroid.presentation.common

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun AppOpenGoogleMap(
    modifier: Modifier = Modifier,
    text: String = "View location on Google Map",
    latitude: Double,
    longitude: Double
) {

    val context = LocalContext.current
    TextButton(
        modifier = modifier,
        onClick = {

            val viewMapIntent: Intent = Intent(Intent.ACTION_VIEW, "geo: $latitude, $longitude".toUri())
                .setPackage("com.google.android.apps.maps")


            try {
                context.startActivity(viewMapIntent)
            }catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text(text)
    }

}