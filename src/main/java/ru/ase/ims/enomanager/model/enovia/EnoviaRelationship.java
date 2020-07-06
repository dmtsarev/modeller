package ru.ase.ims.enomanager.model.enovia;

import lombok.extern.slf4j.Slf4j;

import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.model.graph.GraphVertexDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "ematrix")
@Slf4j
public class EnoviaRelationship extends Ematrix implements DataModel {

    public static final String OBJECT_TYPE = "relationship";
    public EnoviaRelationship(Ematrix ematrix) {
        super(ematrix);
    }

    public EnoviaRelationship() {
    }
    @Override
    public String getName() {
        return getRelationshipDef().getAdminProperties().getName();
    }

    @Override
    public String getModelType() {
        return OBJECT_TYPE;
    }

    @Override
    public List<GraphVertexDTO> getSourceList() {
        if (this.getRelationshipDef().getFromSide().getTypeRefList() !=  null) {
            return this.getRelationshipDef().getFromSide().getTypeRefList().getTypeRef().stream().map(item -> new GraphVertexDTO(item, EnoviaType.OBJECT_TYPE)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GraphVertexDTO> getTargetList() {
        if (this.getRelationshipDef().getToSide().getTypeRefList() !=  null) {
            return this.getRelationshipDef().getToSide().getTypeRefList().getTypeRef().stream().map(item -> new GraphVertexDTO(item, EnoviaType.OBJECT_TYPE)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
