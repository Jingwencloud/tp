package seedu.awe.testutil;

import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.expense.IndividualAmount;
import seedu.awe.model.person.Person;
import seedu.awe.storage.JsonAdaptedIndividualAmount;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypicalIndividualAmounts {

    public static IndividualAmount ALICE_INDIVIDUAL_AMOUNT = new IndividualAmount(TypicalPersons.ALICE, 20.0);
    public static IndividualAmount AMY_INDIVIDUAL_AMOUNT = new IndividualAmount(TypicalPersons.AMY, 15.0);
    public static IndividualAmount BOB_INDIVIDUAL_AMOUNT = new IndividualAmount(TypicalPersons.BOB, 16.0);
    public static IndividualAmount CARL_INDIVIDUAL_AMOUNT = new IndividualAmount(TypicalPersons.CARL, 100.0);
    public static IndividualAmount DANIEL_INDIVIDUAL_AMOUNT =
            new IndividualAmount(TypicalPersons.DANIEL, 999.95);
    public static IndividualAmount ELLEL_INDIVIDUAL_AMOUNT =
            new IndividualAmount(TypicalPersons.ELLE, 17.31);


    public static List<IndividualAmount> VALID_INDIVIDUAL_AMOUNTS_ABC = List.of(ALICE_INDIVIDUAL_AMOUNT,
            AMY_INDIVIDUAL_AMOUNT, BOB_INDIVIDUAL_AMOUNT, CARL_INDIVIDUAL_AMOUNT);

    public static List<IndividualAmount> VALID_INDIVIDUAL_AMOUNTS_DE = List.of(DANIEL_INDIVIDUAL_AMOUNT,
            ELLEL_INDIVIDUAL_AMOUNT);

    public static Map<Person, Cost> VALID_EXPENSE_MAP_ABC = Map.of(TypicalPersons.ALICE, new Cost(20.0),
            TypicalPersons.AMY, new Cost(15.0), TypicalPersons.BOB, new Cost(16.0),
            TypicalPersons.CARL, new Cost(100.0));

    public static Map<Person, Cost> VALID_EXPENSE_MAP_DE = Map.of(TypicalPersons.DANIEL, new Cost(999.95),
            TypicalPersons.ELLE, new Cost(17.31));

    public static List<JsonAdaptedIndividualAmount> VALID_JSON_ADAPTED_INDIVIDUAL_AMOUNTS_ABC =
            VALID_INDIVIDUAL_AMOUNTS_ABC.stream().map(JsonAdaptedIndividualAmount::new).collect(Collectors.toList());

    public static List<JsonAdaptedIndividualAmount> VALID_JSON_ADAPTED_INDIVIDUAL_AMOUNTS_DE =
            VALID_INDIVIDUAL_AMOUNTS_DE.stream().map(JsonAdaptedIndividualAmount::new).collect(Collectors.toList());
}
