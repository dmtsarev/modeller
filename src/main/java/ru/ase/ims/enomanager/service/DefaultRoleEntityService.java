package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.enovia.xml.ParentRole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DefaultRoleEntityService implements RoleEntityService {

    private final DefaultEntityService defaultEntityService;

    public DefaultRoleEntityService(DefaultEntityService defaultEntityService) {
        this.defaultEntityService = defaultEntityService;
    }

    @Override
    public List<EnoviaEntity> getRoleEntityTree(Long releaseId, String name, String searchWord) {
        List<EnoviaEntity> roleEntities = defaultEntityService.getEntityList(releaseId, "role", searchWord);

        roleEntities.forEach(item -> item.setChild(new ArrayList<>()));

        for (Iterator<EnoviaEntity> it = roleEntities.iterator(); it.hasNext(); ) {
            EnoviaEntity role = it.next();
            ParentRole parentRole = role.getEmatrix().getRole().getParentRole();

            if (parentRole != null) {
                boolean block = false;
                List<String> roleRefItemNameList = parentRole.getRoleRef();

                for (String roleRefItemName : roleRefItemNameList) {
                    EnoviaEntity child = findRoleChildByName(roleEntities, roleRefItemName);

                    if (child != null) {
                        child.addChild(role);
                        if (!block) {
                            it.remove();
                            block = true;
                        }
                    }
                }
            }
        }

        if (name.equals("role")) {
            return roleEntities;
        }

        EnoviaEntity child = findRoleChildByName(roleEntities, name);
        if (child != null) {
            return child.getChild();
        } else {
            return new ArrayList<>();
        }
    }


    public EnoviaEntity findRoleChildByName(List<EnoviaEntity> enoviaEntity, String name) {
        for (EnoviaEntity each : enoviaEntity) {
            if (each.getEmatrix().getRole().getAdminProperties().getName().equals(name)) {
                return each;
            } else {
                EnoviaEntity found = findRoleChildByName(each.getChild(), name);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }
}
