package com.soft2242.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soft2242.shop.convert.GoodsConvert;
import com.soft2242.shop.entity.Category;
import com.soft2242.shop.entity.Goods;
import com.soft2242.shop.enums.CategoryRecommendEnum;
import com.soft2242.shop.mapper.CategoryMapper;
import com.soft2242.shop.mapper.GoodsMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import com.soft2242.shop.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.shop.vo.CategoryChildrenGoodsVO;
import com.soft2242.shop.vo.CategoryVO;
import com.soft2242.shop.vo.RecommendGoodsVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@Service
//@ComponentScan
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Category> getIndexCategoryList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
//        查询首页和分类页都推荐的分类以及在首页推荐的分类
        wrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND.getValue()).or().eq(Category::getIsRecommend, CategoryRecommendEnum.INDEX_RECOMMEND.getValue());
        wrapper.orderByDesc(Category::getCreateTime);
        List<Category> list = baseMapper.selectList(wrapper);
        return list;
    }

//    @Override
//    public List<CategoryVO> getCategoryList() {
//        List<CategoryVO> list = new ArrayList<>();
////        1、查询配置在分类tab页上的 父级分类
//        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Category::getIsRecommend, com.soft2242.shop.enums.CategoryRecommendEnum.ALL_RECOMMEND.getValue()).or().eq(Category::getIsRecommend, com.soft2242.shop.enums.CategoryRecommendEnum.CATEGORY_HOME_RECOMMEND.getValue());
//        List<Category> categories = baseMapper.selectList(wrapper);
////        2、查询该分类下的二级分类
//        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
//        for (Category category : categories) {
//            CategoryVO categoryVO = new CategoryVO();
//            categoryVO.setId(category.getId());
//            categoryVO.setName(category.getName());
//            categoryVO.setIcon(category.getIcon());
//            wrapper.clear();
//            wrapper.eq(Category::getParentId, category.getId());
//            List<Category> childCategories = baseMapper.selectList(wrapper);
//            List<CategoryChildrenGoodsVO> categoryChildrenGoodsList = new ArrayList<>();
////            3、分类下的商品列表
//            for (Category item : childCategories) {
//                CategoryChildrenGoodsVO childrenGoodsVO = new CategoryChildrenGoodsVO();
//                childrenGoodsVO.setId(item.getId());
//                childrenGoodsVO.setName(item.getName());
//                childrenGoodsVO.setIcon(item.getIcon());
//                childrenGoodsVO.setParentId(category.getId());
//                childrenGoodsVO.setParentName(category.getName());
//                queryWrapper.clear();
//                List<Goods> goodsList = goodsMapper.selectList(queryWrapper.eq(Goods::getCategoryId, item.getId()));
//                List<RecommendGoodsVO> goodsVOList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsList);
//                childrenGoodsVO.setGoods(goodsVOList);
//                categoryChildrenGoodsList.add(childrenGoodsVO);
//
//            }
//            categoryVO.setChildren(categoryChildrenGoodsList);
//            list.add(categoryVO);
//        }
//
//        return list;
//    }


    @Override
    public List<CategoryVO> getCategoryList() {
        ArrayList<CategoryVO> list = new ArrayList<>();
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND.getValue()).or();
        List<Category> categories = baseMapper.selectList(wrapper);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        for (Category category : categories) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setId(category.getId());
            categoryVO.setName(category.getName());
            categoryVO.setIcon(category.getIcon());
            wrapper.clear();
            wrapper.eq(Category::getParentId, category.getId());
            List<Category> childCategories = baseMapper.selectList(wrapper);
            ArrayList<CategoryChildrenGoodsVO> categoryChildrenGoodsList = new ArrayList<>();
            for (Category item : childCategories) {
                CategoryChildrenGoodsVO childrenGoodsVO = new CategoryChildrenGoodsVO();
                childrenGoodsVO.setId(item.getId());
                childrenGoodsVO.setName(item.getName());
                childrenGoodsVO.setIcon(item.getIcon());
                childrenGoodsVO.setParentId(category.getId());
                childrenGoodsVO.setParentName(category.getName());
                queryWrapper.clear();
                List<Goods> goodsList = goodsMapper.selectList(queryWrapper.eq(Goods::getCategoryId, item.getId()));
                List<RecommendGoodsVO> goodsVOList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsList);
                childrenGoodsVO.setGoods(goodsVOList);
                categoryChildrenGoodsList.add(childrenGoodsVO);
            }
            categoryVO.setChildren(categoryChildrenGoodsList);
            list.add(categoryVO);
        }
        return list;
    }

}
