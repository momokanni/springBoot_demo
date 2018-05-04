package com.txhl.wxorder.dao;

import com.txhl.wxorder.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String>{

    List<ProductInfo> findProductByProductStatus (Integer productStatus);
}
