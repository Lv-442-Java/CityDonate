package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.model.Comment;

public interface GeneralMapper<M, D> {

     D convertToDto(M model)  ;

     M convertToModel(D dto) ;
}
