package com.chechetimes.wzl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatisplus
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("table_type")
public class TableType extends Model<TableType> {

    private static final long serialVersionUID = 1L;

    private String type;

    private String org;

    private String name;

    private String responsible;

    private Integer flag;


    @Override
    protected Serializable pkVal() {
        return this.type;
    }

}
