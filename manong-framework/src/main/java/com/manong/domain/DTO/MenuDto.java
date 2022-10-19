package com.manong.domain.DTO;

import com.manong.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto extends Menu {

    private List<MenuDto> children;
}
