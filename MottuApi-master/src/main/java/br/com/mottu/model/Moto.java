package br.com.mottu.model;

import br.com.mottu.enums.StatusMotoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2273069510831781207L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    private int ano;

    @Enumerated(EnumType.STRING)
    private StatusMotoEnum status;

    private int quilometragem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rastreador_id")
    private Rastreador rastreador;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    private boolean deleted = false;
}

