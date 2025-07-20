import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
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
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.compose)
            implementation(libs.kmp.capturable.compose)
            // Koin dependencies
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // Ktor dependencies
            implementation(libs.ktor.client.okhttp)

        }
        commonMain.dependencies {
            implementation(projects.hitber)
            implementation(projects.ui.core)
            implementation(projects.core)
            implementation(projects.oriantation)
            implementation(projects.clockTest)
            implementation(projects.pass)
            implementation(projects.newMemoryTest)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.datetime)
            implementation(compose.material3)


            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Voyager Navigator
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)
            // Basic Navigation

//            implementation (libs.navigator)
//            implementation (libs.navigator.tabs)
//            implementation (libs.navigator.transitions)

            // Koin dependencies
            api(libs.koin.core)
            implementation(libs.bundles.koin.compose)
            implementation(libs.lifecycle.viewmodel)

            implementation(compose.materialIconsExtended)
            implementation(libs.font.awesome)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization) // for data serialization
            implementation(compose.materialIconsExtended)
            implementation(libs.font.awesome)
            implementation(libs.datastore.preferences)
            implementation(libs.datastore)

            implementation(libs.voyager.tabNavigator)
            implementation(libs.bundles.ktor)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)


            implementation(libs.coil.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
           // implementation(libs.voyager.navigator.v110beta02)
            //implementation(libs.voyager.transitions.v110beta02)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization)

            //implementation("io.github.moonggae:kmedia:0.0.3")



        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.kmp.capturable.compose)

        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(compose.desktop.common)
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "org.example.hit.heal"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.example.hit.heal"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.play.services.cast.framework)
    debugImplementation(compose.uiTooling)


}

compose.desktop {
    application {
        mainClass = "org.example.hit.heal.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.hit.heal"
            packageVersion = "1.0.0"
        }
    }
}
