// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.2.10-2.0.2" apply false
    // Google Developers (n.d.) Migrate from kapt to KSP. Available at: https://developer.android.com/build/migrate-to-ksp#add-ksp
    // (Accessed: 13 April 2026)
}