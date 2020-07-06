package ru.ase.ims.enomanager.model.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ase.ims.enomanager.model.enovia.xml.Property;

import java.util.List;

public interface Vertex {

    @JsonProperty("type")
    String getObjectType();
    List<Property> getProperties();
    @JsonIgnore
    List<Vertex> getSourceVertexList();
    @JsonIgnore
    List<Vertex> getTargetVertexList();
    void addTargetVertex(Vertex vertex);
    void addSourceVertex(Vertex vertex);
    String getName();
    List<String> getTargetVertexNameList();
    List<String> getSourceVertexNameList();
 }
