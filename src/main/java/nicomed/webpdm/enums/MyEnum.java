package nicomed.webpdm.enums;

import java.util.HashMap;

public class MyEnum {

    public static final int MESS1 = 1 ;
    public static final int MESS2 = 2 ;
    public static final int MESS3 = 3 ;
    public static final int MESS4 = 4 ;

    public static final int[] MESSES = {MESS1,MESS2,MESS3,MESS4};

    public static final String EQUALS = "Равно" ;
    public static final String CONTAINS = "Содержит" ;
    public static final String NOT_CONTAINS = "Не содержит" ;
    public static final String STARTWITH = "Начинается с" ;
    public static final String ENDWITH = "Заканчиватся на" ;

    public static final String[] FILTER = {EQUALS,CONTAINS,NOT_CONTAINS,STARTWITH,ENDWITH};


    public static final HashMap<Integer, String> MonthLONG(){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0,"Январь");
        map.put(1,"Февраль");
        map.put(2,"Март");
        map.put(3,"Апрель");
        map.put(4,"Май");
        map.put(5,"Июнь");
        map.put(6,"Июль");
        map.put(7,"Август");
        map.put(8,"Сентябрь");
        map.put(9,"Октябрь");
        map.put(10,"Ноябрь");
        map.put(11,"Декабрь");
        return map;
    }

    public static final HashMap<Integer, String> MonthSHORT(){
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0,"Янвь");
        map.put(1,"Фев");
        map.put(2,"Март");
        map.put(3,"Апр");
        map.put(4,"Май");
        map.put(5,"Июнь");
        map.put(6,"Июль");
        map.put(7,"Авг");
        map.put(8,"Сен");
        map.put(9,"Окт");
        map.put(10,"Ноя");
        map.put(11,"Дек");
        return map;
    }


}
