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

    // data processed fields
    private final Map<Person, Cost> splitExpenses;


    /**
     * Constructs an {@code Expense}.
     *
     * @param payer of expense.
     * @param cost of expense.
     * @param description of expense.
     * @param included Persons to be included into paying the expense.
     */
    public Expense(Person payer, Cost cost, Description description, List<Person> included) {
        this(payer, cost, description, included, new HashMap<>());
    }

    /**
     * Constructs an {@code Expense}.
     *
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
        this.splitExpenses = new HashMap<>();

        calculateSplitExpense();
    }

    /**
     * Calculates the amount each person has to pay for this expense.
     */
    public void calculateSplitExpense() {
        double toSplitAmount = cost.getCost();

        for (Map.Entry<Person, Cost> entry : individualExpenses.entrySet()) {
            splitExpenses.put(entry.getKey(), entry.getValue());
            toSplitAmount -= entry.getValue().getCost();
        }

        toSplitAmount = toSplitAmount / (included.size() - individualExpenses.size());

        for (Person person: included) {
            splitExpenses.putIfAbsent(person, new Cost(toSplitAmount));
        }
    }

    public Map<Person, Cost> getSplitExpenses() {
        return splitExpenses;
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

    /**
     * Replaces the payee {@code target} with {@code editedPerson}.
     */
    public Optional<Expense> updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        Person payer = this.payer;
        List<Person> included = new ArrayList<>(this.included);
        Map<Person, Cost> individualExpenses = new HashMap<>();
        individualExpenses.putAll(this.individualExpenses);
        boolean isModified = false;

        if (payer.equals(target)) {
            payer = editedPerson;
            isModified = true;
        }

        if (included.contains(target)) {
            included.set(included.indexOf(target), editedPerson);
            isModified = true;
        }

        Cost toAddBack = individualExpenses.remove(target);
        if (!Objects.isNull(toAddBack)) {
            individualExpenses.put(editedPerson, toAddBack);
            isModified = true;
        }

        return isModified
                ? Optional.ofNullable(new Expense(payer, cost, description, included, individualExpenses))
                : Optional.empty();
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
