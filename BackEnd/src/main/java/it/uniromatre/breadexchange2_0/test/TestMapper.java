package it.uniromatre.breadexchange2_0.test;

import it.uniromatre.breadexchange2_0.file.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class TestMapper {


    public TestClass toTest(TestRequest request) {
        return TestClass.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();
    }

    public TestResponse toTestResponse(TestClass testClass) {
        return TestResponse.builder()
                .id(testClass.getId())
                .name(testClass.getName())
                .description(testClass.getDescription())
                .owner(testClass.getOwner().getName())
                .test_url(FileUtils.readFileFromLocation(testClass.getTest_url()))
                .build();
    }
}
