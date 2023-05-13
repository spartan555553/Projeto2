package Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Matéria-prima_Lote", schema = "public", catalog = "proj2")
public class MatériaPrimaLote {
    @Basic
    @Column(name = "id_matéria_prima")
    private int idMatériaPrima;
    @Basic
    @Column(name = "id_lote")
    private int idLote;
    @Basic
    @Column(name = "quantidade")
    private int quantidade;

    public int getIdMatériaPrima() {
        return idMatériaPrima;
    }

    public void setIdMatériaPrima(int idMatériaPrima) {
        this.idMatériaPrima = idMatériaPrima;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatériaPrimaLote that = (MatériaPrimaLote) o;
        return idMatériaPrima == that.idMatériaPrima && idLote == that.idLote && quantidade == that.quantidade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatériaPrima, idLote, quantidade);
    }
}
