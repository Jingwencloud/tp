package seedu.awe.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.testutil.Assert.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.awe.commons.exceptions.IllegalValueException;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.expense.IndividualAmount;
import seedu.awe.testutil.TypicalExpenses;
import seedu.awe.testutil.TypicalGroups;
import seedu.awe.testutil.TypicalIndividualAmounts;

public class StorageUtilTest {

    public static final List<Expense> VALID_EXPENSES_BALI = TypicalExpenses.getTypicalExpenses(TypicalGroups.BALI);

    public static final List<Expense> VALID_EXPENSES_HELSINKI = TypicalExpenses
            .getTypicalExpenses(TypicalGroups.HELSINKI);

    public static final List<JsonAdaptedExpense> VALID_JSON_ADAPTED_EXPENSES_BALI = VALID_EXPENSES_BALI
            .stream()
            .map(JsonAdaptedExpense::new)
            .collect(Collectors.toList());

    public static final List<JsonAdaptedExpense> VALID_JSON_ADAPTED_EXPENSES_HELSINKI = VALID_EXPENSES_HELSINKI
            .stream()
            .map(JsonAdaptedExpense::new)
            .collect(Collectors.toList());

    @Test
    public void convertExpenseMapToListToIndividualAmounts_validInput_success() {
        List<IndividualAmount> listAbc1 = StorageUtil.convertExpenseMapToListOfIndividualAmounts(
                TypicalIndividualAmounts.VALID_EXPENSE_MAP_ABC);

        List<IndividualAmount> listAbc2 = TypicalIndividualAmounts.VALID_INDIVIDUAL_AMOUNTS_ABC;
        assertTrue(listAbc1.size() == listAbc2.size());
        for (IndividualAmount individualAmount1 : listAbc1) {
            boolean isFound = false;
            for (IndividualAmount individualAmount2 : listAbc2) {
                if (individualAmount1.equals(individualAmount2)) {
                    isFound = true;
                    break;
                }
            }
            assertTrue(isFound);
        }

        List<IndividualAmount> listDe1 = StorageUtil.convertExpenseMapToListOfIndividualAmounts(
                TypicalIndividualAmounts.VALID_EXPENSE_MAP_DE);
        List<IndividualAmount> listDe2 = TypicalIndividualAmounts.VALID_INDIVIDUAL_AMOUNTS_DE;
        assertEquals(listDe2.size(), listDe1.size());
        for (IndividualAmount individualAmount1 : listDe1) {
            boolean isFound = false;
            for (IndividualAmount individualAmount2 : listDe2) {
                if (individualAmount1.equals(individualAmount2)) {
                    isFound = true;
                    break;
                }
            }
            assertTrue(isFound);
        }
    }

    @Test
    public void convertExpenseMapToListToIndividualAmounts_invalidInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StorageUtil.convertExpenseMapToListOfIndividualAmounts(null));
    }

    @Test
    public void convertAdaptedExpensesToExpenses_validInput_success() throws IllegalValueException {
        assertEquals(VALID_EXPENSES_BALI,
                StorageUtil.convertAdaptedExpensesToExpenses(VALID_JSON_ADAPTED_EXPENSES_BALI));

        assertEquals(VALID_EXPENSES_HELSINKI,
                StorageUtil.convertAdaptedExpensesToExpenses(VALID_JSON_ADAPTED_EXPENSES_HELSINKI));
    }

    @Test
    public void convertAdaptedExpensesToExpenses_invalidInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StorageUtil.convertAdaptedExpensesToExpenses(null));
    }

    @Test
    public void convertListOfJsonAdaptedIndividualAmountsToExpenseMap_validInput_success()
            throws IllegalValueException {
        assertEquals(TypicalIndividualAmounts.VALID_EXPENSE_MAP_ABC, StorageUtil
                .convertListOfJsonAdaptedIndividualAmountsToExpenseMap(
                        TypicalIndividualAmounts.VALID_JSON_ADAPTED_INDIVIDUAL_AMOUNTS_ABC
                ));

        assertEquals(TypicalIndividualAmounts.VALID_EXPENSE_MAP_DE, StorageUtil
                .convertListOfJsonAdaptedIndividualAmountsToExpenseMap(
                        TypicalIndividualAmounts.VALID_JSON_ADAPTED_INDIVIDUAL_AMOUNTS_DE
                ));
    }

    @Test
    public void convertListOfJsonAdaptedIndividualAmountsToExpenseMap_invalidInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                StorageUtil.convertListOfJsonAdaptedIndividualAmountsToExpenseMap(null));
    }
}
