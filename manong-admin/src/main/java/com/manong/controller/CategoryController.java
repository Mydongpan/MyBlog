package com.manong.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Category;
import com.manong.domain.enums.AppHttpCodeEnum;
import com.manong.domain.service.CategoryService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.WebUtils;
import com.manong.domain.vo.ExcelCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){

        return categoryService.getAllCategory();

    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response){

        try {
            //设置下载的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取要导出的数据
            List<Category> list = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtil.copyBeanList(list, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(),ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE).sheet("分类导出").doWrite(excelCategoryVos);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(responseResult));
        }
    }

    /**
     * 分页查询分类列表
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public ResponseResult page(Category category,Integer pageNum,Integer pageSize){

        return categoryService.categoryPage(category,pageNum,pageSize);

    }

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category){

        categoryService.save(category);
        return ResponseResult.okResult();
    }

    /**
     * 根据分类di查询分类信息
     * @param id
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseResult selectByCategoryId(@PathVariable Long id){

        return categoryService.selectByCategoryId(id);
    }

    /**
     * 将修改后的数据保存到数据库中
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult eidt(@RequestBody Category category){

        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    /**
     * 根据分类id移除指定分类
     * @param id
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteById(@PathVariable Long id){

        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
}