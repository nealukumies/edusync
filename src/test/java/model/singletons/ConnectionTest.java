package model.singletons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Test class for the Connection singleton.
 */
class ConnectionTest {

    @Test
    void getInstance() {
        final Connection instance1 = Connection.getInstance();
        final Connection instance2 = Connection.getInstance();
        assertSame(instance1, instance2);
    }
}