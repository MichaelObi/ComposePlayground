package dev.michaelobi.clubhouse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import dev.michaelobi.clubhouse.ui.theme.ClubhouseTheme
import dev.michaelobi.clubhouse.ui.home.Home

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClubhouseUI()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClubhouseUI() {
    ClubhouseTheme {
        Home()
    }
}