package org.walkerljl.toolkit.template.compute;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归查询模版
 *
 * @author xingxun
 */
public abstract class RecursiveQueryTemplate<PARAM, RESULT> {

    public List<RESULT> query(PARAM param) {
        if (param == null) {
            return null;
        }
        List<RESULT> resultList = new ArrayList<RESULT>();
        do  {
            List<RESULT> partResultList = query0(param);
            if (partResultList == null || partResultList.isEmpty()) {
                break;
            }
            resultList.addAll(partResultList);
        } while(isContinue(param));

        return  resultList;
    }

    protected abstract boolean isContinue(PARAM param);

    protected abstract List<RESULT> query0(PARAM param);

}