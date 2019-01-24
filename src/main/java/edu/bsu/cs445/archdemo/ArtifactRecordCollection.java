package edu.bsu.cs445.archdemo;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Stream;

@XmlRootElement(name="metadata")
@XmlAccessorType(XmlAccessType.FIELD)
class ArtifactRecordCollection {

    // This item is used by the JAXB parsing but not used in custom code.
    @SuppressWarnings({"unused","MismatchedQueryAndUpdateOfCollection"})
    @XmlElement(name="record")
    private List<ArtifactRecord> items = Lists.newArrayList();

    int size() {
        return items.size();
    }

    Stream<ArtifactRecord> stream() {
        return items.stream();
    }
}
