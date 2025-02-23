package hu.jakab.ekkeencoprosampbackend.controller.base;

import java.util.Optional;

public interface UpdateController<REQ, RES, ID> {
    Optional<RES> updateEntity(ID id, REQ dto);
}
