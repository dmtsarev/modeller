package ru.ase.ims.enomanager.model.enovia;

import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.model.graph.GraphVertexDTO;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name ="ematrix")
public class EnoviaPolicy extends Ematrix implements DataModel   {

    public static final String OBJECT_TYPE = "policy";

    public EnoviaPolicy() {}

    public EnoviaPolicy(Ematrix ematrix) {super(ematrix);}

    @Override
    public String getName() { return getPolicy().getAdminProperties().getName(); }

    @Override
    public String getModelType() {return OBJECT_TYPE;}

    @Override
    public List<GraphVertexDTO> getSourceList() {
        return new ArrayList<>();
    }
    @Override
    public List<GraphVertexDTO> getTargetList() {
        return new ArrayList<>();
    }
}
