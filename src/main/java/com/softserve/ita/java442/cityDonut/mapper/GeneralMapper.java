package com.softserve.ita.java442.cityDonut.mapper;

public interface GeneralMapper<M, D> {

    D convertToDto(M model);

    M convertToModel(D dto);
}
