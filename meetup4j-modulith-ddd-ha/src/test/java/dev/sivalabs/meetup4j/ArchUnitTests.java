package dev.sivalabs.meetup4j;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

class ArchUnitTests {


    @ParameterizedTest
    @ValueSource(strings = {"events", "registrations"})
    void shouldVerifyHexagonalArchitecture(String moduleName) {
        JavaClasses classes = new ClassFileImporter().importPackages("dev.sivalabs.meetup4j.%s".formatted(moduleName));

        onionArchitecture()
                .withOptionalLayers(true)
                .domainModels("dev.sivalabs.meetup4j.%s.domain..".formatted(moduleName))
                //.domainServices("dev.sivalabs.meetup4j.%s.domain.service..".formatted(moduleName))
                .applicationServices("dev.sivalabs.meetup4j.%s.application..".formatted(moduleName))
                .adapter("persistence", "dev.sivalabs.meetup4j.%s.infra.persistence..".formatted(moduleName))
                .adapter("rest", "dev.sivalabs.meetup4j.%s.interfaces.rest..".formatted(moduleName))
                .check(classes);
    }
}
