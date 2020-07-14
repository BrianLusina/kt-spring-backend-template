plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotation("org.springframework.stereotype.Repository")
    annotation("org.springframework.stereotype.Component")
}

dependencies {
    implementation(project(":core"))
    implementation(Libs.Data.springBootJdbc)
    implementation(Libs.SpringBoot.dataJpa)
    implementation(Libs.Data.postgres)
    compileOnly(Libs.Utils.lombok)
    testImplementation(Libs.SpringBoot.Test.starterTest)
}
