package org.walkerljl.toolkit.template.handle.sal;

import org.junit.Test;
import org.walkerljl.toolkit.template.BaseUnitTest;

/**
 *
 * @author xingxun
 */
public class SalHandleTemplateTest extends BaseUnitTest {

    @Test
    public void testForLogger() {

        SalHandleTemplate.getInstance().getDigestLogger();
        SalHandleTemplate.getInstance().getDetailLogger();
        SalHandleTemplate.getInstance().getErrorLogger();
    }
}