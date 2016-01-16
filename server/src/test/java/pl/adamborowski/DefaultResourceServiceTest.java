package pl.adamborowski;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.adamborowski.exception.ItemException;
import pl.adamborowski.store.UserStore;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResourceServiceTest {
    private UserStore userStore;

    @Before
    public void setUp() throws Exception {
        userStore = new UserStore();
    }

    @Test
    public void testCreateItem() throws ItemException {
        userStore.requestCreateItem("guid1", "apple", "phone1", 3);
        assertThat(userStore.getItems().get(0).getName(), CoreMatchers.equalTo("apple"));
    }

    @Test(expected = ItemException.class)
    public void testCreateItem_shouldFailIfNameExists() throws ItemException {
        userStore.requestCreateItem("quid1", "apple", "phone1", 4);
        userStore.requestCreateItem("quid2", "apple", "tablet1", 2);
    }

    @Test(expected = ItemException.class)
    public void testDeleteItem_shouldFailIfNotExists() throws ItemException {
        userStore.requestDeleteItem("quid1", "apple", "phone1");
    }
}