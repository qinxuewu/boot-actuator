package com.pflm.common.utils;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.pflm.common.xss.SQLFilter;


/***
* @Description:    查询参数
* @Author:         qinxuewu
* @CreateDate:     26/11/2018 下午 3:29
* @Email 870439570@qq.com
**/
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");
        if (StringUtils.isNotBlank(sidx)) {
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if (StringUtils.isNotBlank(order)) {
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
