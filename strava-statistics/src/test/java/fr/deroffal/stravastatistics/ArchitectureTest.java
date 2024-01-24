package fr.deroffal.stravastatistics;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(packagesOf = ArchitectureTest.class)
public class ArchitectureTest {

  private static final String BASE_PACKAGE = ArchitectureTest.class.getPackage().getName();
  private static final String APP_PACKAGE = BASE_PACKAGE + ".app";
  private static final String GENERATED_MODEL_PACKAGE = BASE_PACKAGE + ".model";
  private static final String CLIENT_PACKAGES = BASE_PACKAGE + ".client..";
  private static final String PERSISTENCE_PACKAGE = BASE_PACKAGE + ".repository";

  @ArchTest
  public void enforceHexagonalArchitecture(JavaClasses classes) {
    classes()
        .that().resideInAnyPackage(APP_PACKAGE, GENERATED_MODEL_PACKAGE)
        .should().onlyAccessClassesThat().resideInAnyPackage(
            // java
            "java..",
            // logging
            "org.slf4j..",
            // app packages
            APP_PACKAGE, GENERATED_MODEL_PACKAGE
        )
        .check(classes);
  }

  @ArchTest
  public void checkDependenciesOnClientModule(JavaClasses classes) {
    noClasses().that().resideInAPackage(CLIENT_PACKAGES)
        .should().onlyBeAccessed().byClassesThat().resideOutsideOfPackage(CLIENT_PACKAGES)
        .check(classes);

    noClasses().that().resideOutsideOfPackage(CLIENT_PACKAGES)
        .should().accessClassesThat().resideInAnyPackage("org.springframework.http", "org.springframework.web")
        .check(classes);
  }

  @ArchTest
  public void checkDependenciesOnPersistenceModule(JavaClasses classes) {
    noClasses().that().resideInAPackage(PERSISTENCE_PACKAGE)
        .should().onlyBeAccessed().byClassesThat().resideOutsideOfPackage(PERSISTENCE_PACKAGE)
        .check(classes);

    noClasses().that().resideOutsideOfPackage(PERSISTENCE_PACKAGE)
        .should().accessClassesThat().resideInAnyPackage("org.springframework.data", "com.mongodb")
        .check(classes);
  }

}
