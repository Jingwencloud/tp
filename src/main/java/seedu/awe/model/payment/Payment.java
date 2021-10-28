package seedu.awe.model.payment;

import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.awe.model.expense.Cost;
import seedu.awe.model.person.Person;

public class Payment implements Comparable<Payment> {

    private Person payer;
    private Person payee;
    private Cost cost;
    /**
     * Payment object represents transactions to be made to balance expenses.
     * @param payer person to pay amount
     * @param payee person to be paid amount
     * @param cost amount to pay
     */
    public Payment(Person payer, Person payee, Cost cost) {
        requireAllNonNull(payer, payee, cost);
        this.payer = payer;
        this.payee = payee;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("%s pays $%.2f to %s.", payee.getName(), getCost().getCost(), payer.getName());
    }

    public Person getPayer() {
        return payer;
    }

    public Person getPayee() {
        return payee;
    }

    public Cost getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Payment)) {
            return false;
        }

        Payment otherExpense = (Payment) other;
        boolean isSamePayer = otherExpense.getPayer().equals(getPayer());
        boolean isSamePayee = otherExpense.getPayee().equals(getPayee());
        boolean isSameCost = otherExpense.getCost().equals(getCost());

        return isSamePayer && isSamePayee && isSameCost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer, payee, cost);
    }

    /**
     * Sort by payer name then payee name.
     * @param otherPayment Payment to compare
     * @return Integer to mark priority
     */
    @Override
    public int compareTo(Payment otherPayment) {
        String payeeName = this.getPayee().getName().getFullName();
        String otherPayeeName = this.getPayee().getName().getFullName();
        String payerName = this.getPayer().getName().getFullName();
        String otherPayerName = this.getPayer().getName().getFullName();

        if (payerName.compareTo(otherPayerName) == -1) {
            return -1;
        } else if (payerName.compareTo(otherPayerName) == 1) {
            return 1;
        } else {
            if (payeeName.compareTo(otherPayeeName) == -1) {
                return -1;
            } else if (payeeName.compareTo(otherPayeeName) == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
