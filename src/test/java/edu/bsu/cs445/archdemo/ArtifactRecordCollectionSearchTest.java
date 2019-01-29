package edu.bsu.cs445.archdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ArtifactRecordCollectionSearchTest {


    @DisplayName("Return filtered title")
    @Test
    void testParse_filteredTitle() {

        ArtifactRecordCollectionSearch recordSearch = new ArtifactRecordCollectionSearch();
        List<ArtifactRecord> result = recordSearch.search("Revenge");
        assertEquals("Revenge", result.get(0).getTitle());
        assertEquals("Willem de Kooning ; Harold Rosenberg (Poem)", result.get(0).getArtist());
    }

    @DisplayName("Return filtered artist")
    @Test
    void testParse_filteredArtist() {
        ArtifactRecordCollectionSearch recordSearch = new ArtifactRecordCollectionSearch();
        List<ArtifactRecord> result = recordSearch.search("Revenge");
        assertEquals("Revenge", result.get(0).getTitle());
        assertEquals("Willem de Kooning ; Harold Rosenberg (Poem)", result.get(0).getArtist());
    }

    @DisplayName("Return zero items if searchTerm does not match")
    @Test
    void testParse_searchTermDoesNotMatch() {
        ArtifactRecordCollectionSearch recordSearch = new ArtifactRecordCollectionSearch();
        List<ArtifactRecord> result = recordSearch.search("Pickles");
        assertEquals(0, result.size());
    }
}
