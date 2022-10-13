package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Comment;
import com.manong.domain.entity.User;
import com.manong.domain.enums.AppHttpCodeEnum;
import com.manong.domain.exception.SystemException;
import com.manong.domain.mapper.CommentMapper;
import com.manong.domain.service.CommentService;
import com.manong.domain.service.UserService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.vo.CommentVo;
import com.manong.domain.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    /**
     * 查询没有子评论的评论
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult getCommentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {

        //进行分页查询
        Page<Comment> pageInfo = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemContants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

        //判断是否根评论
        queryWrapper.eq(Comment::getRootId, SystemContants.COMMENT_ROOT_ID);
        //评论类别
        queryWrapper.eq(Comment::getType,commentType);

        Page<Comment> page = page(pageInfo, queryWrapper);
        //得到comment集合
        List<Comment> commentList = page.getRecords();
        List<CommentVo> commentVos = toCommentVoList(commentList);
        //查询子评论
        commentVos = commentVos.stream().map((item) ->{
            item.setChildren(getChildren(item.getId()));
            return item;
        }).collect(Collectors.toList());

        return ResponseResult.okResult(new PageVo(commentVos,page.getTotal()));
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.COMMENT_NOT_NULL);
        }
        save(comment);

        return ResponseResult.okResult();
    }

    /**
     * 将commentList转为CommentVoList
     * @param list
     * @return
     */
    public List<CommentVo> toCommentVoList(List<Comment> list){
        //进行复制
        List<CommentVo> commentVoList = BeanCopyUtil.copyBeanList(list, CommentVo.class);
        commentVoList = commentVoList.stream().map((item) ->{
            //通过createBy查询username
            Long createBy = item.getCreateBy();
            String nickName = userService.getById(createBy).getNickName();
            item.setUsername(nickName);
            //通过toCommentUserId查询用户id，如果toCommentUserId不为-1
            if (item.getToCommentUserId() != -1){
                String toCommentName = userService.getById(item.getToCommentUserId()).getNickName();
                item.setToCommentUserName(toCommentName);
            }
            return item;
        }).collect(Collectors.toList());
        return commentVoList;
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id
     * @return
     */
    public List<CommentVo> getChildren(Long id){

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List commentList = list(queryWrapper);
        List<CommentVo> commentVoList = BeanCopyUtil.copyBeanList(commentList, CommentVo.class);
        return commentVoList;
    }
}
