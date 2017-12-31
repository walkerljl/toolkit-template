package org.walkerljl.toolkit.template;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.template.compute.RecursiveQueryTemplate;

/**
 *
 * @author xingxun
 */
public class RecursiveQueryTemplateTest {

    @Test
    public void test() {
        PagingQueryParam param = new PagingQueryParam();
        param.setCurrentPage(1);
        param.setPageSize(2);
        param.setNeedQueryTotalCount(6);
        List<PagingQueryResult> resultList = new RecursiveQueryTemplate<PagingQueryParam, PagingQueryResult>() {
            @Override
            public boolean isContinue(PagingQueryParam pagingQueryParam) {
                boolean isContinue = (pagingQueryParam.getCurrentPage() * pagingQueryParam.getPageSize() <= pagingQueryParam.getNeedQueryTotalCount());
                return isContinue;
            }

            @Override
            public List<PagingQueryResult> query0(PagingQueryParam pagingQueryParam) {
                List<PagingQueryResult> partResultList = new ArrayList<>(pagingQueryParam.getPageSize());
                for (int i = ((pagingQueryParam.getCurrentPage() - 1) * pagingQueryParam.getPageSize());
                     i < (pagingQueryParam.getCurrentPage() * pagingQueryParam.getPageSize()); i++) {
                    PagingQueryResult pagingQueryResult = new PagingQueryResult();
                    partResultList.add(pagingQueryResult);
                    pagingQueryResult.setId("id-" + i);
                    pagingQueryResult.setName("name-" + i);
                }

                pagingQueryParam.setCurrentPage(pagingQueryParam.getCurrentPage() + 1);
                return partResultList;
            }
        }.query(param);

        for (int i = 0; i < resultList.size(); i++ ) {
            PagingQueryResult pagingQueryResult = resultList.get(i);
            Assert.assertEquals(String.format("id-%s", String.valueOf(i)), pagingQueryResult.getId());
            Assert.assertEquals(String.format("name-%s", String.valueOf(i)), pagingQueryResult.getName());
            System.out.println(String.format("id=%s,name=%s", pagingQueryResult.getId(), pagingQueryResult.getName()));
        }
    }
}

class PagingQueryResult {

    private String id;
    private String name;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id  value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name  value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }
}

class PagingQueryParam {

    private int currentPage;
    private int pageSize;
    private int needQueryTotalCount;

    /**
     * Getter method for property <tt>currentPage</tt>.
     *
     * @return property value of currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Setter method for property <tt>currentPage</tt>.
     *
     * @param currentPage  value to be assigned to property currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     *
     * @return property value of pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     *
     * @param pageSize  value to be assigned to property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter method for property <tt>needQueryTotalCount</tt>.
     *
     * @return property value of needQueryTotalCount
     */
    public int getNeedQueryTotalCount() {
        return needQueryTotalCount;
    }

    /**
     * Setter method for property <tt>needQueryTotalCount</tt>.
     *
     * @param needQueryTotalCount  value to be assigned to property needQueryTotalCount
     */
    public void setNeedQueryTotalCount(int needQueryTotalCount) {
        this.needQueryTotalCount = needQueryTotalCount;
    }
}