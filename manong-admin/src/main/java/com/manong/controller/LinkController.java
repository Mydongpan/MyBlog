package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Link;
import com.manong.domain.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 分页查询友链列表
     * @param link
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ResponseResult page(Link link,Integer pageNum,Integer pageSize){

        return linkService.pageLink(link,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){

        linkService.save(link);
        return ResponseResult.okResult();
    }

    /**
     * 回显link详细信息
     * @param id
     * @return
     */
    @GetMapping("/{linkId}")
    public ResponseResult selectByLinkId(@PathVariable Long id){

        Link link = linkService.getBaseMapper().selectById(id);
        return ResponseResult.okResult(link);
    }

    /**
     * 将修改的信息添加到数据库
     * @param link
     * @return
     */
    @PutMapping
    public ResponseResult eidt(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{linkId}")
    public ResponseResult delete(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
