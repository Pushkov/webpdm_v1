package nicomed.webpdm.enums;

public enum FormatSheet {
    ND("БЧ",0f),
    A4("А4",0.125f),
    A4_3("А4х3",0.375f),
    A3("А3",0.25f),
    А3_3("А3х3",0.75f),
    А2("А2",0.5f),
    А2_3("А2х3",1.5f),
    А1("А1",1f),
    А1_3("А1х3",3f);

    private String name;
    private float size;
    FormatSheet(String _name, float _size){
        name = _name;
        size = _size;
    }

    public String getName(){
        return this.name;
    }

    public float getSizeA1(){
        return this.size;
    }
}
