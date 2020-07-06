package ru.ase.ims.enomanager.model.enovia;

import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.model.graph.GraphVertexDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "ematrix")
public class EnoviaType extends Ematrix implements DataModel {


    public static final String OBJECT_TYPE = "type";
    public EnoviaType() {}
    public EnoviaType(Ematrix ematrix) {
        super(ematrix);
    }
    @Override
    public String getName() {
        return getType().getAdminProperties().getName();
    }
    @Override
    public String getModelType() {
        return OBJECT_TYPE;
    }

    @Override
    public List<GraphVertexDTO> getSourceList() {
        if (this.getType().getDerivedFrom() != null) {
            return this.getType().getDerivedFrom().getTypeRefList().getTypeRef().stream().map(item -> new GraphVertexDTO(item, EnoviaType.OBJECT_TYPE)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GraphVertexDTO> getTargetList() {
        List items = new ArrayList<>();
        if (this.getType().getAttributeList() != null) {
            items.addAll(this.getType().getAttributeList().getAttributes().stream().map(item -> new GraphVertexDTO(item, EnoviaAttribute.OBJECT_TYPE)).collect(Collectors.toList()));
        }
        return items;
    }
}
