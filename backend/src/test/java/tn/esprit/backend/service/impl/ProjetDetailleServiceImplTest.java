package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.repository.ProjetDetailleRepository;
import tn.esprit.backend.repository.ProjetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetDetailleServiceImplTest {

    @Mock
    ProjetDetailleRepository projetDetailleRepository;

    @Mock
    ProjetRepository projetRepository;

    @InjectMocks
    ProjetDetailleServiceImpl projetDetailleService;

    private ProjetDetaille exemple(Long id) {
        return ProjetDetaille.builder()
                .id(id)
                .description("Application web")
                .technologie("Spring Boot")
                .coutProvisoire(15000.0)
                .dateDebut(LocalDate.of(2026, 10, 1))
                .build();
    }

    @Test
    void addProjetDetaille_enregistreEtRetourneLeDetail() {
        ProjetDetaille pd = exemple(null);
        when(projetDetailleRepository.save(pd)).thenReturn(pd);

        assertThat(projetDetailleService.addProjetDetaille(pd).getTechnologie()).isEqualTo("Spring Boot");
        verify(projetDetailleRepository).save(pd);
    }

    @Test
    void updateProjetDetaille_enregistreLesModifications() {
        ProjetDetaille pd = exemple(1L);
        pd.setCoutProvisoire(20000.0);
        when(projetDetailleRepository.save(pd)).thenReturn(pd);

        assertThat(projetDetailleService.updateProjetDetaille(pd).getCoutProvisoire()).isEqualTo(20000.0);
    }

    @Test
    void deleteProjetDetaille_supprimeParId() {
        projetDetailleService.deleteProjetDetaille(1L);

        verify(projetDetailleRepository).deleteById(1L);
    }

    @Test
    void getProjetDetailleById_retourneLeDetailQuandIlExiste() {
        ProjetDetaille pd = exemple(1L);
        when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(pd));

        assertThat(projetDetailleService.getProjetDetailleById(1L)).isSameAs(pd);
    }

    @Test
    void getProjetDetailleById_retourneNullQuandIlNExistePas() {
        when(projetDetailleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(projetDetailleService.getProjetDetailleById(99L)).isNull();
    }

    @Test
    void getAllProjetsDetailles_retourneLaListe() {
        when(projetDetailleRepository.findAll()).thenReturn(List.of(exemple(1L), exemple(2L)));

        assertThat(projetDetailleService.getAllProjetsDetailles()).hasSize(2);
    }

    @Test
    void getProjetDetaillesByProjet_retourneLesDetailsDuProjet() {
        when(projetDetailleRepository.findByProjetId(5L)).thenReturn(List.of(exemple(1L)));

        assertThat(projetDetailleService.getProjetDetaillesByProjet(5L)).hasSize(1);
        verify(projetDetailleRepository).findByProjetId(5L);
    }

    @Test
    void assignProjetDetailleToProjet_associeLeProjet() {
        ProjetDetaille pd = exemple(1L);
        Projet projet = Projet.builder().id(5L).sujet("Gestion des projets").build();
        when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(pd));
        when(projetRepository.findById(5L)).thenReturn(Optional.of(projet));
        when(projetDetailleRepository.save(pd)).thenReturn(pd);

        ProjetDetaille resultat = projetDetailleService.assignProjetDetailleToProjet(1L, 5L);

        assertThat(resultat.getProjet()).isSameAs(projet);
        verify(projetDetailleRepository).save(pd);
    }
}
