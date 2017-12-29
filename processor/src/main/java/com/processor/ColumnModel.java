package com.processor;


import com.models.Column;

import javax.lang.model.element.VariableElement;

/**
 * Created by kjh 2017/12/29
 */
public class ColumnModel {
    private String fieldName;
    private String name;

    public ColumnModel(VariableElement variableElement) {
        Column column = variableElement.getAnnotation(Column.class);
        name = column.name();
        fieldName = variableElement.getSimpleName().toString();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getName() {
        return name;
    }
}
