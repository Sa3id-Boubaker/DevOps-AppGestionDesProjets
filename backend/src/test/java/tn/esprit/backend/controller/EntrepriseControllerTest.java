package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.service.IEntrepriseService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrepriseControllerTest {

    @Mock
    IEntrepriseService entrepriseService;          // faux service

    @InjectMocks
    EntrepriseController entrepriseController;     // le vrai controleur teste

    @Test
    void addEntreprise_appelleLeService() {
        Entreprise e = Entreprise.builder().nom("Esprit").build();
        when(entrepriseService.addEntreprise(e)).thenReturn(e);

        assertThat(entrepriseController.addEntreprise(e).getNom()).isEqualTo("Esprit");
        verify(entrepriseService).addEntreprise(e);
    }

    @Test
    void updateEntreprise_appelleLeService() {
        Entreprise e = Entreprise.builder().id(1L).nom("Esprit").build();
        when(entrepriseService.updateEntreprise(e)).thenReturn(e);

        assertThat(entrepriseController.updateEntreprise(e).getId()).isEqualTo(1L);
        verify(entrepriseService).updateEntreprise(e);
    }

    @Test
    void deleteEntreprise_appelleLeService() {
        entrepriseController.deleteEntreprise(1L);

        verify(entrepriseService).deleteEntreprise(1L);
    }

    @Test
    void getEntrepriseById_appelleLeService() {
        Entreprise e = Entreprise.builder().id(1L).nom("Esprit").build();
        when(entrepriseService.getEntrepriseById(1L)).thenReturn(e);

        assertThat(entrepriseController.getEntrepriseById(1L).getNom()).isEqualTo("Esprit");
    }

    @Test
    void getAllEntreprises_appelleLeService() {
        when(entrepriseService.getAllEntreprises()).thenReturn(List.of(new Entreprise(), new Entreprise()));

        assertThat(entrepriseController.getAllEntreprises()).hasSize(2);
    }
}
