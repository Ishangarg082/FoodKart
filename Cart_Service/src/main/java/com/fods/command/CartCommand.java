package com.fods.command;

import com.fods.model.CartItemResponseDTO;

public interface CartCommand {
    CartItemResponseDTO execute();
}
