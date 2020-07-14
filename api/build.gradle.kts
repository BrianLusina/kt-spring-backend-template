plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")
}

allOpen {
    annotation("org.springframework.web.bind.annotation.RestController")
    annotation("org.springframework.web.bind.annotation.RequestMapping")
}

dependencies {
    implementation(project(":core"))
    implementation(Libs.Utils.fasterXmlJacksonModule)
    implementation(Libs.SpringBoot.web)
    implementation(Libs.SpringBoot.springFoxSwaggerUi)
    implementation(Libs.SpringBoot.springFoxSwagger2)
    compileOnly(Libs.Utils.lombok)
    testImplementation(Libs.SpringBoot.Test.starterTest)
}
