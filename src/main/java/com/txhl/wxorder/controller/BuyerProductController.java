package com.txhl.wxorder.controller;

import com.txhl.wxorder.entity.ProductCategory;
import com.txhl.wxorder.entity.ProductInfo;
import com.txhl.wxorder.service.ProductCategoryService;
import com.txhl.wxorder.service.ProductInfoService;
import com.txhl.wxorder.util.ResultUtils;
import com.txhl.wxorder.vo.CategoryAndProductVO;
import com.txhl.wxorder.vo.ProductInfoVO;
import com.txhl.wxorder.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 *
 * @author Administrator
 * @create 2018-04-01 20:29
 */
@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService pis;

    @Autowired
    private ProductCategoryService pc;

    /**
     * 类目+商品列表
     * param:
     * describe:
     * creat_user:
     * creat_date: 2018/4/4
     * creat_time: 14:45
     **/
    @GetMapping(value = "list")
    //@Cacheable(cacheNames = "product",key = "123" )
    //@Cacheable(cacheNames = "product",key = "#XXX" ,condition = "#XXX.length() > 3",unless = "#result.getCode() != 200")
    public ResultVO list(){
        //查询所有上架商品
        List<ProductInfo> onLine = pis.findOnLine();

        //查询类目（一次性查询）
        //精简特性（Java8）
        List<Integer> categoryList = onLine.stream()
                                    .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categorys = pc.findByCategoryTypeIn(categoryList);
        List<CategoryAndProductVO> cvoList = new ArrayList<CategoryAndProductVO>();
        //数据拼装
        for (ProductCategory pc : categorys) {
            CategoryAndProductVO categoryAndProductVO = new CategoryAndProductVO();
            categoryAndProductVO.setCategoryName(pc.getCategoryName());
            categoryAndProductVO.setCategoryType(pc.getCategoryType());
            List<ProductInfoVO> piList = new ArrayList<ProductInfoVO>();
            for (ProductInfo pi:onLine) {
                if(pi.getCategoryType().equals(pc.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(pi,productInfoVO);
                    piList.add(productInfoVO);
                }
            }
            categoryAndProductVO.setProductInfoVOList(piList);
            cvoList.add(categoryAndProductVO);
        }
        return ResultUtils.success(cvoList);
    }
}
