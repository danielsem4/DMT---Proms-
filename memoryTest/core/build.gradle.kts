import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.10"


}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "memoryTest"
            isStatic = true
        }
    }
    jvm("desktop")
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            val voyagerVersion = "1.1.0-beta02"
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.bundles.voyager.common)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1")


            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation("network.chaintech:kmp-date-time-picker:1.0.7")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

            implementation(libs.navigation.compose)
            implementation(libs.viewmodel.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.compose.dnd)  //drag and drop dependencies



        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.compose.dnd)
        }
    }

}

android {
    namespace = "org.example.hit.heal.memoryTest"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {

        minSdk = libs.versions.android.minSdk.get().toInt()



    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(libs.compose.dnd)
}

compose.desktop {
    application {
        mainClass = "org.example.hit.heal.memoryTest"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.hit.heal.memoryTest"
            packageVersion = "1.0.0"
        }
    }
}