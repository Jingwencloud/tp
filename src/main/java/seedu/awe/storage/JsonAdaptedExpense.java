package seedu.awe.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.awe.commons.exceptions.IllegalValueException;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Description;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.expense.IndividualAmount;
import seedu.awe.model.person.Address;
import seedu.awe.model.person.Person;

public class JsonAdaptedExpense {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final JsonAdaptedPerson payer;
    private final String cost;
    private final String description;
    private final List<JsonAdaptedPerson> included = new ArrayList<>();
    private final List<JsonAdaptedIndividualAmount> individualExpenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("payer") JsonAdaptedPerson payer,
                              @JsonProperty("cost") String cost,
                              @JsonProperty("description") String description,
                              @JsonProperty("included") List<JsonAdaptedPerson> included,
                              @JsonProperty("individualExpenses")
                                          List<JsonAdaptedIndividualAmount> individualExpenses) {
        this.payer = payer;
        this.cost = cost;
        this.description = description;
        this.included.addAll(included);
        if (individualExpenses != null) {
            this.individualExpenses.addAll(individualExpenses);
        }
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        payer = new JsonAdaptedPerson(source.getPayer());
        cost = String.valueOf(source.getCost().getCost());
        description = source.getDescription().toString();
        included.addAll(source.getIncluded()
                .stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        Map<Person, Cost> individualExpenses = source
                .getIndividualExpenses();
        List<IndividualAmount> individualAmounts = StorageUtil
                .convertExpenseMapToListOfIndividualAmounts(individualExpenses);
        this.individualExpenses.addAll(individualAmounts
                .stream()
                .map(JsonAdaptedIndividualAmount::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Expense toModelType() throws IllegalValueException {
        final Person modelPayer = payer.toModelType();

        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Cost modelCost = new Cost(cost);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        final ArrayList<Person> modelIncluded = new ArrayList<>();
        for (JsonAdaptedPerson includedPerson : included) {
            modelIncluded.add(includedPerson.toModelType());
        }
        final Description modelDescription = new Description(description);

        Map<Person, Cost> individualExpenses = StorageUtil
                .convertListOfJsonAdaptedIndividualAmountsToExpenseMap(this.individualExpenses);

        return new Expense(modelPayer, modelCost, modelDescription, modelIncluded, individualExpenses);
    }
}
