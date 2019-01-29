package edu.bsu.cs445.archdemo;

import com.google.common.base.Preconditions;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

class ArtifactRecordCollectionSearch {

    List<ArtifactRecord> search(String searchTerm) {
        JaxbParser parser = JaxbParser.create();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("owsley.xml");
        ArtifactRecordCollection collection = parser.parse(input);
        Preconditions.checkNotNull(collection, "The collection should already be in memory");
        return collection.stream()
                .filter(artifactRecord -> artifactRecord.getTitle().contains(searchTerm)
                        || artifactRecord.getArtist().contains(searchTerm))
                .collect(Collectors.toList());
    }
}
