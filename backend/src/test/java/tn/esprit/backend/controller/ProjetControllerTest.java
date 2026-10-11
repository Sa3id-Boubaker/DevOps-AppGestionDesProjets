package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IProjetService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetControllerTest {

    @Mock
    IProjetService projetService;

    @InjectMocks
    ProjetController projetController;

    @Test
    void addProjet_appelleLeService() {
        Projet p = Projet.builder().sujet("Gestion des projets").build();
        when(projetService.addProjet(p)).thenReturn(p);

        assertThat(projetController.addProjet(p).getSujet()).isEqualTo("Gestion des projets");
        verify(projetService).addProjet(p);
    }

    @Test
    void updateProjet_appelleLeService() {
        Projet p = Projet.builder().id(1L).build();
        when(projetService.updateProjet(p)).thenReturn(p);

        assertThat(projetController.updateProjet(p).getId()).isEqualTo(1L);
    }

    @Test
    void deleteProjet_appelleLeService() {
        projetController.deleteProjet(1L);

        verify(projetService).deleteProjet(1L);
    }

    @Test
    void getProjetById_appelleLeService() {
        Projet p = Projet.builder().id(1L).build();
        when(projetService.getProjetById(1L)).thenReturn(p);

        assertThat(projetController.getProjetById(1L)).isSameAs(p);
    }

    @Test
    void getAllProjets_appelleLeService() {
        when(projetService.getAllProjets()).thenReturn(List.of(new Projet()));

        assertThat(projetController.getAllProjets()).hasSize(1);
    }
}
