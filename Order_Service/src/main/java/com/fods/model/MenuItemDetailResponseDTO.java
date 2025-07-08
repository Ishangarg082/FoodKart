package com.fods.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MenuItemDetailResponseDTO<T> {
    private Long menuId;
    private T data;

    @Override
    public String toString() {
        return "MenuItemDetailResponseDTO{" +
                "menuId=" + menuId +
                ", data=" + data +
                '}';
    }
}
