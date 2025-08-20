package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Protocolo {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkFarmaceutico", nullable = false)
    private Farmaceutico farmaceutico;

    @ManyToMany
    @JoinTable(
            name = "protocolo_medicamento",
            joinColumns = @JoinColumn(name = "fkProtocolo"),
            inverseJoinColumns = @JoinColumn(name = "fkMedicamento")
    )
    private List<Medicamento> medicamentos = new ArrayList<>();

    @OneToOne(mappedBy = "protocolo")
    private Consulta consulta;

    private String codigo;
    private Date dataGerada;
    private Date dataEntrega;
    private Boolean status;

    public Long getId() {return id; }

    public void setId(Long id) {this.id = id; }

    public Farmaceutico getFarmaceutico() {return farmaceutico; }

    public void setFarmaceutico(Farmaceutico farmaceutico) {this.farmaceutico = farmaceutico; }

    public String getCodigo() {return codigo; }

    public void setCodigo(String codigo) {this.codigo = codigo; }

    public Date getDataGerada() {return dataGerada; }

    public void setDataGerada(Date dataGerada) {this.dataGerada = dataGerada; }

    public Date getDataEntrega() {return dataEntrega; }

    public void setDataEntrega(Date dataEntrega) {this.dataEntrega = dataEntrega; }

    public Boolean getStatus() {return status; }

    public void setStatus(Boolean status) {this.status = status; }

    public List<Medicamento> getMedicamentos() {return medicamentos; }

    public void setMedicamentos(List<Medicamento> medicamentos) {this.medicamentos = medicamentos; }

    public void setConsulta(Consulta consulta) {this.consulta = consulta;  }

    public Consulta getConsulta() {return consulta; }

    public void addMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    public void finalizarProtocolo(Farmaceutico farmaceutico, Date dataAtual){
        this.farmaceutico = farmaceutico;
        this.dataEntrega = dataAtual;
        status = true;
    };

}
