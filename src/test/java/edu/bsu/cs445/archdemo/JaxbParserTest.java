package edu.bsu.cs445.archdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JaxbParserTest {

    @DisplayName("Test that the parser finds the right number of records")
    @ParameterizedTest(name="Number of records in {0} should be {1}")
    @CsvSource({"owsley-FirstRecord.xml, 1", "owsley-FirstThreeRecords.xml, 3"})
    void testParse_numberOfRecords(String inputSource, int expectedLength) {
        JaxbParser parser = new JaxbParser();
        InputStream input =  Thread.currentThread().getContextClassLoader().getResourceAsStream(inputSource);
        ArtifactRecordCollection collection = parser.parse(input);
        assertEquals(expectedLength, collection.size());
    }
}
