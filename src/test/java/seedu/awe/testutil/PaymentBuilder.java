package seedu.awe.testutil;

import static seedu.awe.testutil.TypicalPayments.ALICE_BOB_PAYMENT;

import seedu.awe.model.expense.Cost;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;

public class PaymentBuilder {

    private Person payer;
    private Person payee;
    private Cost cost;

    /**
     * Constructs PaymentBuilder.
     */
    public PaymentBuilder() {
        payee = ALICE_BOB_PAYMENT.getPayee();
        payer = ALICE_BOB_PAYMENT.getPayer();
        cost = ALICE_BOB_PAYMENT.getCost();
    }

    /**
     * Sets Payer field.
     * @param payer Person to set as payer.
     * @return edited Paymentbuilder
     */
    public PaymentBuilder withPayer(Person payer) {
        this.payer = payer;
        return this;
    }

    /**
     * Sets Payee field.
     * @param payee Person to set as payee.
     * @return edited Paymentbuilder
     */
    public PaymentBuilder withPayee(Person payee) {
        this.payee = payee;
        return this;
    }

    /**
     * Sets Cost field.
     * @param cost Cost to set as cost.
     * @return edited Paymentbuilder
     */
    public PaymentBuilder withCost(Cost cost) {
        this.cost = cost;
        return this;
    }

    /**
     * Produces payment object.
     * @return Payment object.
     */
    public Payment build() {
        return new Payment(payer, payee, cost);
    }
}
