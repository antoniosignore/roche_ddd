package com.roche.ddd.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roche.ddd.domain.Product;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AddProductRequest {

    @NotNull
    private final Product product;

    @JsonCreator
    public AddProductRequest(@NotNull final Product product) {
        this.product = product;
    }

}
