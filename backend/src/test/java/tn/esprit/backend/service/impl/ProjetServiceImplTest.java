package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.ProjetRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetServiceImplTest {

    @Mock
    ProjetRepository projetRepository;

    @InjectMocks
    ProjetServiceImpl projetService;

    @Test
    void addProjet_enregistreEtRetourneProjet() {
        Projet p = Projet.builder().sujet("Gestion des projets").build();
        when(projetRepository.save(p)).thenReturn(p);

        assertThat(projetService.addProjet(p).getSujet()).isEqualTo("Gestion des projets");
        verify(projetRepository).save(p);
    }

    @Test
    void updateProjet_enregistreLesModifications() {
        Projet p = Projet.builder().id(1L).sujet("Nouveau sujet").build();
        when(projetRepository.save(p)).thenReturn(p);

        assertThat(projetService.updateProjet(p).getSujet()).isEqualTo("Nouveau sujet");
    }

    @Test
    void deleteProjet_supprimeParId() {
        projetService.deleteProjet(1L);

        verify(projetRepository).deleteById(1L);
    }

    @Test
    void getProjetById_retourneProjetQuandIlExiste() {
        Projet p = Projet.builder().id(1L).build();
        when(projetRepository.findById(1L)).thenReturn(Optional.of(p));

        assertThat(projetService.getProjetById(1L)).isSameAs(p);
    }

    @Test
    void getProjetById_retourneNullQuandIlNExistePas() {
        when(projetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(projetService.getProjetById(99L)).isNull();
    }

    @Test
    void getAllProjets_retourneLaListe() {
        when(projetRepository.findAll()).thenReturn(List.of(new Projet(), new Projet(), new Projet()));

        assertThat(projetService.getAllProjets()).hasSize(3);
    }
}
