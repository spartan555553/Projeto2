package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Matéria-prima", schema = "public", catalog = "proj2")
public class MatériaPrima {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_matéria_prima")
    private int idMatériaPrima;
    @Basic
    @Column(name = "id_fornecedor")
    private int idFornecedor;
    @Basic
    @Column(name = "tipo_matéria_prima")
    private String tipoMatériaPrima;
    @Basic
    @Column(name = "custo_unitário")
    private double custoUnitário;
    @Basic
    @Column(name = "descrição")
    private String descrição;

    public MatériaPrima() {
        // Empty constructor required by JPA
    }

    public MatériaPrima(int idFornecedor, String tipoMatériaPrima, Double custoUnitário, String descrição) {
        this.idFornecedor = idFornecedor;
        this.tipoMatériaPrima = tipoMatériaPrima;
        this.custoUnitário = custoUnitário;
        this.descrição = descrição;
    }

    public int getIdMatériaPrima() {
        return idMatériaPrima;
    }

    public void setIdMatériaPrima(int idMatériaPrima) {
        this.idMatériaPrima = idMatériaPrima;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getTipoMatériaPrima() {
        return tipoMatériaPrima;
    }

    public void setTipoMatériaPrima(String tipoMatériaPrima) {
        this.tipoMatériaPrima = tipoMatériaPrima;
    }

    public double getCustoUnitário() {
        return custoUnitário;
    }

    public void setCustoUnitário(double custoUnitário) {
        this.custoUnitário = custoUnitário;
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
        MatériaPrima that = (MatériaPrima) o;
        return idMatériaPrima == that.idMatériaPrima && idFornecedor == that.idFornecedor && Double.compare(that.custoUnitário, custoUnitário) == 0 && Objects.equals(tipoMatériaPrima, that.tipoMatériaPrima) && Objects.equals(descrição, that.descrição);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatériaPrima, idFornecedor, tipoMatériaPrima, custoUnitário, descrição);
    }
}
