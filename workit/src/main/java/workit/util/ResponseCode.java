package workit.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 문법으로 인하여 서버가 요청을 이해할 수 없습니다."),
    NO_VALUE_REQUIRED(HttpStatus.BAD_REQUEST, false, "필요한 값이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버 내부 오류입니다."),

    // auth
    LOGIN_SUCCESS(HttpStatus.CREATED, true, "로그인 성공"),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, false, "로그인에 실패하였습니다."),
    DISAGREE_KAKAO_EMAIL(HttpStatus.BAD_REQUEST, false, "카카오 이메일 항목에 동의하지 않았습니다."),
    FAILED_VALIDATE_APPLE_LOGIN(HttpStatus.BAD_REQUEST, false, "애플 로그인 내부 오류입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "해당 계정이 존재하지 않습니다."),
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, false, "유효하지 않는 소셜 타입입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, false, "유효하지 않은 토큰입니다."),

    // user
    GET_USER_INFO_SUCCESS(HttpStatus.OK, true, "사용자 정보 조회 성공"),
    DELETE_USER_SUCCESS(HttpStatus.OK, true, "탈퇴 성공"),

    // project
    INVALID_PROJECT_TITLE_LENGTH(HttpStatus.BAD_REQUEST, false, "30자 이하의 프로젝트 제목으로 설정해주세요."),
    ALREADY_EXIST_PROJECT_TITLE(HttpStatus.BAD_REQUEST, false, "이미 생성된 프로젝트입니다"),
    NOT_USER_PROJECT(HttpStatus.BAD_REQUEST, false, "유저에 해당하는 프로젝트가 존재하지 않습니다"),
    PROJECT_NOT_FOUND(HttpStatus.BAD_REQUEST, false, "해당 프로젝트가 존재하지 않습니다"),
    CREATE_PROJECT_SUCCESS(HttpStatus.CREATED, true, "프로젝트 생성 성공"),
    MODIFY_PROJECT_SUCCESS(HttpStatus.OK, true, "프로젝트 수정 성공"),
    DELETE_PROJECT_SUCCESS(HttpStatus.OK, true, "프로젝트 삭제 성공"),
    GET_PROJECTS_SUCCESS(HttpStatus.OK, true, "모든 프로젝트 조회 성공"),
    GET_RECENT_PROJECTS_SUCCESS(HttpStatus.OK, true, "최근 프로젝트 조회 성공"),


    // work
    GET_ALL_WORKIT_SUCCESS(HttpStatus.OK, true, "전체 워킷 조회 성공"),
    GET_WORKIT_DETAIL_SUCCESS(HttpStatus.OK, true, "워킷 상세 조회 성공"),
    INVALID_WORK_TITLE_LENGTH(HttpStatus.BAD_REQUEST, false, "30자 이하의 업무 제목으로 설정해주세요."),
    INVALID_WORK_DESCRIPTION_LENGTH(HttpStatus.BAD_REQUEST, false, "1000자 이하의 설명으로 작성해주세요."),
    WORK_NOT_FOUND(HttpStatus.BAD_REQUEST, false, "업무를 찾을 수 없습니다."),
    NO_ABILITIES(HttpStatus.BAD_REQUEST, false, "태그를 선택하지 않았습니다."),
    WORK_CREATE_SUCCESS(HttpStatus.CREATED, true, "업무 생성 성공"),
    WORK_MODIFY_SUCCESS(HttpStatus.CREATED, true, "업무 수정 성공"),
    DELETE_WORK_SUCCESS(HttpStatus.CREATED, true, "업무 삭제 성공"),
    NOT_USERS_WORK(HttpStatus.BAD_REQUEST, false, "로그인한 사용자의 업무가 아닙니다."),
    INVALID_DATE_TYPE(HttpStatus.BAD_REQUEST, false, "올바른 날짜 형식이 아닙니다."),

    // ability
    GET_ALL_ABILITY_SUCCESS(HttpStatus.OK, true, "전체 역량 태그 조회 성공"),
    GET_ABILITY_COLLECTION(HttpStatus.OK, true, "역량 모아보기 조회 성공"),
    GET_ABILITY_COLLECTION_DETAIL(HttpStatus.OK, true, "역량 모아보기 상세 조회 성공"),
    ABILITY_NOT_FOUND(HttpStatus.OK, true, "해당 역량이 존재하지 않습니다"),
    NOT_ABILITY_PROJECT(HttpStatus.BAD_REQUEST, false, "유저에 해당하는 역량이 존재하지 않습니다"),

    // collection
    GET_PROJECT_COLLECTION(HttpStatus.OK, true, "프로젝트 모아보기 조회 성공"),
    GET_PROJECT_COLLECTION_DETAIL(HttpStatus.OK, true, "프로젝트 모아보기 상세 조회 성공"),
    GET_PROJECT_COLLECTION_DETAIL_BY_DATE_FILTER(HttpStatus.OK, true, "기간별 프로젝트 모아보기 상세 조회 성공"),
    GET_ABILITY_COLLECTION_DETAIL_BY_DATE_FILTER(HttpStatus.OK, true, "기간별 역량 모아보기 상세 조회 성공");

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;
}
