package com.txhl.wxorder.controller;

import com.txhl.wxorder.constants.Constants;
import com.txhl.wxorder.entity.ProductCategory;
import com.txhl.wxorder.exception.WXOrderException;
import com.txhl.wxorder.form.CategoryForm;
import com.txhl.wxorder.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * class_name: SellerCategoryController
 * package: com.txhl.wxorder.controller
 * describe: 卖家类目
 * creat_user: sl
 * creat_date: 2018/4/26
 * creat_time: 21:18
 **/
@Slf4j
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * @param:
     * describe: 类目列表
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 21:32
     **/
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = productCategoryService.findALL();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * @param: categoryId
     * describe: 类目详情页
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 21:32
     **/
    @GetMapping("/categoryDetail")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = productCategoryService.findOne(categoryId);
            map.put("category", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    /**
     * @param: categoryForm
     * describe: 保存/更新
     * creat_user: sl
     * creat_date: 2018/4/26
     * creat_time: 21:31
     **/
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【类目详情】 异常原因: {}",bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", Constants.CATEGORY_REDIRECT_URI);
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                productCategory = productCategoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            productCategoryService.save(productCategory);
        } catch (WXOrderException e) {
            map.put("msg", e.getMessage());
            map.put("url", Constants.CATEGORY_REDIRECT_URI);
            return new ModelAndView("common/error", map);
        }

        map.put("url", Constants.CATEGORY_LIST_REDIRECT_URI);
        return new ModelAndView("common/success", map);
    }
}
