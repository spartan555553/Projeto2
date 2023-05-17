package Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Lote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lote")
    private int idLote;
    @Basic
    @Column(name = "id_funcionário")
    private int idFuncionário;
    @Basic
    @Column(name = "custo")
    private double custo;
    @Basic
    @Column(name = "data_criação")
    private Date dataCriação;
    @Basic
    @Column(name = "data_validade")
    private Date dataValidade;
    @Basic
    @Column(name = "estado_lote")
    private String estadoLote;

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getIdFuncionário() {
        return idFuncionário;
    }

    public void setIdFuncionário(int idFuncionário) {
        this.idFuncionário = idFuncionário;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public Date getDataCriação() {
        return dataCriação;
    }

    public void setDataCriação(Date dataCriação) {
        this.dataCriação = dataCriação;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getEstadoLote() {
        return estadoLote;
    }

    public void setEstadoLote(String estadoLote) {
        this.estadoLote = estadoLote;
    }

    public Lote(int idFuncionário, double custo, Date dataCriação, Date dataValidade, String estadoLote) {
        this.idFuncionário = idFuncionário;
        this.custo = custo;
        this.dataCriação = dataCriação;
        this.dataValidade = dataValidade;
        this.estadoLote = estadoLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return idLote == lote.idLote && idFuncionário == lote.idFuncionário && Double.compare(lote.custo, custo) == 0 && Objects.equals(dataCriação, lote.dataCriação) && Objects.equals(dataValidade, lote.dataValidade) && Objects.equals(estadoLote, lote.estadoLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, idFuncionário, custo, dataCriação, dataValidade, estadoLote);
    }
}
