package com.polycis.main.entity.script;

import com.baomidou.mybatisplus.annotations.TableName;
import com.polycis.main.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     js脚本entity
 * </p>
 *
 * @author cg
 * @since 2019-06-19
 */
@Data
@TableName("product_rel_script")
public class ProductRelScriptBO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 脚本内容
     */
    private String scriptContent;

    /**
     * 脚本草稿
     */
    private String scriptDraft;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getScriptDraft() {
        return scriptDraft;
    }

    public void setScriptDraft(String scriptDraft) {
        this.scriptDraft = scriptDraft;
    }



}
