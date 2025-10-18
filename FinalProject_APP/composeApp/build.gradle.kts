plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

kotlin {
    // יעד אנדרואיד (מומלץ לגרסאות Compose שונות)
    android()

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }


    // יעדי iOS (ייבנו רק אם יש toolchain מתאים)
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        // Opt-in גלובלי לניסיוני
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            }
        }

        val commonMain by getting {
            resources.srcDirs(
                "src/commonMain/resources",
                "src/commonMain/composeResources"
            )
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                // הערכה/שמירה ל-JSON + זמן
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
        }

        val commonTest by getting {
            dependencies { implementation(libs.kotlin.test) }
        }
    }
}

android {
    namespace = "com.example.finalprojectnew"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.finalprojectnew"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") { isMinifyEnabled = false }
    }

    // Java על 11 (תואם ל-Kotlin)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

compose {
    resources {
        publicResClass = true
    }
}

// תצוגות מקדימות בלבד
dependencies {
    debugImplementation(compose.uiTooling)
}

// רשת ביטחון: מכוון את כל משימות ה-Kotlin ל-JVM 11
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

// משימה לחידוש מחולל ה-Res
tasks.register("regenImages") {
    dependsOn("clean", "generateResourceAccessorsForCommonMain")
}
