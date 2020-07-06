package ru.ase.ims.enomanager.model.enovia;

import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.model.graph.GraphVertexDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "ematrix")
public class EnoviaInterface extends Ematrix implements DataModel {
    public static final String OBJECT_TYPE = "interface";
    public EnoviaInterface(Ematrix ematrix) {
        super(ematrix);
    }
    public EnoviaInterface() {}
    @Override
    public String getModelType() {return OBJECT_TYPE;}

    @Override
    public List<GraphVertexDTO> getSourceList() {
        if (this.getInterfaceType().getDerivedFromInterface() != null) {
            return this.getInterfaceType().getDerivedFromInterface().getInterfaceTypeRefList().getInterfaces()
                    .stream().map(item -> new GraphVertexDTO(item, EnoviaInterface.OBJECT_TYPE))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<GraphVertexDTO> getTargetList() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return getInterfaceType().getAdminProperties().getName();
    }
}
