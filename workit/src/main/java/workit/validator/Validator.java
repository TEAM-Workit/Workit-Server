package workit.validator;

import workit.util.CustomException;
import workit.util.ResponseCode;

public class Validator {
    public static void validateProjectTitleLength(String title) {
        if (title.length() > 30) {
            throw new CustomException(ResponseCode.INVALID_WORK_TITLE_LENGTH);
        }
    }

    public static void validateWorkTitleLength(String title) {
        if (title.length() > 30) {
            throw new CustomException(ResponseCode.INVALID_WORK_TITLE_LENGTH);
        }
    }

    public static void validateWorkDescriptionLength(String description) {
        if (description.length() > 1000) {
            throw new CustomException(ResponseCode.INVALID_WORK_DESCRIPTION_LENGTH);
        }
    }
}
