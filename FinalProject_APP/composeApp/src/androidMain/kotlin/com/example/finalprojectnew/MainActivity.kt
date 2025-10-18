package com.example.finalprojectnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.finalprojectnew.assessment.storage.AssessmentStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // ⬅️ חשוב: אתחול למחסן הקבצים באנדרואיד
        AssessmentStorage.init(applicationContext)

        setContent { App() }
    }
}
