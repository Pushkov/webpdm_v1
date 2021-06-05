package nicomed.webpdm.enums;

public enum Months {
//    ALL("Все"),
    JANUARY("Январь"),
    FEBRARY("Февраль"),
    MARTH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNY("Июнь"),
    JULY("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OKTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь")
//    , ALL("Все"),
    ;


    private String name;
    private float size;
    Months(String _name){
        name = _name;
    }

    public String getName(){
        return this.name;
    }
}
