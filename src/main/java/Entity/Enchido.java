package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Enchido {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_enchido")
    private int idEnchido;
    @Basic
    @Column(name = "id_lote")
    private int idLote;
    @Basic
    @Column(name = "tipo_enchido")
    private String tipoEnchido;
    @Basic
    @Column(name = "custo_unitário")
    private double custoUnitário;
    @Basic
    @Column(name = "fase_produção")
    private String faseProdução;
    @Basic
    @Column(name = "descrição")
    private String descrição;

    public Enchido() {
        // Empty constructor required by JPA
    }

    public Enchido(String tipoEnchido, Double custoUnitário, String descrição) {
        this.tipoEnchido = tipoEnchido;
        this.custoUnitário = custoUnitário;
        this.descrição = descrição;
    }

    public int getIdEnchido() {
        return idEnchido;
    }

    public void setIdEnchido(int idEnchido) {
        this.idEnchido = idEnchido;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getTipoEnchido() {
        return tipoEnchido;
    }

    public void setTipoEnchido(String tipoEnchido) {
        this.tipoEnchido = tipoEnchido;
    }

    public double getCustoUnitário() {
        return custoUnitário;
    }

    public void setCustoUnitário(double custoUnitário) {
        this.custoUnitário = custoUnitário;
    }

    public String getFaseProdução() {
        return faseProdução;
    }

    public void setFaseProdução(String faseProdução) {
        this.faseProdução = faseProdução;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enchido enchido = (Enchido) o;
        return idEnchido == enchido.idEnchido && idLote == enchido.idLote && Double.compare(enchido.custoUnitário, custoUnitário) == 0 && Objects.equals(tipoEnchido, enchido.tipoEnchido) && Objects.equals(faseProdução, enchido.faseProdução) && Objects.equals(descrição, enchido.descrição);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnchido, idLote, tipoEnchido, custoUnitário, faseProdução, descrição);
    }
}
