package com.roche.ddd.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roche.ddd.domain.Product;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DeleteProductRequest {

    @NotNull
    private final Product product;

    @JsonCreator
    public DeleteProductRequest(@NotNull final Product product) {
        this.product = product;
    }

}
