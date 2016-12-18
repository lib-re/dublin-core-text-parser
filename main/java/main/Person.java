package main;

/**
 *
 */
public class Person {

    //Name Pre-fixes and titles
    static {

        //traditional
        String MR   = "Mr.";    //mister
        String MRS  = "Mrs.";   //missus
        String MS   = "Ms.";    //miss
        String MISS = "Miss";   //miss
        String DR   = "Dr.";    //doctor

        //professional
        String COACH= "Coach";  //coach
        String SUPT = "Supt.";  //superintendent
        String ADM  = "Adm.";   //administrator

        //governmental
        String ATTY = "Atty.";  //attorney
        String HON  = "Hon.";   //honorable
        String PRES = "Pres.";  //president
        String GOV  = "Gov.";   //governor
        String OFC  = "Ofc.";   //officer
        String AMB  = "Amb.";   //ambassador
        String REP  = "Rep.";   //representative

        //familial/religious
        String MSGR = "Msgr.";  //Monsignor
        String REV  = "Rev.";   //reverend
        String FR   = "Fr.";    //father
        String SR   = "Sr.";    //sister
        String BR   = "Br.";    //brother

        //military
        String MAJ  = "Maj.";   //major
        String CAPT = "Capt.";  //captain
        String CMDR = "Cmdr.";  //commander
        String LT   = "Lt.";    //lieutenant
        String LTOCL= "Lt Col.";//lieutenant colonel
        String COL  = "Col.";   //colonel
        String GEN  = "Gen.";   //general

    }

    private String uid;
    private String title;
    private String first;
    private String middle;
    private String last;
    private String suffix;
    private String buffer;

    public Person(String oneName){  }



}