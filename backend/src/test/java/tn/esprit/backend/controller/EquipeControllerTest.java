package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.service.IEquipeService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeControllerTest {

    @Mock
    IEquipeService equipeService;

    @InjectMocks
    EquipeController equipeController;

    @Test
    void addEquipe_appelleLeService() {
        Equipe e = Equipe.builder().nom("DevOps").build();
        when(equipeService.addEquipe(e)).thenReturn(e);

        assertThat(equipeController.addEquipe(e).getNom()).isEqualTo("DevOps");
        verify(equipeService).addEquipe(e);
    }

    @Test
    void updateEquipe_appelleLeService() {
        Equipe e = Equipe.builder().id(1L).nom("DevOps").build();
        when(equipeService.updateEquipe(e)).thenReturn(e);

        assertThat(equipeController.updateEquipe(e).getId()).isEqualTo(1L);
    }

    @Test
    void deleteEquipe_appelleLeService() {
        equipeController.deleteEquipe(1L);

        verify(equipeService).deleteEquipe(1L);
    }

    @Test
    void getEquipeById_appelleLeService() {
        Equipe e = Equipe.builder().id(1L).build();
        when(equipeService.getEquipeById(1L)).thenReturn(e);

        assertThat(equipeController.getEquipeById(1L)).isSameAs(e);
    }

    @Test
    void getAllEquipes_appelleLeService() {
        when(equipeService.getAllEquipes()).thenReturn(List.of(new Equipe(), new Equipe()));

        assertThat(equipeController.getAllEquipes()).hasSize(2);
    }

    @Test
    void getEquipesByEntreprise_appelleLeService() {
        when(equipeService.getEquipesByEntreprise(1L)).thenReturn(List.of(new Equipe()));

        assertThat(equipeController.getEquipesByEntreprise(1L)).hasSize(1);
    }

    @Test
    void assignEquipeToEntreprise_appelleLeService() {
        Equipe e = Equipe.builder().id(1L).build();
        when(equipeService.assignEquipeToEntreprise(1L, 2L)).thenReturn(e);

        assertThat(equipeController.assignEquipeToEntreprise(1L, 2L)).isSameAs(e);
        verify(equipeService).assignEquipeToEntreprise(1L, 2L);
    }

    @Test
    void assignEquipeToProjet_appelleLeService() {
        Equipe e = Equipe.builder().id(1L).build();
        when(equipeService.assignEquipeToProjet(1L, 3L)).thenReturn(e);

        assertThat(equipeController.assignEquipeToProjet(1L, 3L)).isSameAs(e);
        verify(equipeService).assignEquipeToProjet(1L, 3L);
    }
}
