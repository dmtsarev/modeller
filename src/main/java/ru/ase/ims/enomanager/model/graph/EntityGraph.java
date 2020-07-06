package ru.ase.ims.enomanager.model.graph;

import java.io.Serializable;
import java.util.List;

public class EntityGraph implements Serializable {
    public EntityGraph() {
    }

    public EntityGraph(List<GraphVertexDTO> nodes, List<GraphEdgeDTO> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    private List<GraphVertexDTO> nodes;
    private List<GraphEdgeDTO> edges;

    public List<GraphVertexDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphVertexDTO> nodes) {
        this.nodes = nodes;
    }

    public List<GraphEdgeDTO> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdgeDTO> edges) {
        this.edges = edges;
    }
}
