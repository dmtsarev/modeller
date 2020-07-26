package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.enovia.xml.ParentRole;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultRoleEntityService implements RoleEntityService {

    private final DefaultEntityService defaultEntityService;

    public DefaultRoleEntityService(DefaultEntityService defaultEntityService) {
        this.defaultEntityService = defaultEntityService;
    }

    @Override
    public List<EnoviaEntity> getRoleEntityTree(Long releaseId, String searchWord) {

//        List<EnoviaEntity> roleEntities = defaultEntityService.getEntityList(releaseId, "role", searchWord);
//        TreeNode<EnoviaEntity> roleRefsTreeRoot = new TreeNode<EnoviaEntity>(new EnoviaEntity("root", "role", "root", null));
//
//        for (int i = 0; i < maxTreeDeepness; i++) {
//            List<EnoviaEntity> tmpToRemove = new ArrayList<>();
//            roleEntities.forEach(role -> {
//                ParentRole parentRole = role.getEmatrix().getRole().getParentRole();
//
//                if (parentRole != null) {
//                    List<String> roleRefNameList = parentRole.getRoleRef();
//                    roleRefNameList.forEach(roleRefItemName -> {
//                        EnoviaEntity child = findRoleChildByName(roleRefsTreeRoot, roleRefItemName);
//                        Objects.requireNonNullElse(child, roleRefsTreeRoot).addChild(new TreeNode<EnoviaEntity>(role));
//                    });
//                } else {
//                    roleRefsTreeRoot.addChild(new TreeNode<EnoviaEntity>(role));
//                }
//
//                tmpToRemove.add(role);
//            });
//
//            roleEntities.removeAll(tmpToRemove);
//        }
//
//        return roleRefsTreeRoot.getChild();

        List<EnoviaEntity> roleEntities = defaultEntityService.getEntityList(releaseId, "role", searchWord);
        EnoviaEntity newRoleEntitiesRoot = new EnoviaEntity("roleRoot", new Release(releaseId, "", "", "", "", new Project()));
        newRoleEntitiesRoot.addChildren(roleEntities);

        roleEntities.forEach(role -> {
            ParentRole parentRole = role.getEmatrix().getRole().getParentRole();

            if (parentRole != null) {
                List<String> roleRefNameList = parentRole.getRoleRef();
                roleRefNameList.forEach(roleRefItemName -> {
                    EnoviaEntity child = findRoleChildByName(newRoleEntitiesRoot, roleRefItemName);
                    if (child != null) {
                        child.addChild(role);
                        newRoleEntitiesRoot.getChild().remove(role);
                    }
//                    Objects.requireNonNullElse(child, newRoleEntitiesRoot).addChild(role);
                });
            }
        });

        return newRoleEntitiesRoot.getChild();
    }

    public EnoviaEntity findRoleChildByName(EnoviaEntity enoviaEntityy, String name) {
        List<EnoviaEntity> childList = enoviaEntityy.getChild();
        for (EnoviaEntity each : childList) {
            if (each.getEmatrix().getRole().getAdminProperties().getName().equals(name)) {
                return each;
            } else {
                findRoleChildByName(each, name);
            }
        }

        return null;
    }
}
