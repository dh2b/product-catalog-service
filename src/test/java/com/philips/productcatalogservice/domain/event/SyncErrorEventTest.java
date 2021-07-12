package com.philips.productcatalogservice.domain.event;

import com.philips.productcatalog.domain.event.SyncErrorEvent;
import com.philips.productcatalogservice.helper.ProductHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

/**
 * Sync error event test.
 */
public class SyncErrorEventTest {

    private SyncErrorEvent syncErrorEvent;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        syncErrorEvent = buildSyncErrorEvent();
    }

    /**
     * Sync error event should not have any blank/null attribute.
     */
    @Test
    public void syncErrorEventShouldNotHaveAnyBlankAttribute() {
        Assert.assertNotNull(syncErrorEvent.getVersion());
        Assert.assertNotNull(syncErrorEvent.getHttpMethod());
        Assert.assertNotNull(syncErrorEvent.getProduct());
    }

    private SyncErrorEvent buildSyncErrorEvent() {
        return new SyncErrorEvent(HttpMethod.POST.name(), ProductHelper.buildProduct());
    }
}
