package com.soft2242.shop.convert;

import com.soft2242.shop.entity.IndexRecommend;
import com.soft2242.shop.vo.IndexRecommendVO;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface IndexRecommendConvert {
    IndexRecommendConvert INSTANCE = Mappers.getMapper(IndexRecommendConvert.class);

    @Mapping(expression = "java(MapStruct.strToList(indexRecommend.getPictures()))", target = "pictures")
    IndexRecommendVO convertToIndexRecommendVo(IndexRecommend indexRecommend);

    List<IndexRecommendVO> convertToUserVoList(List<IndexRecommend> list);

    class MapStruct {
        public static List<String> strToList(String str) {
            if (StringUtils.isNotEmpty(str)) {
                return Arrays.asList(str.split(","));
            }
            return null;
        }
    }
}

