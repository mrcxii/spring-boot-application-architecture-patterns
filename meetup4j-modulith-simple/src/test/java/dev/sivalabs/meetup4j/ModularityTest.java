package dev.sivalabs.meetup4j;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {

    ApplicationModules modules = ApplicationModules.of(Meetup4jApplication.class);

    @Test
    void testModularity() {
        modules.verify();
    }
}
