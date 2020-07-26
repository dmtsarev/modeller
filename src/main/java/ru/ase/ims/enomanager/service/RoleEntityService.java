package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.EnoviaEntity;

import java.util.List;

public interface RoleEntityService {
    List<EnoviaEntity> getRoleEntityTree(Long releaseId, String searchWord);
}
