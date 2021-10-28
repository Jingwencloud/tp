package seedu.awe.testutil;

import java.util.List;

import seedu.awe.model.expense.Cost;
import seedu.awe.model.payment.Payment;

public class TypicalPayments {

    public static final Cost ALICE_BOB_COST = new Cost(20.10);
    public static final Cost AMY_ELLE_COST = new Cost(71.34);
    public static final Cost DANIEL_FIONA_COST = new Cost(65.88);
    public static final Cost CARL_DANIEL_COST = new Cost(190.59);
    public static final Cost ALICE_CARL_COST = new Cost(29.08);

    public static final Payment ALICE_BOB_PAYMENT =
            new Payment(TypicalPersons.ALICE, TypicalPersons.BOB, ALICE_BOB_COST);

    public static final Payment AMY_ELLE_PAYMENT =
            new Payment(TypicalPersons.AMY, TypicalPersons.ELLE, AMY_ELLE_COST);

    public static final Payment DANIEL_FIONA_PAYMENT =
            new Payment(TypicalPersons.DANIEL, TypicalPersons.FIONA, DANIEL_FIONA_COST);

    public static final Payment CARL_DANIEL_PAYMENT =
            new Payment(TypicalPersons.CARL, TypicalPersons.DANIEL, CARL_DANIEL_COST);

    public static final Payment ALICE_CARL_PAYMENT =
            new Payment(TypicalPersons.ALICE, TypicalPersons.CARL, ALICE_CARL_COST);

    public static final List<Payment> VALID_PAYMENT_LIST =
            List.of(ALICE_BOB_PAYMENT, ALICE_CARL_PAYMENT, CARL_DANIEL_PAYMENT,
            DANIEL_FIONA_PAYMENT, AMY_ELLE_PAYMENT);

}
