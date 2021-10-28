package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_CALCULATEPAYMENTSCOMMAND_GROUP_NOT_FOUND;
import static seedu.awe.commons.core.Messages.MESSAGE_CALCULATEPAYMENTSCOMMAND_PAYMENTS_EMPTY;
import static seedu.awe.commons.core.Messages.MESSAGE_CALCULATEPAYMENTSCOMMAND_SUCCESS;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.group.Group;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;

public class CalculatePaymentsCommand extends Command {
    public static final String COMMAND_WORD = "calculatepayments";

    private final Group group;

    public CalculatePaymentsCommand(Group group) {
        this.group = group;
    }

    private class Pair implements Comparable<Pair> {

        private double surplus;
        private Person person;

        Pair(double surplus, Person person) {
            this.surplus = surplus;
            this.person = person;
        }

        double getSurplus() {
            return surplus;
        }

        Person getPerson() {
            return person;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof Pair)) { // instanceof handles nulls
                return false;
            }

            Pair otherPayment = (Pair) other;

            boolean isSamePerson = person.equals(otherPayment.getPerson());
            boolean isSameSurplus = surplus == otherPayment.getSurplus();

            return isSamePerson && isSameSurplus;
        }

        @Override
        public int hashCode() {
            return Objects.hash(surplus, person);
        }

        @Override
        public int compareTo(Pair p2) {
            if (this.getSurplus() < p2.getSurplus()) {
                return -1;
            } else if (this.getSurplus() > p2.getSurplus()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(group);

        if (!model.hasGroup(group)) {
            throw new CommandException(MESSAGE_CALCULATEPAYMENTSCOMMAND_GROUP_NOT_FOUND);
        }

        Group group = model.getGroupByName(this.group.getGroupName());
        List<Payment> payments = getPayments(group);
        model.setPayments(payments);

        if (payments.isEmpty()) {
            return new CommandResult(MESSAGE_CALCULATEPAYMENTSCOMMAND_PAYMENTS_EMPTY, false, false, false,
                    false, false, false,
                    true);
        } else {
            return new CommandResult(MESSAGE_CALCULATEPAYMENTSCOMMAND_SUCCESS, false, false, false,
                    false, false, false,
                    true);
        }
    }

    private static List<Pair> sortPairs(List<Pair> pairs) {
        pairs.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair pair, Pair t1) {
                return pair.compareTo(t1);
            }
        });
        return pairs;
    }

    private List<Pair> getNamesAndSurplusesList(Group group) {
        List<Pair> namesAndSurpluses = new ArrayList<>();
        Map<Person, Cost> amountsPaid = group.getPaidByPayers();
        Map<Person, Cost> expensesIncurred = group.getSplitExpenses();
        List<Person> members = new ArrayList<>();
        for (Person person : amountsPaid.keySet()) {
            if (!members.contains(person)) {
                members.add(person);
            }
        }
        for (Person person : expensesIncurred.keySet()) {
            if (!members.contains(person)) {
                members.add(person);
            }
        }
        double marginOfError = 0.01;
        for (Person person: members) {
            Cost amountPaid = amountsPaid.getOrDefault(person, new Cost(0.0));
            Cost expenseIncurred = expensesIncurred.getOrDefault(person, new Cost(0.0));
            double surplus = amountPaid.getCost() - expenseIncurred.getCost();
            if (Math.abs(surplus) >= marginOfError) {
                Pair nameSurplusPair = new Pair(surplus, person);
                namesAndSurpluses.add(nameSurplusPair);
            }
        }
        return namesAndSurpluses;
    }

    /**
     * Calculates payments to be made between Persons in the group.
     * @param group group for which payments are to be calculated
     * @return List of payments to make.
     */
    private List<Payment> getPayments(Group group) throws CommandException {
        List<Pair> namesAndSurplusesList = getNamesAndSurplusesList(group);
        List<Payment> payments = calculatePayments(namesAndSurplusesList);
        payments.sort(new Comparator<Payment>() {
            @Override
            public int compare(Payment payment, Payment t1) {
                return payment.compareTo(t1);
            }
        });
        return payments;
    }

    private boolean checkSumIsZero(List<Pair> pairs) {
        double total = 0.00;
        for (Pair pair: pairs) {
            total += pair.getSurplus();
        }
        double marginOfError = 0.01;
        return Math.abs(total) < marginOfError;
    }

    private static Optional<Pair> getSmallerPair(Pair p1, Pair p2) {
        requireAllNonNull(p1, p2);
        double p1AbsoluteSurplus = Math.abs(p1.getSurplus());
        double p2AbsoluteSurplus = Math.abs(p2.getSurplus());
        double marginOfError = 0.01;
        double difference = Math.abs(p1AbsoluteSurplus - p2AbsoluteSurplus);
        if (difference < marginOfError) {
            return Optional.empty();
        } else if (p1AbsoluteSurplus < p2AbsoluteSurplus) {
            return Optional.ofNullable(p1);
        } else {
            return Optional.ofNullable(p2);
        }
    }

    private static List<Pair> removeZeroCostElements(List<Pair> pairs) {
        List<Pair> newPairs = new ArrayList<>();
        for (int i = 0; i < pairs.size(); i++) {
            Double surplus = pairs.get(i).getSurplus();
            if (surplus != 0) {
                newPairs.add(pairs.get(i));
            }
        }
        return newPairs;
    }

    /**
     * Tabulates payments to make between all persons to balance payments and expenses.
     * @param pairs Name and Surplus/Deficit of Person.
     * @return List of payments to balance payments and expenses.
     * @throws CommandException thrown if a discrepancy in payments is observed.
     */
    private List<Payment> calculatePayments(List<Pair> pairs) throws CommandException {
        if (!checkSumIsZero(pairs)) {
            throw new CommandException("There appears to be a discrepancy within your payments.");
        }
        pairs = removeZeroCostElements(pairs);
        List<Payment> payments = new ArrayList<>();
        while (!pairs.isEmpty()) {
            pairs = sortPairs(pairs);
            if (pairs.size() == 1) {
                throw new CommandException("There appears to be a discrepancy within your payments.");
            }
            Pair pairWithLowestSurplus = pairs.get(0);
            Pair pairWithHighestSurplus = pairs.get(pairs.size() - 1);
            Payment paymentToAdd = calculatePayment(pairWithLowestSurplus, pairWithHighestSurplus);
            payments.add(paymentToAdd);
            Optional<Pair> smallerPair = getSmallerPair(pairWithLowestSurplus, pairWithHighestSurplus);
            if (smallerPair.isEmpty()) {
                pairs.remove(0);
                pairs.remove(pairs.size() - 1);
            } else if (smallerPair.get().equals(pairWithHighestSurplus)) {
                pairs.remove(pairs.size() - 1);
                double newSurplus = pairWithLowestSurplus.getSurplus() + pairWithHighestSurplus.getSurplus();
                Pair newPairWithLowestSurplus = new Pair(newSurplus, pairWithLowestSurplus.getPerson());
                pairs.remove(0);
                pairs.add(0, newPairWithLowestSurplus);
            } else if (smallerPair.get().equals(pairWithLowestSurplus)) {
                pairs.remove(0);
                double newSurplus = pairWithHighestSurplus.getSurplus() + pairWithLowestSurplus.getSurplus();
                Pair newPairWithHighestSurplus = new Pair(newSurplus, pairWithHighestSurplus.getPerson());
                pairs.remove(pairs.size() - 1);
                pairs.add(newPairWithHighestSurplus);
            }
        }
        return payments;
    }

    /**
     * Calculates individual Payments to make between persons to balance payments and expenditures.
     * @param deficitPair Name and Amount pair for person paying amount.
     * @param surplusPair Name and Amount pair for person receiving amount.
     * @return Payment to make.
     */
    private Payment calculatePayment(Pair deficitPair, Pair surplusPair) {
        Person payee = deficitPair.getPerson();
        double absoluteDeficit = Math.abs(deficitPair.getSurplus());
        Person payer = surplusPair.getPerson();
        double absoluteSurplus = Math.abs(surplusPair.getSurplus());
        Cost minimumAmount = new Cost(Math.min(absoluteDeficit, absoluteSurplus));
        return new Payment(payer, payee, minimumAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof CalculatePaymentsCommand)) {
            return false;
        }

        CalculatePaymentsCommand calculatePaymentsCommand = (CalculatePaymentsCommand) other;

        return this.group.equals(calculatePaymentsCommand.group);
    }
}
