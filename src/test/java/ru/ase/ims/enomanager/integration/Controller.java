package ru.ase.ims.enomanager.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.controller.v1.*;

@Component
public class Controller {

    @Autowired
    protected DefaultEntityController defaultEntityController;

    @Autowired
    protected DefaultGraphController defaultGraphController;

    @Autowired
    protected DefaultReleaseController defaultReleaseController;

    @Autowired
    protected UserController userController;

    @Autowired
    protected TagsController tagsController;

    @Autowired
    protected DefaultProjectController defaultProjectController;
}
