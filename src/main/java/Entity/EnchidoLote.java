package Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Enchido_Lote", schema = "public", catalog = "proj2")
public class EnchidoLote {
    @Basic
    @Column(name = "id_enchido")
    private int idEnchido;
    @Basic
    @Column(name = "id_lote")
    private int idLote;
    @Basic
    @Column(name = "quantidade")
    private int quantidade;

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
        EnchidoLote that = (EnchidoLote) o;
        return idEnchido == that.idEnchido && idLote == that.idLote && quantidade == that.quantidade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnchido, idLote, quantidade);
    }
}
