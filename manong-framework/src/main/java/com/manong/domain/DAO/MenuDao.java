package com.manong.domain.DAO;

import com.manong.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDao extends Menu {

    private List<MenuDao> children;
}
