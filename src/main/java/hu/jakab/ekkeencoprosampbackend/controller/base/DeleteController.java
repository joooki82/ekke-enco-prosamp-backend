package hu.jakab.ekkeencoprosampbackend.controller.base;

public interface DeleteController<ID> {
    boolean deleteEntity(ID id);
}
