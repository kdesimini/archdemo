package edu.bsu.cs445.archdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class ArtifactRecordCollectionTest {

    @DisplayName("Test that an empty collection yields an empty stream")
    @Test
    void testStream_emptyCollection_emptyStream() {
        ArtifactRecordCollection collection = new ArtifactRecordCollection();
        Stream<ArtifactRecord> stream = collection.stream();
        Assertions.assertEquals(0, stream.count());
    }
}
