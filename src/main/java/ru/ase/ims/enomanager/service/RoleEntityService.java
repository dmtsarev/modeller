package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.enovia.xml.ParentRole;
import ru.ase.ims.enomanager.service.xml.Node;

import java.util.List;
import java.util.Objects;

@Service
public class RoleEntityService {
    private final DefaultEntityService defaultEntityService;

    public RoleEntityService(DefaultEntityService defaultEntityService) {
        this.defaultEntityService = defaultEntityService;
    }

    public Node<String> getRoleEntityTree(Long releaseId, String searchWord) {

        List<EnoviaEntity> roleEntities = defaultEntityService.getEntityList(releaseId, "role", searchWord);
        Node<String> roleRefsTreeRoot = new Node<String>("root");
        roleEntities.forEach(role -> {
            String roleName = role.getEmatrix().getRole().getAdminProperties().getName();
            ParentRole parentRole = role.getEmatrix().getRole().getParentRole();

            if (parentRole != null) {
                List<String> roleRefName = parentRole.getRoleRef();
//                System.out.println(role.getEmatrix().getRole().getParentRole().getRoleRef());

                roleRefName.forEach(roleRefItemName -> {
                    Node<String> child = roleRefsTreeRoot.findChildByName(roleRefItemName);
                    Objects.requireNonNullElse(child, roleRefsTreeRoot).addChild(new Node<String>(roleName));
                });
            } else {
                roleRefsTreeRoot.addChild(new Node<String>(roleName));
            }
        });

        return roleRefsTreeRoot;
    }
}
