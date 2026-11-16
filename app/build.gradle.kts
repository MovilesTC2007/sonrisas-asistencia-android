val ktlintCli = configurations.create("ktlintCli")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "mx.tec.sonrisas.asistencia"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "mx.tec.sonrisas.asistencia"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    detektPlugins(libs.detekt.formatting)
    add("ktlintCli", "com.pinterest.ktlint:ktlint-cli:1.5.0") // final version upgrade
}
// configuration tools for ci/cd
tasks.register<JavaExec>("ktlintSourcesCheck") {
    group = "verification"
    description = "Revisa el formato de src/**/*.kt con ktlint"
    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")
    args("src/**/*.kt", "!**/build/**")
}

tasks.register<JavaExec>("ktlintSourcesFormat") {
    group = "formatting"
    description = "Corrige automáticamente el formato de src/**/*.kt"
    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")
    args("-F", "src/**/*.kt", "!**/build/**")
}

tasks.named("ktlintCheck") { dependsOn("ktlintSourcesCheck") }
tasks.named("ktlintFormat") { dependsOn("ktlintSourcesFormat") }

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/config/detekt/detekt.yml")) // specific config rules
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "11"
}
