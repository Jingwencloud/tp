package seedu.awe.model.expense;

import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import seedu.awe.model.person.Person;

public class IndividualAmount {

    private Person person;
    private Double expenditure;

    /**
     * Intermediate class to store expense maps in json format
     * @param person person to whom expenditure is attached.
     * @param expenditure expenditure attached to person.
     */
    public IndividualAmount(Person person, double expenditure) {
        requireAllNonNull(person, expenditure);
        this.person = person;
        this.expenditure = expenditure;
    }

    public Person getPerson() {
        return person;
    }

    public Double getExpenditure() {
        return expenditure;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof IndividualAmount)) {
            return false;
        }

        IndividualAmount individualAmount = (IndividualAmount) other;

        boolean isEqualExpenditure = this.expenditure.equals(individualAmount.getExpenditure());
        boolean isSamePerson = this.person.equals(individualAmount.getPerson());

        return isEqualExpenditure && isSamePerson;
    }
}
