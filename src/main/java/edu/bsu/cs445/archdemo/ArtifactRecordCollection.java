package edu.bsu.cs445.archdemo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="metadata")
@XmlAccessorType(XmlAccessType.FIELD)
class ArtifactRecordCollection {

    // This item is used by the JAXB parsing but not used in custom code.
    @SuppressWarnings({"unused","MismatchedQueryAndUpdateOfCollection"})
    @XmlElement(name="record")
    private List<ArtifactRecord> items;


    int size() {
        return items.size();
    }
}
