package com.jeshco.backendspringboot.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskSearchValues {

    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;

}
