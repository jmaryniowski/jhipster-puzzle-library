package pl.maryniowski.apps.puzzlelibrary;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("pl.maryniowski.apps.puzzlelibrary");

        noClasses()
            .that()
            .resideInAnyPackage("pl.maryniowski.apps.puzzlelibrary.service..")
            .or()
            .resideInAnyPackage("pl.maryniowski.apps.puzzlelibrary.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..pl.maryniowski.apps.puzzlelibrary.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
