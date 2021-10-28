package seedu.awe.model.payment;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.awe.model.group.Group;

public class PaymentList implements Iterable<Payment> {

    private final ObservableList<Payment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Payment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private Optional<Group> group;

    public PaymentList() {
        group = Optional.empty();
    }

    /**
     * List of payments.
     * @param group group from which payments are generated.
     */
    public PaymentList(Group group) {
        requireNonNull(group);
        this.group = Optional.of(group);
    }

    public void setGroup(Group group) {
        this.group = Optional.of(group);
    }

    public Optional<Group> getGroup() {
        return group;
    }

    /**
     * Returns true if the list contains an equivalent payment as the given argument.
     */
    public boolean contains(Payment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Clears the entire list of payments.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Adds all payments from the given list to the list of payments.
     *
     * @param payments List of payments to add.
     */
    public void addAll(List<Payment> payments) {
        internalList.addAll(payments);
    }

    /**
     * Adds an payment to the list.
     */
    public void add(Payment toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Payment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Payment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PaymentList)) { // instanceof handles nulls
            return false;
        }

        return internalList.equals(((PaymentList) other).internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
