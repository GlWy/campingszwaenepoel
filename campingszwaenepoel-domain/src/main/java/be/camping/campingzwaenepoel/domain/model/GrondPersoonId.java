package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * GrondPersoonId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class GrondPersoonId  implements java.io.Serializable {


    // Fields
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
     private int fkGrondId;
     private int fkPersoonId;


    // Constructors

    /** default constructor */
    public GrondPersoonId() {
    }

    
    /** full constructor */
    public GrondPersoonId(int id, int fkGrondId, int fkPersoonId) {
        this.id = id;
        this.fkGrondId = fkGrondId;
        this.fkPersoonId = fkPersoonId;
    }

   
    // Property accessors

    @Column(name="ID", nullable=false)

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="FK_GROND_ID", nullable=false)

    public int getFkGrondId() {
        return this.fkGrondId;
    }
    
    public void setFkGrondId(int fkGrondId) {
        this.fkGrondId = fkGrondId;
    }

    @Column(name="FK_PERSOON_ID", nullable=false)

    public int getFkPersoonId() {
        return this.fkPersoonId;
    }
    
    public void setFkPersoonId(int fkPersoonId) {
        this.fkPersoonId = fkPersoonId;
    }
   

/*

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof GrondPersoonId) ) return false;
		 GrondPersoonId castOther = ( GrondPersoonId ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getFkGrondId()==castOther.getFkGrondId()) || ( this.getFkGrondId()!=null && castOther.getFkGrondId()!=null && this.getFkGrondId().equals(castOther.getFkGrondId()) ) )
 && ( (this.getFkPersoonId()==castOther.getFkPersoonId()) || ( this.getFkPersoonId()!=null && castOther.getFkPersoonId()!=null && this.getFkPersoonId().equals(castOther.getFkPersoonId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getFkGrondId() == null ? 0 : this.getFkGrondId().hashCode() );
         result = 37 * result + ( getFkPersoonId() == null ? 0 : this.getFkPersoonId().hashCode() );
         return result;
   }   
*/




}