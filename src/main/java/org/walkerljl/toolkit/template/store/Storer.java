package org.walkerljl.toolkit.template.store;

import org.walkerljl.toolkit.standard.exception.AppException;

/**
 * Storer
 *
 * @author lijunlin
 * @Date 2016/12/19
 */
public interface Storer<T> {

    void store(T element) throws AppException;
}
