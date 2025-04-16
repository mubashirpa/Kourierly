package com.evaluation.kourierly.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.evaluation.kourierly.navigation.KourierlyNavHost
import com.evaluation.kourierly.presentation.theme.KourierlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KourierlyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0),
                ) { innerPadding ->
                    KourierlyNavHost(
                        navController = rememberNavController(),
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .imePadding(),
                    )
                }
            }
        }
    }
}
