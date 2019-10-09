package seedu.exercise.logic.commands.history;

import java.util.Stack;

import seedu.exercise.logic.commands.Command;
import seedu.exercise.logic.commands.UndoableCommand;

/**
 * Tracks the history of commands for undo and redo.
 */
public class ActionHistory {

    private static Stack<Action> undoStack;
    private static Stack<Action> redoStack;

    private ActionHistory() {
        if (undoStack == null) {
            undoStack = new Stack<>();
            redoStack = new Stack<>();
        }
    }

    public static ActionHistory getInstance() {
        return new ActionHistory();
    }

    public void addToRedoStack(UndoableCommand command) {
        undoStack.add(new Action(command));
        redoStack.clear();
    }

    public Action popUndoStack() {
        Action actionToUndo = undoStack.pop();
        redoStack.push(actionToUndo);
        return actionToUndo;
    }

    public Action popRedoStack() {
        Action actionToRedo = redoStack.pop();
        undoStack.push(actionToRedo);
        return actionToRedo;
    }

    public boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    public boolean isRedoStackEmpty() {
        return redoStack.isEmpty();
    }
}
