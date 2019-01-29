package edu.bsu.cs445.archdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class JaxbParserTest {

    @DisplayName("Test that the parser finds the right number of records")
    @ParameterizedTest(name = "Number of records in {0} should be {1}")
    @CsvSource({"owsley-FirstRecord.xml, 1", "owsley-FirstThreeRecords.xml, 3"})
    void testParse_numberOfRecords(String inputSource, int expectedLength) {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(inputSource);
        ArtifactRecordCollection collection = parser.parse(input);
        assertEquals(expectedLength, collection.size());
    }

    @DisplayName("Return filtered title")
    @Test
    void testParse_filteredTitle() {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley-FirstThreeRecords.xml");
        ArtifactRecordCollection collection = parser.parse(input);
        List<ArtifactRecord> result = collection.stream()
                .filter(artifactRecord -> artifactRecord.getTitle().contains("Revenge")
                        || artifactRecord.getTitle().contains("Reve"))
                .collect(Collectors.toList());
        assertEquals("Revenge", result.get(0).getTitle());
        assertEquals("Willem de Kooning ; Harold Rosenberg (Poem)", result.get(0).getArtist());
    }

    @DisplayName("Return filtered artist")
    @Test
    void testParse_filteredArtist() {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley-FirstThreeRecords.xml");
        ArtifactRecordCollection collection = parser.parse(input);
        List<ArtifactRecord> result = collection.stream()
                .filter(artifactRecord -> artifactRecord.getArtist().contains("Willem de Kooning")
                        || artifactRecord.getArtist().contains("Will")
                        || artifactRecord.getArtist().contains("Harold"))
                .collect(Collectors.toList());
        assertEquals("Revenge", result.get(0).getTitle());
        assertEquals("Willem de Kooning ; Harold Rosenberg (Poem)", result.get(0).getArtist());
    }

    @DisplayName("Provide only relevant items by ensuring size and validity")
    @Test
    void testParse_onlyMatchedItems() {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley-FirstThreeRecords.xml");
        ArtifactRecordCollection collection = parser.parse(input);
        List<ArtifactRecord> result = collection.stream()
                .filter(artifactRecord -> artifactRecord.getTitle().contains("Revenge")
                        || artifactRecord.getTitle().contains("Revenge"))
                .collect(Collectors.toList());
        assertEquals(1, result.size());
        assertNotEquals("Fishing Boats (Fischerboote)", result.get(0).getTitle());
        assertNotEquals("Karl Schmidt-Rottluff", result.get(0).getArtist());
        assertNotEquals("Head of a Girl (Frauenbild)", result.get(0).getTitle());
        assertNotEquals("Emil Nolde", result.get(0).getArtist());
    }

    @DisplayName("Return correct title and artist of searched term")
    @Test
    void testParse_ReturnCorrectTitleAndArtist() {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley-FirstRecord.xml");
        ArtifactRecordCollection collection = parser.parse(input);
        List<ArtifactRecord> result = collection.stream()
                .filter(artifactRecord -> artifactRecord.getTitle().contains("Head of a Girl (Frauenbild)")
                        || artifactRecord.getArtist().contains("Head of a Girl (Frauenbild)"))
                .collect(Collectors.toList());
        assertEquals("Head of a Girl (Frauenbild)", result.get(0).getTitle());
        assertEquals("Emil Nolde", result.get(0).getArtist());
    }

    @DisplayName("Return zero results for search term not matched")
    @Test
    void testParse_NonMatchingSearchTerm() {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley-FirstThreeRecords.xml");
        ArtifactRecordCollection collection = parser.parse(input);
        List<ArtifactRecord> result = collection.stream()
                .filter(artifactRecord -> artifactRecord.getTitle().contains("Pickles")
                        || artifactRecord.getArtist().contains("Pickles"))
                .collect(Collectors.toList());
        assertEquals(0, result.size());
    }
}
