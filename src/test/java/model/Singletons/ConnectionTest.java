package model.Singletons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    @Test
    void getInstance() {
        Connection instance1 = Connection.getInstance();
        Connection instance2 = Connection.getInstance();
        assertSame(instance1, instance2);
    }

    @Disabled
    void sendGetRequest() {
    }

    @Disabled
    void sendPostRequest() {
    }

    @Disabled
    void sendPutRequest() {
    }

    @Disabled
    void sendDeleteRequest() {
    }
}