package com.adms.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyz
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JobProject implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * key
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * project name
     */
    private String name;

    private String description;

    /**
     * creator id
     */
    private Integer userId;

    /**
     * 0 not available, 1 available
     */
    private Integer flag;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;


}
