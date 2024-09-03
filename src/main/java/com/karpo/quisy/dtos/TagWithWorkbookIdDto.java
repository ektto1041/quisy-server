package com.karpo.quisy.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagWithWorkbookIdDto {
    private Long workbookId;
    private String tag;
}
