package hu.jakab.ekkeencoprosampbackend.controller.base;

public interface CreateController<REQ, RES> {
    RES createEntity(REQ dto);
}
