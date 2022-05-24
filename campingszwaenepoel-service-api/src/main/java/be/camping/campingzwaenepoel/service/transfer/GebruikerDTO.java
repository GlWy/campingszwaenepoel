package be.camping.campingzwaenepoel.service.transfer;

public class GebruikerDTO {

    // Fields    

    private Integer id;
    private String taalcode;
    private String gebruiker;
    private String wachtwoord;


   // Constructors

  
   // Property accessors

   public Integer getId() {
       return this.id;
   }
   
   public void setId(Integer id) {
       this.id = id;
   }
   
   public String getTaalcode() {
       return this.taalcode;
   }
   
   public void setTaalcode(String taalcode) {
       this.taalcode = taalcode;
   }
   
   public String getGebruiker() {
       return this.gebruiker;
   }
   
   public void setGebruiker(String gebruiker) {
       this.gebruiker = gebruiker;
   }
   
   public String getWachtwoord() {
       return this.wachtwoord;
   }
   
   public void setWachtwoord(String wachtwoord) {
       this.wachtwoord = wachtwoord;
   }

}
