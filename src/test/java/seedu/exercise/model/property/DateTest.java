package seedu.exercise.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid phone numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // less than 3 numbers
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("32/02/2019")); // A month cannot have 32 days

        // valid phone numbers
        assertTrue(Date.isValidDate("12/02/2019"));
        assertTrue(Date.isValidDate("31/10/2019"));
        assertTrue(Date.isValidDate("31/02/2019")); // Date will be round down to the latest day of that month
    }

    @Test
    public void isEndDateAfterStartDate() {
        //same date
        assertTrue(Date.isEndDateAfterStartDate("12/01/2019", "12/01/2019"));

        //end date after start date
        assertTrue(Date.isEndDateAfterStartDate("01/01/2019", "02/03/2019"));

        //end date before start date
        assertFalse(Date.isEndDateAfterStartDate("02/01/2019", "01/11/2018"));
    }

    @Test
    public void isBetweenStartAndEndDate() {
        //all same date
        assertTrue(Date.isBetweenStartAndEndDate(new Date("01/10/2019"), new Date("01/10/2019"),
                new Date("01/10/2019")));

        //target date between start and end date
        assertTrue(Date.isBetweenStartAndEndDate(new Date("15/03/2019"), new Date("01/03/2019"),
                new Date("30/03/2019")));

        //target date not in between start and end date
        assertFalse(Date.isBetweenStartAndEndDate(new Date("14/12/2019"), new Date("01/03/2019"),
                new Date("30/03/2019")));
    }

    @Test
    public void numberOfDaysBetween() {
        //same date
        assertEquals(Date.numberOfDaysBetween(new Date("10/01/2019"), new Date("10/01/2019")), 0);

        //end date before start date
        assertEquals(Date.numberOfDaysBetween(new Date("09/01/2019"), new Date("01/01/2019")), -8);

        //a week apart
        assertEquals(Date.numberOfDaysBetween(new Date("01/01/2019"), new Date("08/01/2019")), 7);
    }

    @Test
    public void getListOfDates() {
        ArrayList<Date> actualDates = Date.getListOfDates(new Date("01/05/2019"), new Date("05/05/2019"));
        ArrayList<Date> expectedDate = new ArrayList<>();
        expectedDate.add(new Date("01/05/2019"));
        expectedDate.add(new Date("02/05/2019"));
        expectedDate.add(new Date("03/05/2019"));
        expectedDate.add(new Date("04/05/2019"));
        expectedDate.add(new Date("05/05/2019"));

        assertEquals(actualDates, expectedDate);
    }
}