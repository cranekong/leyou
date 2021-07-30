package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("/brand/page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "desc", required = false) Boolean desc) {
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @RequestMapping("/brand")
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {

        Brand brand1 = this.brandService.queryBrandsById(brand.getId());
        //brand不为空，修改
        if (null != brand1) {
            this.brandService.updateBrand(brand, cids);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {//新增
            this.brandService.saveBrand(brand, cids);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
}
