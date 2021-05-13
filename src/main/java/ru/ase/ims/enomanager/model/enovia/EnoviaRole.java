package ru.ase.ims.enomanager.model.enovia;

import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.model.graph.GraphVertexDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ematrix")
public class EnoviaRole extends Ematrix implements DataModel {
    public static final String OBJECT_TYPE = "role";

    public EnoviaRole() {}
    public EnoviaRole(Ematrix ematrix) {
        super(ematrix);
    }

    @Override
    public String getModelType() {
        return OBJECT_TYPE;
    }

    @Override
    public List<GraphVertexDTO> getSourceList() {
        return new ArrayList<>();
    }

    @Override
    public List<GraphVertexDTO> getTargetList() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return getRole().getAdminProperties().getName();
    }
}
