
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.androidLibrary)


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
            baseName = "uiCore"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting


        commonMain {
            dependencies {
                implementation(projects.core)
                implementation(projects.ui.core)



                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.serialization.json)

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
                implementation(libs.ktor.serialization.kotlinx.json)
                //implementation(libs.kmp.capturable.compose)
                implementation(compose.material3)
                implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")
                implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
                implementation(libs.voyager.navigator.v110beta02)
                implementation(libs.voyager.transitions.v110beta02)
                implementation(libs.jetbrains.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)

                implementation(libs.kmp.date.time.picker)
                implementation(libs.jetbrains.kotlinx.datetime)

                implementation(libs.navigation.compose)
                implementation(libs.viewmodel.compose)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.compose.dnd)
                implementation(libs.compose.dnd)
                implementation(libs.kmp.capturable.compose)
                //drag and drop dependencies

            }
        }
        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.compose)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kmp.capturable.compose)
            }
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.kmp.capturable.compose)

        }

    }
}
android {
    namespace = "com.new_memory_test"
    compileSdk =  libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

}

 

