package seedu.exercise.testutil.regime;

import static seedu.exercise.testutil.exercise.TypicalExercises.getTypicalExercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * A utility class containing a list of {@code Regime} objects to be used in tests.
 */
public class TypicalRegimes {

    public static final List<Exercise> EXERCISES = getTypicalExercises();
    public static final Regime REGIME_LEVEL_1 = new RegimeBuilder()
            .withName("Level 1").withExercises(EXERCISES.subList(0, 3)).build();
    public static final Regime REGIME_LEVEL_2 = new RegimeBuilder()
            .withName("Level 2").withExercises(EXERCISES.subList(3, 5)).build();
    public static final Regime REGIME_LEVEL_3 = new RegimeBuilder()
            .withName("Level 3").withExercises(EXERCISES.subList(5, 7)).build();
    public static final Regime REGIME_LEVEL_4 = new RegimeBuilder()
            .withName("Level 4").build();

    /**
     * Returns an {@code ReadOnlyResourceBook<Exercise>} with all the typical exercises.
     */
    public static ReadOnlyResourceBook<Regime> getTypicalRegimeBook() {
        ReadOnlyResourceBook<Regime> regimeBook = new ReadOnlyResourceBook<>();
        for (Regime regime : getTypicalRegimes()) {
            regimeBook.addResource(regime);
        }
        return regimeBook;
    }

    public static List<Regime> getTypicalRegimes() {
        return new ArrayList<>(
                Arrays.asList(REGIME_LEVEL_1, REGIME_LEVEL_2, REGIME_LEVEL_3, REGIME_LEVEL_4));
    }
}
