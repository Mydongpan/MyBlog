package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Tag;
import com.manong.domain.service.TagService;
import com.manong.domain.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取标签列表
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagVo tagVo){

        return tagService.getTagList(pageNum,pageSize,tagVo);
    }

    /**
     * 新增用户
     * @param tag
     * @return
     */
    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag){

        return tagService.saveTag(tag);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 根据id修改标签
     * @param tag
     * @return
     */
    @PutMapping
    public ResponseResult updateTag(@RequestBody Tag tag){
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }
}
