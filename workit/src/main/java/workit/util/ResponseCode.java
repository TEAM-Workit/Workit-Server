package workit.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // common
    BAD_REQUEST(StatusCode.BAD_REQUEST, false, "잘못된 문법으로 인하여 서버가 요청을 이해할 수 없습니다", HttpStatus.BAD_REQUEST),
    NULL_VALUE(StatusCode.NULL_VALUE, false, "필요한 값이 없습니다", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(StatusCode.INTERNAL_SERVER_ERROR, false, "서버 내부 오류입니다", HttpStatus.INTERNAL_SERVER_ERROR),

    // auth
    LOGIN_SUCCESS(StatusCode.CREATED, true, "로그인 성공", HttpStatus.CREATED),

    UNAUTHORIZED(StatusCode.UNAUTHORIZED, false, "유효하지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),
    INVALID_SOCIAL_TYPE(StatusCode.INVALID_SOCIAL_TYPE, false, "유효하지 않는 소셜 타입입니다", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(StatusCode.LOGIN_FAILED, false, "로그인에 실패하였습니다", HttpStatus.BAD_REQUEST),
    FAILED_VALIDATE_APPLE_LOGIN(StatusCode.FAILED_VALIDATE_APPLE_LOGIN, false, "애플 로그인 내부 오류입니다", HttpStatus.BAD_REQUEST),

    // user
    GET_USER_INFO_SUCCESS(StatusCode.OK, true, "사용자 정보 조회 성공", HttpStatus.OK),
    DELETE_USER_SUCCESS(StatusCode.OK, true, "탈퇴 성공", HttpStatus.OK),
    MODIFY_USER_SUCCESS(StatusCode.OK, true, "사용자 정보 수정 성공", HttpStatus.OK),
    USER_NOT_FOUND(StatusCode.USER_NOT_FOUND, false, "해당 계정이 존재하지 않습니다", HttpStatus.BAD_REQUEST),


    // project
    INVALID_PROJECT_TITLE_LENGTH(StatusCode.INVALID_PROJECT_TITLE_LENGTH, false, "유효하지 않은 프로젝트 명입니다", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST_PROJECT_TITLE(StatusCode.ALREADY_EXIST_PROJECT_TITLE, false, "이미 생성된 프로젝트입니다", HttpStatus.BAD_REQUEST),
    NOT_USER_PROJECT(StatusCode.NOT_USER_PROJECT, false, "유저에 해당하는 프로젝트가 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    PROJECT_NOT_FOUND(StatusCode.PROJECT_NOT_FOUND, false, "해당 프로젝트가 존재하지 않습니다", HttpStatus.NOT_FOUND),

    CREATE_PROJECT_SUCCESS(StatusCode.CREATED, true, "프로젝트 생성 성공", HttpStatus.OK),
    MODIFY_PROJECT_SUCCESS(StatusCode.OK, true, "프로젝트 수정 성공", HttpStatus.OK),
    DELETE_PROJECT_SUCCESS(StatusCode.OK, true, "프로젝트 삭제 성공", HttpStatus.OK),
    GET_PROJECTS_SUCCESS(StatusCode.OK, true, "모든 프로젝트 조회 성공", HttpStatus.OK),
    GET_RECENT_PROJECTS_SUCCESS(StatusCode.OK, true, "최근 프로젝트 조회 성공", HttpStatus.OK),


    // work
    GET_ALL_WORKIT_SUCCESS(StatusCode.OK, true, "전체 워킷 조회 성공", HttpStatus.OK),
    GET_WORKIT_DETAIL_SUCCESS(StatusCode.OK, true, "워킷 상세 조회 성공", HttpStatus.OK),
    INVALID_WORK_DESCRIPTION_LENGTH(StatusCode.INVALID_WORK_DESCRIPTION_LENGTH, false, "1000자 이하의 설명으로 작성해주세요", HttpStatus.BAD_REQUEST),
    WORK_CREATE_SUCCESS(StatusCode.CREATED, true, "업무 생성 성공", HttpStatus.OK),
    WORK_MODIFY_SUCCESS(StatusCode.CREATED, true, "업무 수정 성공", HttpStatus.OK),
    DELETE_WORK_SUCCESS(StatusCode.CREATED, true, "업무 삭제 성공", HttpStatus.OK),
    NOT_USER_WORK(StatusCode.NOT_USER_WORK, false, "유저에 해당하는 업무가 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    INVALID_DATE_TYPE(StatusCode.INVALID_DATE_TYPE, false, "올바른 날짜 형식이 아닙니다", HttpStatus.BAD_REQUEST),
    NO_ABILITIES(StatusCode.NO_ABILITIES, false, "태그를 선택하지 않았습니다", HttpStatus.BAD_REQUEST),
    WORK_NOT_FOUND(StatusCode.WORK_NOT_FOUND, false, "해당 업무가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    INVALID_WORK_TITLE_LENGTH(StatusCode.INVALID_WORK_TITLE_LENGTH, false, "유효하지 않는 업무 명입니다", HttpStatus.BAD_REQUEST),


    // ability
    GET_ALL_ABILITY_SUCCESS(StatusCode.OK, true, "전체 역량 태그 조회 성공", HttpStatus.OK),
    GET_ABILITY_COLLECTION(StatusCode.OK, true, "역량 모아보기 조회 성공", HttpStatus.OK),
    GET_ABILITY_COLLECTION_DETAIL(StatusCode.OK, true, "역량 모아보기 상세 조회 성공", HttpStatus.OK),
    ABILITY_NOT_FOUND(StatusCode.ABILITY_NOT_FOUND, true, "해당 역량이 존재하지 않습니다", HttpStatus.NOT_FOUND),

    // collection
    GET_PROJECT_COLLECTION(StatusCode.OK, true, "프로젝트 모아보기 조회 성공", HttpStatus.OK),
    GET_PROJECT_COLLECTION_DETAIL(StatusCode.OK, true, "프로젝트 모아보기 상세 조회 성공", HttpStatus.OK),
    GET_PROJECT_COLLECTION_DETAIL_BY_DATE_FILTER(StatusCode.OK, true, "기간별 프로젝트 모아보기 상세 조회 성공", HttpStatus.OK),
    GET_ABILITY_COLLECTION_DETAIL_BY_DATE_FILTER(StatusCode.OK, true, "기간별 역량 모아보기 상세 조회 성공", HttpStatus.OK);

    private final StatusCode statusCode;
    private final Boolean success;
    private final String message;
    private final HttpStatus httpStatus;
}

