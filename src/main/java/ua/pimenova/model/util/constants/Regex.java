package ua.pimenova.model.util.constants;

public class Regex {
    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{8,}";
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,20}";
    public static final String PHONE_REGEX = "^\\+380\\d{2}\\d{3}\\d{2}\\d{2}$";
}
