package nicomed.webpdm.enums;

public enum Years {
    ALL("Все"),
    y2014("2014"),
    y2015("2015"),
    y2016("2016"),
    y2017("2017"),
    y2018("2018"),
    y2019("2019"),
    y2020("2020"),
    y2021("2021"),
    y2022("2022");

    private String name;
    private float size;
    Years(String _name){
        name = _name;
    }

    public String getName(){
        return this.name;
    }
}
