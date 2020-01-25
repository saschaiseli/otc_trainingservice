//package ch.opentrainingcenter.otc.training.archunit;
//
//import com.tngtech.archunit.junit.AnalyzeClasses;
//import com.tngtech.archunit.junit.ArchTest;
//import com.tngtech.archunit.lang.ArchRule;
//
//import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
//
//@AnalyzeClasses(packages = "ch.opentrainingcenter")
//public class LayeredArchitectureTest {
//    @ArchTest
//    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
//
//            .layer("Controllers").definedBy("ch.opentrainingcenter.otc.training.boundary..")
//            .layer("Services").definedBy("ch.opentrainingcenter.otc.training.service..")
//            .layer("Persistence").definedBy("ch.opentrainingcenter.otc.training.repository..")
//
//            .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
//            .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
//            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Services");
//
//    @ArchTest
//    static final ArchRule layer_dependencies_are_respected_with_exception = layeredArchitecture()
//
//            .layer("Controllers").definedBy("ch.opentrainingcenter.otc.training.boundary..")
//            .layer("Services").definedBy("ch.opentrainingcenter.otc.training.service..")
//            .layer("Persistence").definedBy("ch.opentrainingcenter.otc.training.repository..")
//
//            .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
//            .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
//            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Services");
//
////            .ignoreDependency(SomeMediator.class, ServiceViolatingLayerRules.class);
//}
