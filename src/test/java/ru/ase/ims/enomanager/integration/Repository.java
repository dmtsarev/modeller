package ru.ase.ims.enomanager.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.repository.*;

@Component
public class Repository {

    @Autowired
    protected EntityRepository entityRepository;

    @Autowired
    protected ReleaseRepository releaseRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected TagsRepository tagsRepository;

    @Autowired
    protected TagTypeRepository tagTypeRepository;
}
