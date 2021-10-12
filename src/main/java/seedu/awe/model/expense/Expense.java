package seedu.awe.model.expense;

import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.awe.model.person.Person;


public class Expense {

    // identity fields
    private final Person payer;

    // data fields
    private final Cost cost;
    private final Description description;
    private final List<Person> excluded;

    /**
     * Constructs an {@code Expense}.
     *
     * @param payer of expense.
     * @param cost of expense.
     * @param description of expense.
     */
    public Expense(Person payer, Cost cost, Description description) {
        this.payer = payer;
        this.cost = cost;
        this.description = description;
        excluded = new ArrayList<>();
    }

    /**
     * Constructs an {@code Expense}.
     *
     * @param payer of expense.
     * @param cost of expense.
     * @param description of expense.
     * @param excluded Person to be excluded from paying the expense.
     */
    public Expense(Person payer, Cost cost, Description description, List<Person> excluded) {
        this.payer = payer;
        this.cost = cost;
        this.description = description;
        this.excluded = excluded;
    }

    public Expense setCost(Cost newCost) {
        return new Expense(payer, newCost, description, excluded);
    }

    public Person getPayer() {
        return payer;
    }

    public Cost getCost() {
        return cost;
    }

    public Description getDescription() {
        return description;
    }

    public List<Person> getExcluded() {
        return excluded;
    }

    /**
     * Replaces the payee {@code target} with {@code editedPerson}.
     */
    public Optional<Expense> updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        if (payer.equals(target)) {
            return Optional.of(new Expense(editedPerson, cost, description));
        }

        return Optional.ofNullable(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getPayer().equals(getPayer())
                && otherExpense.getCost().equals(getCost())
                && otherExpense.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer, cost, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPayer())
                .append(" paid ")
                .append(getCost())
                .append(" for ")
                .append(getDescription())
                .append(".");
        return builder.toString();
    }
}
