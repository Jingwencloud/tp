package seedu.awe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.awe.commons.core.index.Index;
import seedu.awe.commons.util.StringUtil;
import seedu.awe.logic.parser.exceptions.EmptyGroupException;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Description;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Address;
import seedu.awe.model.person.Email;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Phone;
import seedu.awe.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidGroupName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String awe} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code awe} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String groupName} into an {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code groupName} is invalid.
     */
    public static GroupName parseGroupName(String groupName) throws ParseException {
        requireNonNull(groupName);
        String trimmedGroupName = groupName.trim();
        if (!GroupName.isValidGroupName(trimmedGroupName)) {
            throw new ParseException(GroupName.MESSAGE_CONSTRAINTS);
        }
        return new GroupName(trimmedGroupName);
    }

    /**
     * Parses {@code Collection<String> groupNames} into a {@code List<GroupName>}.
     */
    public static List<GroupName> parseGroupNames(Collection<String> groupNames) throws ParseException {
        requireNonNull(groupNames);
        final List<GroupName> groupNamesList = new ArrayList<>();
        for (String groupName : groupNames) {
            groupNamesList.add(parseGroupName(groupName));
        }
        return groupNamesList;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> names} into a {@code List<Name>}.
     * @param names Collection of strings that represent names.
     * @return List of names
     */
    public static List<Name> parseMemberNames(Collection<String> names) throws EmptyGroupException {
        requireNonNull(names);
        final Set<Name> memberNameSet = new HashSet<>();
        final List<Name> memberNameList = new ArrayList<>();
        boolean isValid = true;
        int invalidCount = 0;
        for (String personName : names) {
            try {
                new Name(personName);
            } catch (IllegalArgumentException err) {
                invalidCount++;
                isValid = false;
            }
            if (isValid) {
                memberNameSet.add(new Name(personName));
            }
            isValid = true;
        }
        if (invalidCount == names.size()) {
            throw new EmptyGroupException(
                    String.format(MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP,
                            MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES,
                            MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES));
        }
        memberNameList.addAll(memberNameSet);
        return memberNameList;
    }

    /**
     * Parses {@code Collection<String> names} into a {@code List<Name>}.
     */
    public static List<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final ArrayList<Name> nameList = new ArrayList<>();
        for (String name : names) {
            nameList.add(parseName(name));
        }
        return nameList;
    }

    /**
     * Parses a {@code String cost} into a {@code Cost}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Cost parseCost(String cost) throws ParseException {
        requireNonNull(cost);
        String trimmedCost = cost.trim();
        if (!Cost.isValidCost(trimmedCost)) {
            throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
        }
        return new Cost(trimmedCost);
    }

    /**
     * Parses {@code Collection<String> costs} into a {@code List<Cost>}.
     */
    public static List<Cost> parseCosts(Collection<String> costs) throws ParseException {
        requireNonNull(costs);
        final ArrayList<Cost> costList = new ArrayList<>();
        for (String cost : costs) {
            costList.add(parseCost(cost));
        }
        return costList;
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses a collection of {@code String name} into a list of {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static List<Name> parseExcluded(Collection<String> toExclude) throws ParseException {
        requireNonNull(toExclude);
        final ArrayList<Name> excludedList = new ArrayList<>();
        for (String excludedName : toExclude) {
            excludedList.add(parseName(excludedName));
        }
        return excludedList;
    }
}
