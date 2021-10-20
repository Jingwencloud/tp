package seedu.awe.model.expense;

import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import seedu.awe.model.person.Person;


public class Expense {

    // identity fields
    private final Person payer;

    // data fields
    private final Cost cost;
    private final Description description;
    private final List<Person> included;
    private final Map<Person, Cost> individualExpenses;

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
        included = new ArrayList<>();
        individualExpenses = new HashMap<>();
    }

    /**
     * Constructs an {@code Expense}.
     *
     * @param payer of expense.
     * @param cost of expense.
     * @param description of expense.
     * @param included Persons to be included into paying the expense.
     */
    public Expense(Person payer, Cost cost, Description description, List<Person> included) {
        this.payer = payer;
        this.cost = cost;
        this.description = description;
        this.included = included;
        this.individualExpenses = new HashMap<>();
        for (Person person : included) {
            individualExpenses.put(person, new Cost(0));
        }
    }

    /**
     * Constructs an {@code Expense}.
     * @param payer of expense.
     * @param cost of expense.
     * @param description of expense.
     * @param included Persons to be included into paying the expense.
     * @param individualExpenses Expense of each included person.
     */
    public Expense(Person payer, Cost cost, Description description, List<Person> included,
                   Map<Person, Cost> individualExpenses) {
        this.payer = payer;
        this.cost = cost;
        this.description = description;
        this.included = included;
        this.individualExpenses = individualExpenses;
    }

    public Expense setIndividualExpenses(HashMap<Person, Cost> individualExpenses) {
        return new Expense(payer, cost, description, included, individualExpenses);
    }

    public Expense setCost(Cost newCost) {
        return new Expense(payer, newCost, description, included, individualExpenses);
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

    public List<Person> getIncluded() {
        return included;
    }

    public Map<Person, Cost> getIndividualExpenses() {
        return individualExpenses;
    }

    public Expense setIncluded(List<Person> included) {
        List<Person> includedCopy = new ArrayList<>(this.included);
        includedCopy.addAll(included);
        return new Expense(payer, cost, description, includedCopy, individualExpenses);
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
