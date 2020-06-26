
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ProfilActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.StartViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myNeighboursList_onClickItem_shouldShowDetails() {
        // Start the list neighbours view
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        // When perform a click on a item view
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Show details of element
        onView(ViewMatchers.withId(R.id.profil_neighbour)).check(matches(isDisplayed()));
    }

    @Test
    public void myProfil_onStartUp_shouldShowNameInTextView() {
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.profil_neighbour)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.profil_name)).check(matches(withText("Caroline")));
        // onView(withText(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0).getName())).check(matches(isDisplayed()));
    }

    @Test
    public void myFavoritesNeighboursList_shouldDisplayOnlyFavoritesNeighbours()throws Exception{
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        // Click on first item view
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.profil_neighbour)).check(matches(isDisplayed()));
        // Click to add it in favorite
        onView(ViewMatchers.withId(R.id.profil_button_addFavorite)).perform(click());
        // Click back button to return in neighbours list
        onView(ViewMatchers.withId(R.id.profil_back_button)).perform(click());
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        // Swipe view pager to show favorites list
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(swipeLeft());
        onView(ViewMatchers.withId(R.id.list_favorites)).check(matches(isDisplayed()));
        // Check if favorites list is not empty
        onView(ViewMatchers.withId(R.id.list_favorites)).check(matches(hasMinimumChildCount(1)));
    }
}