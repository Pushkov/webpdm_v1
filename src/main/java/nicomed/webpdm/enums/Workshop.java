package nicomed.webpdm.enums;

public enum Workshop {
    ENC("ЭнЦ",0),
    StPC_1("СтПЦ-1",1),
    StPC_2("СтПЦ-2",2),
    StPC_3("СтПЦ-3",3),
    CROMC("ЦРОМЦ",4);

    private String name;
    private int id;
    Workshop(String _name, int _id){
        this.name = _name;
        this.id = _id;
    }

    public String[] getNames(){
        String[] res = new String[Workshop.values().length];
        for ( int i =0 ; i< Workshop.values().length; i ++ ) {
                res[i] = Workshop.values()[i].getName();
        }
        return res;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }
}
