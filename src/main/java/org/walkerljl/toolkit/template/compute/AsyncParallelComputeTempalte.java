package org.walkerljl.toolkit.template.compute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.walkerljl.toolkit.standard.NameValue;

/**
 * 异步并行计算模板
 *
 * @param <PARAM>
 * @param <RESULT>
 * @author lijunlin
 */
public abstract class AsyncParallelComputeTempalte<PARAM, RESULT> {

    /**
     * 执行计算
     *
     * @param threadPoolExecutor 执行任务的线程池
     * @param params 任务参数数组，执行引擎会为每个参数创建一个可被执行的任务。
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<PARAM, RESULT> compute(ThreadPoolExecutor threadPoolExecutor, PARAM[] params)
            throws ExecutionException, InterruptedException {
        return compute(threadPoolExecutor, params, false);
    }

    /**
     * 执行计算
     *
     * @param threadPoolExecutor 执行任务的线程池
     * @param params 任务参数数组，执行引擎会为每个参数创建一个可被执行的任务。
     * @param onlyOne 当一个计算任务被执行完成时立即返回结果，此时整个计算过程结束。
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<PARAM, RESULT> compute(ThreadPoolExecutor threadPoolExecutor, PARAM[] params, boolean onlyOne)
            throws ExecutionException, InterruptedException {
        if (isEmpty(params)) {
            return null;
        }
        List<PARAM> paramList = Arrays.asList(params);
        return compute(threadPoolExecutor, paramList, onlyOne);
    }

    /**
     * 执行计算
     *
     * @param threadPoolExecutor 执行任务的线程池
     * @param params 任务参数列表，执行引擎会为每个参数创建一个可被执行的任务。
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Map<PARAM, RESULT> compute(ThreadPoolExecutor threadPoolExecutor, List<PARAM> params)
            throws InterruptedException, ExecutionException {
        return compute(threadPoolExecutor, params, false);
    }

    /**
     * 执行计算
     *
     * @param threadPoolExecutor 执行任务的线程池
     * @param params 任务参数列表，执行引擎会为每个参数创建一个可被执行的任务。
     * @param onlyOne 当一个计算任务被执行完成时立即返回结果，此时整个计算过程结束。
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Map<PARAM, RESULT> compute(ThreadPoolExecutor threadPoolExecutor, List<PARAM> params, boolean onlyOne)
            throws InterruptedException, ExecutionException {
        if (isEmpty(params)) {
            return null;
        }

        Map<PARAM, RESULT> resultMap = new HashMap<PARAM, RESULT>();
        if (params.size() == 1) {
            PARAM param = params.get(0);
            RESULT result = compute0(param);
            if (result != null) {
                resultMap.put(param, result);
            }
        } else {
            CompletionService<NameValue<PARAM, RESULT>> completionService = new ExecutorCompletionService<NameValue<PARAM, RESULT>>(
                    threadPoolExecutor);
            List<Future<NameValue<PARAM, RESULT>>> futures = new ArrayList<Future<NameValue<PARAM, RESULT>>>();
            for (final PARAM param : params) {
                futures.add(completionService.submit(new Callable<NameValue<PARAM, RESULT>>() {
                    @Override
                    public NameValue<PARAM, RESULT> call() throws Exception {
                        RESULT result = compute0(param);
                        if (result == null) {
                            return null;
                        }
                        return new NameValue<PARAM, RESULT>(param, result);
                    }
                }));
            }
            for (Future<NameValue<PARAM, RESULT>> future : futures) {
                NameValue<PARAM, RESULT> result = future.get();
                if (result == null) {
                    continue;
                }
                resultMap.put(result.getName(), result.getValue());
                if (onlyOne) {
                    break;
                }
            }
            //cancel
            if (onlyOne) {
                for (Future<NameValue<PARAM, RESULT>> future : futures) {
                    future.cancel(true);
                }
            }
        }
        return resultMap;
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
     * 是否为空
     *
     * @param array 数组
     * @param <E>
     * @return
     */
    private <E> boolean isEmpty(E[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 完成单个任务计算功能
     *
     * @param param 参数
     * @return
     */
    public abstract RESULT compute0(PARAM param);
}