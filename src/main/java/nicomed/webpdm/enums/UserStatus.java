package nicomed.webpdm.enums;

public enum UserStatus {
    ACTIVE ("Активен"),
    BANNED("Отключен"),
    DELETED("Удален");

    private String name;
    UserStatus(String _name){
        this.name = _name;
    }

    public String getName() {
        return this.name;
    }
}
