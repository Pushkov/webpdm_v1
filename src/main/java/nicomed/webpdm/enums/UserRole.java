package nicomed.webpdm.enums;

public enum UserRole {
    ENGINEER("Инженер"),
    NK("Нормоконтролер"),
    EKON("Экономист"),
    MN_BURO("Нач. бюро"),
    MN_OTD("Зам.нач."),
    MN("Нач."),
    ARCHIVE("Архивариус"),
    ADMIN("Администратор");


    private String name;
    UserRole(String _name){
        this.name = _name;
    }

    public String getName() {
        return this.name;
    }
}
