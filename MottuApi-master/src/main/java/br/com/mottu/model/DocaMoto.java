package br.com.mottu.model;

import br.com.mottu.enums.SituacaoMotoEnum;
import br.com.mottu.enums.StatusVinculoEnum;
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
public class DocaMoto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2273069510831781207L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doca_id", nullable = false)
    private Doca doca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id", nullable = false)
    private Moto moto;

    @Enumerated(EnumType.STRING)
    private StatusVinculoEnum status;

    @Enumerated(EnumType.STRING)
    private SituacaoMotoEnum situacao;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    private boolean deleted = false;
}

