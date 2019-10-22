package seedu.exercise.logic.commands;

public interface ResourceTypeDependentCommand {

    /**
     * Returns the type of the resource being added to the model.
     *
     * @return the name of the resource being added, "exercise" or "regime"
     */
    String getResourceType();
}
