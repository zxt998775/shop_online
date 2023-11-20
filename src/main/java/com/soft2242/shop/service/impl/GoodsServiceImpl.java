package com.soft2242.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft2242.shop.common.exception.ServerException;

import com.soft2242.shop.common.result.PageResult;
import com.soft2242.shop.convert.GoodsConvert;

import com.soft2242.shop.entity.*;
import com.soft2242.shop.mapper.*;
import com.soft2242.shop.query.Query;
import com.soft2242.shop.query.RecommendByTabGoodsQuery;
import com.soft2242.shop.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.shop.vo.*;
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
@AllArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    private final IndexRecommendMapper indexRecommendMapper;
    private final IndexRecommendTabMapper indexRecommendTabMapper;
    private final GoodsDetailMapper goodsDetailMapper;
    private final GoodsSpecificationMapper goodsSpecificationMapper;
    private final GoodsSpecificationDetailMapper goodsSpecificationDetailMapper;

    @Override
    public IndexTabRecommendVO getTabRecommendGoodsByTabId(RecommendByTabGoodsQuery query) {
//        1、根据推荐的recommendId 查询实体
        IndexRecommend indexRecommend = indexRecommendMapper.selectById(query.getSubType());
        if (indexRecommend == null) {
            throw new ServerException("推荐分类不存在");
        }
//        2、查询该分类下的tab 列表
        LambdaQueryWrapper<IndexRecommendTab> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IndexRecommendTab::getRecommendId, indexRecommend.getId());
        List<IndexRecommendTab> tabList = indexRecommendTabMapper.selectList(wrapper);
        if (tabList.size() == 0) {
            throw new ServerException("该分类下不存在 tab 分类");
        }

//        3、tab分类下的商品列表
        List<IndexTabGoodsVO> list = new ArrayList<>();
        for (IndexRecommendTab item : tabList) {
            IndexTabGoodsVO tabGoods = new IndexTabGoodsVO();
            tabGoods.setId(item.getId());
            tabGoods.setName(item.getName());
            Page<Goods> page = new Page<>(query.getPage(), query.getPageSize());
            Page<Goods> goodsPage = baseMapper.selectPage(page, new LambdaQueryWrapper<Goods>().eq(Goods::getTabId, item.getId()));
            List<RecommendGoodsVO> goodsList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsPage.getRecords());
            PageResult<RecommendGoodsVO> result = new PageResult<>(page.getTotal(), query.getPageSize(), query.getPage(), page.getPages(), goodsList);
            tabGoods.setGoodsItems(result);
            list.add(tabGoods);
        }

        IndexTabRecommendVO recommendVO = new IndexTabRecommendVO();
        recommendVO.setId(indexRecommend.getId());
        recommendVO.setName(indexRecommend.getName());
        recommendVO.setCover(indexRecommend.getCover());
        recommendVO.setSubTypes(list);


        return recommendVO;
    }

    @Override
    public PageResult<RecommendGoodsVO> getRecommendGoodsByPage(Query query) {
        Page<Goods> page = new Page<>();
        Page<Goods> goodsPage = baseMapper.selectPage(page, null);
        List<RecommendGoodsVO> result = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsPage.getRecords());
        return new PageResult<>(page.getTotal(), query.getPageSize(), query.getPage(), page.getPages(), result);
    }

    @Override
    public GoodsVO getGoodsDetail(Integer id) {
        //     根据id 获取商品详情
        Goods goods = baseMapper.selectById(id);
        if (goods == null) {
            throw new ServerException("商品不存在");
        }
//        System.out.println("goods = " + goods.toString());
//        System.out.println("goods = " + goods.toString());
//        System.out.println("goods = " + goods);
        GoodsVO goodsVO = GoodsConvert.INSTANCE.convertToGoodsVO(goods);
        //        商品规格
        List<GoodsDetail> goodsDetails = goodsDetailMapper.selectList(new LambdaQueryWrapper<GoodsDetail>().eq(GoodsDetail::getGoodsId, goods.getId()));
        goodsVO.setProperties(goodsDetails);
        /**
         *    手动转换 json格式
         *    可以用mymbatis handle来转换,这边为了偷懒,手动转换了.....
         */

        ObjectMapper objectMapper = new ObjectMapper();
        //      商品可选规格集合
        List<GoodsSpecification> specificationList = goodsSpecificationMapper.selectList(new LambdaQueryWrapper<GoodsSpecification>().eq(GoodsSpecification::getGoodsId, goods.getId()));
        List<GoodsSpecificationVO> specificationListVO = new ArrayList<>();
        specificationList.forEach(s ->{
            List<GoodsValueVO> goodsVOs = new ArrayList<>();
            try {
                goodsVOs  =objectMapper.readValue(s.getValue(), new TypeReference<List<GoodsValueVO>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            GoodsSpecificationVO build = GoodsSpecificationVO.builder()
                    .id(s.getId())
                    .goodsId(s.getGoodsId())
                    .name(s.getName())
                    .value(goodsVOs)
                    .deleteFlag(s.getDeleteFlag())
                    .createTime(s.getCreateTime())
                    .updateTime(s.getUpdateTime())
                    .build();
            specificationListVO.add(build);
        });
//        goodsVO.setSpecs(specificationList);
//        替换掉
        goodsVO.setSpecs(specificationListVO);

          /**
         *    手动转换 json格式
         *    可以用mymbatis handle来转换,这边为了偷懒,手动转换了.....
         */
        //      商品规格详情
        List<GoodsSpecificationDetail> goodsSpecificationDetails = goodsSpecificationDetailMapper.selectList(new LambdaQueryWrapper<GoodsSpecificationDetail>().eq(GoodsSpecificationDetail::getGoodsId, goods.getId()));

        List<GoodsSpecificationDetailVO> vos = new ArrayList<>();

        goodsSpecificationDetails.forEach(g -> {
            List<SpecsVO> specsVOS = new ArrayList<>();
            try {
                specsVOS  =objectMapper.readValue(g.getSpecs(), new TypeReference<List<SpecsVO>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            GoodsSpecificationDetailVO build = GoodsSpecificationDetailVO.builder()
                    .cover(g.getCover())
                    .createTime(g.getCreateTime())
                    .deleteFlag(g.getDeleteFlag())
                    .goodsId(g.getGoodsId())
                    .id(g.getId())
                    .inventory(g.getInventory())
                    .oldPrice(g.getOldPrice())
                    .specs(specsVOS)
                    .price(g.getPrice())
                    .updateTime(g.getUpdateTime())
                    .build();

            vos.add(build);
        });
//        goodsVO.setSkus(goodsSpecificationDetails);
//        替换掉如下
        goodsVO.setSkus(vos);
        //      查找同类商品,去除自身
        List<Goods> goodsList = baseMapper.selectList(new LambdaQueryWrapper<Goods>().eq(Goods::getCategoryId, goods.getCategoryId()).ne(Goods::getId, goods.getId()));
        List<RecommendGoodsVO> goodsVOList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsList);
        goodsVO.setSimilarProducts(goodsVOList);
        return goodsVO;
    }
}