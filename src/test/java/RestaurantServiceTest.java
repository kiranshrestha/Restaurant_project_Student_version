import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void initRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Chilly Chicken",309);
        restaurant.addToMenu("Chilly Paneer", 169);
        restaurant.addToMenu("Honey Chilly Potato", 100);
        restaurant.addToMenu("Egg Role", 90);
    }



    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant restaurant = service.findRestaurantByName("Amelie's cafe");
        assertEquals(restaurant.getName(), "Amelie's cafe");
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Amelie's"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void when_no_item_is_selected_the_order_total_will_give_zero() {
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Mockito.when(restaurantSpy.getSelectedItems()).thenReturn(new ArrayList<>());
        double orderValue = restaurantSpy.getTotalOrderPrice();
        assertEquals(orderValue, 0f);
    }

    @Test
    public void when_3_items_are_selected_from_menu_from_starting_point_then_order_value_should_be_the_total_of_the_3_selected_items() {

        Restaurant restaurantSpy = Mockito.spy(restaurant);

        //Selecting first 3 items.
        for (int i = 0; i < restaurantSpy.getMenu().size(); i++) {
            if(i<3) {
                restaurantSpy.getMenu().get(i).setSelected(true);
            } else
                break;
        }
        ArrayList<Item> selection = (ArrayList<Item>) restaurantSpy.getMenu().stream().filter(Item::isSelected).collect(Collectors.toList());
        Mockito.when(restaurantSpy.getSelectedItems()).thenReturn(selection);
        double orderValue = restaurantSpy.getTotalOrderPrice();
        assertEquals(orderValue, 697f );
    }

    @Test
    public void when_last_3_items_are_selected_from_menu_then_order_value_should_be_the_total_of_the_3_selected_items() {

        Restaurant restaurantSpy = Mockito.spy(restaurant);

        //Selecting last 3 items.
        for (int i = restaurantSpy.getMenu().size(); i > restaurantSpy.getMenu().size() - 3 ; i--) {
            restaurantSpy.getMenu().get(i-1).setSelected(true);
        }
        ArrayList<Item> selection = (ArrayList<Item>) restaurantSpy.getMenu().stream().filter(Item::isSelected).collect(Collectors.toList());
        Mockito.when(restaurantSpy.getSelectedItems()).thenReturn(selection);
        double orderValue = restaurantSpy.getTotalOrderPrice();
        assertEquals(359f, orderValue );
    }

    @Test
    public void when_all_items_are_selected_from_menu_then_order_value_should_be_the_total_of_all_items() {

        Restaurant restaurantSpy = Mockito.spy(restaurant);

        //Selecting first 3 items.
        for (int i = 0; i < restaurantSpy.getMenu().size(); i++) {
            restaurantSpy.getMenu().get(i).setSelected(true);
        }
        ArrayList<Item> selection = (ArrayList<Item>) restaurantSpy.getMenu().stream().filter(Item::isSelected).collect(Collectors.toList());
        Mockito.when(restaurantSpy.getSelectedItems()).thenReturn(selection);
        double orderValue = restaurantSpy.getTotalOrderPrice();
        assertEquals(359 + 697, orderValue);
    }

}