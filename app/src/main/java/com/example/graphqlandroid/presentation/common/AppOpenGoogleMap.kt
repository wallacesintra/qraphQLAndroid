package com.example.graphqlandroid.presentation.common

import android.content.Intent
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
            val gmmIntentUri = "geo:$latitude,$longitude".toUri()
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            }else{
                Toast.makeText(context, "Google Map not available", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text(text)
    }

}