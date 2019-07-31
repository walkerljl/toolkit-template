package org.walkerljl.toolkit.template.compute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 递归查询模版
 *
 * @author xingxun
 */
public abstract class RecursiveQueryTemplate<PARAM, RESULT> {

    /**
     * 查询
     *
     * @param param 查询参数
     * @return
     */
    public List<RESULT> query(PARAM param) {
        if (param == null) {
            return null;
        }
        List<RESULT> resultList = new ArrayList<>();
        do {
            List<RESULT> partResultList = query0(param);
            if (isEmpty(partResultList)) {
                break;
            }
            resultList.addAll(partResultList);
        } while (isContinue(param));

        return resultList;
    }

    /**
     * 是否为空
     *
     * @param collection 集合
     * @return
     */
    private boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 是否继续查询
     *
     * @param param 查询参数
     * @return
     */
    protected abstract boolean isContinue(PARAM param);

    /**
     * 执行具体的查询业务
     *
     * @param param 查询参数
     * @return
     */
    protected abstract List<RESULT> query0(PARAM param);
}