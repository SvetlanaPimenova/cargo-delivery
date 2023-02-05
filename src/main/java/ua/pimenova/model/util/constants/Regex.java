package ua.pimenova.model.util.constants;

/**
 * Contains all required for validation regexes
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class Regex {
    /** Use it for email only*/
    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    /** Use it for password only*/
    public static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{8,}";

    /** Use it for names and surnames*/
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,20}";

    /** Use it for phone numbers only*/
    public static final String PHONE_REGEX = "^\\+380\\d{2}\\d{3}\\d{2}\\d{2}$";
}
