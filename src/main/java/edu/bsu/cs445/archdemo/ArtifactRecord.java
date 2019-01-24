package edu.bsu.cs445.archdemo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="record")
@XmlAccessorType(XmlAccessType.FIELD)
class ArtifactRecord {
    @SuppressWarnings("unused") // Not set in source code, but set through JAXB.
    @XmlElement(name="Title")
    private String title;

    String getTitle() {
        return title;
    }
}
