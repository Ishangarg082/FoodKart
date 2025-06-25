package com.fods.command;

import com.fods.model.CartItemRequestDTO;
import com.fods.model.CartItemResponseDTO;

public class AddItemCommand implements CartCommand{

    private final CartItemRequestDTO request;

    public AddItemCommand(CartItemRequestDTO request) {
        this.request = request;
    }

    @Override
    public CartItemResponseDTO execute() {
        return null;
    }
}
