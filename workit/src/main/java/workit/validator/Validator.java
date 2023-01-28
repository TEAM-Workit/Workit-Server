package workit.validator;

import workit.entity.User;
import workit.entity.Work;
import workit.util.CustomException;
import workit.util.ResponseCode;

public class Validator {
    public static void validateProjectTitleLength(String title) {
        if (title.length() > 30) {
            throw new CustomException(ResponseCode.INVALID_WORK_TITLE_LENGTH);
        }
    }

    public static void validateProjectTitleNull(String title) {
        if (title.length() == 0) {
            throw new CustomException(ResponseCode.NO_VALUE_REQUIRED);
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

    public static void validateUsersWork(Work work, User user) {
        if (!work.getProject().getUser().equals(user)) {
            throw new CustomException(ResponseCode.NOT_USER_WORK);
        }
    }
}
