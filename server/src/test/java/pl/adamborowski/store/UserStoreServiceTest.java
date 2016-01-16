package pl.adamborowski.store;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.adamborowski.exception.ItemException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserStoreServiceTest {
    private UserStore userStore;

    @Before
    public void setUp() throws Exception {
        userStore = new UserStore();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String dev1 = "phone1";
    private String dev2 = "tablet1";

    // {A1/A2} x {B1/B2} x {C1/C2/C3}
    // {guid exist on server/not exist} x {name exist on server/not exist} x {createRequested/deleteRequested/deltaUpdate}

    @Test // guid exists, name exists, create requested
    public void testA1B1C1() throws ItemException {
        thrown.expect(ItemException.class);
        thrown.expectMessage("Cannot create item, the same GUID exists");
        userStore.requestCreateItem("guid1", "apple", dev1, 3);
        userStore.requestCreateItem("guid1", "apple", dev2, 3);
    }

    @Test // guid exists, name exists, delete requested
    public void testA1B1C2() throws ItemException {
        userStore.requestCreateItem("guid1", "apple", dev1, 3);
        assertThat(userStore.getItems(), CoreMatchers.is(CoreMatchers.not(Matchers.empty())));
        userStore.requestDeleteItem("guid1", "apple", dev2);
        assertThat(userStore.getItems(), CoreMatchers.is(Matchers.empty()));
    }

    // A1B2C1: guid exists, name not exists, create requested => invalid case
    // A1B2C2: guid exists, name not exists, delete requested => invalid case

    @Test // guid exists, name exists, update requested
    public void testA1B1C3() throws ItemException {
        userStore.requestCreateItem("guid1", "apple", dev1, 3);
        assertThat(userStore.getItems(), CoreMatchers.is(CoreMatchers.not(Matchers.empty())));
        userStore.updateItemDeviceDelta("guid1", dev1, 1);
        assertThat(userStore.getItems().get(0).getDeviceDelta(dev1), equalTo(1));
    }

    // A1B2C3: guid exists, name not exists, update requested => invalid case

    @Test // guid not exists, name exists, create requested
    public void testA2B1C1() throws ItemException {
        thrown.expect(ItemException.class);
        thrown.expectMessage("Cannot create item, the item of that name was created on another device");
        userStore.requestCreateItem("guid1", "apple", dev1, 1);
        userStore.requestCreateItem("guid2", "apple", dev1, 1);
    }

    @Test // guid not exists, name exists, delete requested
    public void testA2B1C2() throws ItemException {
        thrown.expect(ItemException.class);
        thrown.expectMessage("Cannot delete item, the item was deleted and recreated on another device");
        // given
        userStore.requestCreateItem("guid1", "apple", dev1, 1);
        // when
        userStore.requestDeleteItem("guid2", "apple", dev1);

    }

    // A2B1C3: guid not exists, name exists, update requested => invalid case

    @Test // guid not extists, name not exists, create requested
    public void testA2B2C1() throws ItemException {
        userStore.requestCreateItem("guid1", "apple", dev1, 3);
        assertThat(userStore.getItems().get(0).getSum(), equalTo(3));
    }

    @Test // guid not exists, name not exists, delete requested
    public void testA2B2C2() throws ItemException {
        thrown.expect(ItemException.class);
        thrown.expectMessage("Cannot delete item, the item was deleted on another device");
        userStore.requestDeleteItem("guid1", "apple", dev1);
    }

    @Test // guid not exists, name not exists, update requested
    public void testA2B2C3() throws ItemException {
        thrown.expect(ItemException.class);
        thrown.expectMessage("Cannot update item, the item was deleted on another device");
        userStore.updateItemDeviceDelta("guid1", dev1, 4);
    }






}