package pl.adamborowski.store;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ItemStoreTest {

    private ItemStore itemStore;

    @Before
    public void setUp() throws Exception {
        itemStore = new ItemStore("guid1", "apple", 0);
    }

    @Test
    public void testUpdateDeviceDelta() throws Exception {
        // given

        // when
        itemStore.updateDeviceDelta("telefon", 3);
        itemStore.updateDeviceDelta("tablet", 5);
        itemStore.updateDeviceDelta("telefon", 2);

        // then

        assertThat(itemStore.getSum(), CoreMatchers.equalTo(7));
    }
}