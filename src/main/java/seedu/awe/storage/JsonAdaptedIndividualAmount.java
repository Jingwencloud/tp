package seedu.awe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.awe.commons.exceptions.IllegalValueException;
import seedu.awe.model.expense.IndividualAmount;

public class JsonAdaptedIndividualAmount {

    private JsonAdaptedPerson person;
    private String expenditure;

    /**
     * Constructs a {@code JsonAdaptedIndividualAmount} with the given individual expenditure/payment
     * details.
     */
    @JsonCreator
    public JsonAdaptedIndividualAmount (@JsonProperty("person") JsonAdaptedPerson jsonAdaptedPerson,
                              @JsonProperty("expenditure") String expenditure) {
        this.person = jsonAdaptedPerson;
        this.expenditure = expenditure;
    }

    /**
     * Converts individual amount object to jsonadapted individual amount object.
     * @param source IndividualAmount object to convert into jsonadapted form.
     */
    public JsonAdaptedIndividualAmount(IndividualAmount source) {
        this.person = new JsonAdaptedPerson(source.getPerson());
        this.expenditure = String.valueOf(source.getExpenditure());
    }

    public IndividualAmount toModelType() throws IllegalValueException {
        return new IndividualAmount(person.toModelType(), Double.parseDouble(expenditure));
    }
}
