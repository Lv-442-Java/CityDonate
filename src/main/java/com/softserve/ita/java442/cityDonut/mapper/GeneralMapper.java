package com.softserve.ita.java442.cityDonut.mapper;

public interface GeneralMapper<M,D> {

    public D convertToDto(M model);

    public M convertToModel(D dto);
}
