package com.fods.restaurant.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MenuItemDetailResponseDTO<T> {
    private Long menuId;
    private T data;

}
