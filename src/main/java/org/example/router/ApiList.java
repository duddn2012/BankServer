package org.example.router;

public enum ApiList {

    /*
     * ERROR CASE
     */

    //유효하지 않은 값
    INVALID(-1),


    //접속 종료
    CLIENT_CONNECT_EXIT(100),

    /*
     * User
     */

    //ID 세팅
    SET_USER_ID(200),

    //Name 세팅
    SET_USER_NAME(201),

    //Email 세팅
    SET_USER_EMAIL(202),

    /*
     * Account
     */
    
    //Service 선택
    SET_USER_SERVICE(300),

    //잔금 세팅
    SET_ACCOUNT_BALANCE(301),
    
    //입금
    DEPOSIT_ACCOUNT(302)
    ;


    private final int value;

    ApiList(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    static ApiList getMappedValue(int inputValue) {
        for (ApiList mapping : ApiList.values()) {
            if (mapping.getValue() == inputValue) {
                return mapping;
            }
        }
        return INVALID;
    }
}