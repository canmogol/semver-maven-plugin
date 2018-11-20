package com.fererlab.semver;

import com.fererlab.semver.flow.FlowContext;
import com.fererlab.semver.flow.FlowException;
import com.fererlab.semver.flow.FlowStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.fail;

public class FlowContextTest {

    @Mock
    private FlowContext flowContext;

    @Mock
    FlowStrategy flowStrategy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidate() throws FlowException {
        Mockito.doCallRealMethod().when(flowContext).validate(flowStrategy);
        try {
            flowContext.validate(flowStrategy);
        } catch (Exception e) {
            fail("got exception while testing validate, exception: " + e.getMessage());
        }
    }

}
