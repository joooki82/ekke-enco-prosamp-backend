package hu.jakab.ekkeencoprosampbackend.controller.base;

import java.util.List;
import java.util.Optional;

public interface ReadController<RES, ID> {
    List<RES> getAllEntities();
    Optional<RES> getEntityById(ID id);
}
