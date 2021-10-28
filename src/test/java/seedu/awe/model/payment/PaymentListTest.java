package seedu.awe.model.payment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.testutil.Assert.assertThrows;
import static seedu.awe.testutil.TypicalPayments.ALICE_BOB_COST;
import static seedu.awe.testutil.TypicalPayments.ALICE_BOB_PAYMENT;
import static seedu.awe.testutil.TypicalPersons.ALICE;
import static seedu.awe.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.awe.testutil.PaymentBuilder;

public class PaymentListTest {

    private final PaymentList paymentList = new PaymentList();

    @Test
    public void contains_nullPayment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> paymentList.contains(null));
    }

    @Test
    public void contains_paymentNotInList_returnsFalse() {
        assertFalse(paymentList.contains(ALICE_BOB_PAYMENT));
    }

    @Test
    public void contains_paymentInList_returnsTrue() {
        paymentList.add(ALICE_BOB_PAYMENT);
        assertTrue(paymentList.contains(ALICE_BOB_PAYMENT));
    }

    @Test
    public void contains_paymentWithSameIdentityFieldsInList_returnsTrue() {
        paymentList.add(ALICE_BOB_PAYMENT);
        Payment editedPayment = new PaymentBuilder().withPayer(ALICE).withPayee(BOB).withCost(ALICE_BOB_COST).build();
        assertTrue(paymentList.contains(editedPayment));
    }

    @Test
    public void add_nullPayment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> paymentList.add(null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                paymentList.asUnmodifiableObservableList().remove(0));
    }
}
