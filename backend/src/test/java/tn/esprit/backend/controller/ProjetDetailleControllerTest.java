package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.service.IProjetDetailleService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetDetailleControllerTest {

    @Mock
    IProjetDetailleService projetDetailleService;

    @InjectMocks
    ProjetDetailleController projetDetailleController;

    @Test
    void addProjetDetaille_appelleLeService() {
        ProjetDetaille pd = ProjetDetaille.builder().technologie("Angular").build();
        when(projetDetailleService.addProjetDetaille(pd)).thenReturn(pd);

        assertThat(projetDetailleController.addProjetDetaille(pd).getTechnologie()).isEqualTo("Angular");
        verify(projetDetailleService).addProjetDetaille(pd);
    }

    @Test
    void updateProjetDetaille_appelleLeService() {
        ProjetDetaille pd = ProjetDetaille.builder().id(1L).build();
        when(projetDetailleService.updateProjetDetaille(pd)).thenReturn(pd);

        assertThat(projetDetailleController.updateProjetDetaille(pd).getId()).isEqualTo(1L);
    }

    @Test
    void deleteProjetDetaille_appelleLeService() {
        projetDetailleController.deleteProjetDetaille(1L);

        verify(projetDetailleService).deleteProjetDetaille(1L);
    }

    @Test
    void getProjetDetailleById_appelleLeService() {
        ProjetDetaille pd = ProjetDetaille.builder().id(1L).build();
        when(projetDetailleService.getProjetDetailleById(1L)).thenReturn(pd);

        assertThat(projetDetailleController.getProjetDetailleById(1L)).isSameAs(pd);
    }

    @Test
    void getAllProjetsDetailles_appelleLeService() {
        when(projetDetailleService.getAllProjetsDetailles()).thenReturn(List.of(new ProjetDetaille()));

        assertThat(projetDetailleController.getAllProjetsDetailles()).hasSize(1);
    }

    @Test
    void getProjetDetaillesByProjet_appelleLeService() {
        when(projetDetailleService.getProjetDetaillesByProjet(5L)).thenReturn(List.of(new ProjetDetaille()));

        assertThat(projetDetailleController.getProjetDetaillesByProjet(5L)).hasSize(1);
    }

    @Test
    void assignProjetDetailleToProjet_appelleLeService() {
        ProjetDetaille pd = ProjetDetaille.builder().id(1L).build();
        when(projetDetailleService.assignProjetDetailleToProjet(1L, 5L)).thenReturn(pd);

        assertThat(projetDetailleController.assignProjetDetailleToProjet(1L, 5L)).isSameAs(pd);
        verify(projetDetailleService).assignProjetDetailleToProjet(1L, 5L);
    }
}
