plugins {
    kotlin("jvm")
    id(Plugins.openApiGeneratorPlugin) version Plugins.openApiGeneratorPluginVersion
}

// Ref: https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/README.adoc
openApiGenerate {
    id.set("email-validation")
    generatorName.set("kotlin")
    inputSpec.set("$rootDir/app/services/src/main/resources/contracts/email-validation.yaml")
    outputDir.set("$buildDir/generated")
    modelPackage.set("com.imbank.omni.registration.services.corebanking.dto")
    modelNameSuffix.set("Response")
}

openApiValidate {
    inputSpec.set("$rootDir/app/services/src/main/resources/contracts/email-validation.yaml")
    recommend.set(true)
}

val generateOpenApi by tasks.registering(org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    group = "build"
    description = "Generates OpenApi Model"
    id.set("email-validation")
    generatorName.set("kotlin")
    inputSpec.set("$rootDir/app/services/src/main/resources/contracts/email-validation.yaml")
    outputDir.set("$buildDir/generated")
    modelPackage.set("com.imbank.omni.registration.services.corebanking.dto")
    modelNameSuffix.set("Response")
    dependsOn.add(setOf("app:config:bootJar"))
    mustRunAfter(":app:config:bootJar", "build")
    doLast {
        logger.info(">> Done Copying app build")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(Libs.SpringBoot.web)
    implementation(Libs.Utils.fasterXmlJacksonModule)
    implementation(Libs.SpringBoot.thymeleaf)
    compileOnly(Libs.Utils.lombok)
    testImplementation(Libs.SpringBoot.Test.starterTest)
}
