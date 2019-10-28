package seedu.exercise.testutil.regime;

import static seedu.exercise.testutil.exercise.TypicalExercises.CLAP;
import static seedu.exercise.testutil.exercise.TypicalExercises.SLAP;

import java.util.Arrays;
import java.util.List;

import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * A utility class to help with building Regime objects.
 */
public class RegimeBuilder {
    private static final String DEFAULT_NAME = "Default Regime";
    private static final UniqueResourceList<Exercise> DEFAULT_EXERCISES = new UniqueResourceList<>();
    static {
        DEFAULT_EXERCISES.add(CLAP);
        DEFAULT_EXERCISES.add(SLAP);
    }


    private Name name;
    private UniqueResourceList<Exercise> exercises;

    public RegimeBuilder() {
        name = new Name(DEFAULT_NAME);
        exercises = new UniqueResourceList<>();
        exercises.setAll(DEFAULT_EXERCISES);
    }

    /**
     * Initializes the RegimeBuilder with a deep copy of the data of {@code RegimeToCopy}.
     */
    public RegimeBuilder(Regime regimeToCopy) {
        name = new Name(regimeToCopy.getRegimeName().toString());
        exercises = new UniqueResourceList<>();
        exercises.setAll(regimeToCopy.getRegimeExercises());
    }

    /**
     * Sets the {@code Name} of the {@code Regime} that we are building.
     */
    public RegimeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Exercise}s of the {@code Regime} that we are building.
     */
    public RegimeBuilder withExercises(Exercise... exercises) {
        UniqueResourceList<Exercise> uniqueExerciseList = new UniqueResourceList<>();
        uniqueExerciseList.setAll(Arrays.asList(exercises));
        this.exercises = uniqueExerciseList;
        return this;
    }

    /**
     * Sets the {@code Exercise}s of the {@code Regime} that we are building.
     */
    public RegimeBuilder withExercises(List<Exercise> exercises) {
        UniqueResourceList<Exercise> uniqueExerciseList = new UniqueResourceList<>();
        uniqueExerciseList.setAll(exercises);
        this.exercises = uniqueExerciseList;
        return this;
    }

    /**
     * Adds an {@code Exercise} to the {@code Regime} that we are building.
     */
    public RegimeBuilder addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        return this;
    }

    /**
     * Deletes an {@code Exercise} from the {@code Regime} that we are building.
     */
    public RegimeBuilder deleteExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        return this;
    }

    public Regime build() {
        return new Regime(name, exercises);
    }

}
