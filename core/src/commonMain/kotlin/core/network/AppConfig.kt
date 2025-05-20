@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package core.network

expect object AppConfig {
    val BASE_URL: String
    val UPLOADS_BASE_URL: String
}