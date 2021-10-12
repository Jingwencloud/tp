package seedu.awe.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.logic.commands.CommandTestUtil.INVALID_NAME_JAMES;
import static seedu.awe.logic.commands.CommandTestUtil.INVALID_NAME_JOHN;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.awe.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.awe.testutil.Assert.assertThrows;
import static seedu.awe.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.awe.logic.parser.exceptions.EmptyGroupException;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Address;
import seedu.awe.model.person.Email;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Phone;
import seedu.awe.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String INVALID_GROUPNAME = "B@li";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";

    private static final String VALID_GROUPNAME = "Bali";

    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseGroupName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupName((String) null));
    }

    @Test
    public void parseGroupName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroupName(INVALID_GROUPNAME));
    }

    @Test
    public void parseGroupName_validValueWithoutWhitespace_returnsName() throws Exception {
        GroupName expectedGroupName = new GroupName(VALID_GROUPNAME);
        assertEquals(expectedGroupName, ParserUtil.parseGroupName(VALID_GROUPNAME));
    }

    @Test
    public void parseGroupName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String groupNameWithWhitespace = WHITESPACE + VALID_GROUPNAME + WHITESPACE;
        GroupName expectedGroupName = new GroupName(VALID_GROUPNAME);
        assertEquals(expectedGroupName, ParserUtil.parseGroupName(groupNameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseMemberNames_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMemberNames(null));
    }

    @Test
    public void parseMemberNames_collectionWithInvalidMemberNames_throwsEmptyGroupException() {
        assertThrows(EmptyGroupException.class, () -> ParserUtil.parseMemberNames(Arrays.asList(INVALID_NAME_JAMES,
                INVALID_NAME_JOHN)));
    }

    @Test
    public void parseMemberNames_emptyCollection_throwsEmptyGroupException() {
        assertThrows(EmptyGroupException.class, () -> ParserUtil.parseMemberNames(Collections.emptyList()));
    }

    @Test
    public void parseMemberNames_collectionWithValidMemberNames_returnsMemberNames() {
        Set<Name> actualNameSet = Set.copyOf(
                ParserUtil.parseMemberNames(Arrays.asList(VALID_NAME_ALICE, VALID_NAME_AMY)));
        Set<Name> expectedNameSet = Set.of(new Name(VALID_NAME_ALICE), new Name(VALID_NAME_AMY));

        assertEquals(actualNameSet, expectedNameSet);
    }

    @Test
    public void parseMemberNames_collectionWithDuplicateMemberNames_returnsUniqueMemberNames() {
        List<Name> actualNameList = ParserUtil.parseMemberNames(Arrays.asList(VALID_NAME_ALICE, VALID_NAME_ALICE));
        List<Name> expectedNameList = Arrays.asList(new Name(VALID_NAME_ALICE));

        assertEquals(actualNameList, expectedNameList);
    }
}
