package com.personal.model;

import com.personal.exception.ObjectValidationException;

public interface Validatable {
    void validate() throws ObjectValidationException;
}
