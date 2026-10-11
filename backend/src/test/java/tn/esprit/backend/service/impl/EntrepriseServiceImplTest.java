package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.repository.EntrepriseRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrepriseServiceImplTest {

    @Mock
    EntrepriseRepository entrepriseRepository;   // faux repository : pas de base de donnees

    @InjectMocks
    EntrepriseServiceImpl entrepriseService;      // la vraie classe testee, avec le faux repository

    @Test
    void addEntreprise_enregistreEtRetourneEntreprise() {
        Entreprise e = Entreprise.builder().nom("Esprit").adresse("Tunis").build();
        when(entrepriseRepository.save(e)).thenReturn(e);

        Entreprise resultat = entrepriseService.addEntreprise(e);

        assertThat(resultat.getNom()).isEqualTo("Esprit");
        verify(entrepriseRepository).save(e);
    }

    @Test
    void updateEntreprise_enregistreLesModifications() {
        Entreprise e = Entreprise.builder().id(1L).nom("Esprit").adresse("Ariana").build();
        when(entrepriseRepository.save(e)).thenReturn(e);

        Entreprise resultat = entrepriseService.updateEntreprise(e);

        assertThat(resultat.getAdresse()).isEqualTo("Ariana");
        verify(entrepriseRepository).save(e);
    }

    @Test
    void deleteEntreprise_supprimeParId() {
        entrepriseService.deleteEntreprise(1L);

        verify(entrepriseRepository).deleteById(1L);
    }

    @Test
    void getEntrepriseById_retourneEntrepriseQuandElleExiste() {
        Entreprise e = Entreprise.builder().id(1L).nom("Esprit").build();
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(e));

        Entreprise resultat = entrepriseService.getEntrepriseById(1L);

        assertThat(resultat).isNotNull();
        assertThat(resultat.getId()).isEqualTo(1L);
    }

    @Test
    void getEntrepriseById_retourneNullQuandElleNExistePas() {
        when(entrepriseRepository.findById(99L)).thenReturn(Optional.empty());

        Entreprise resultat = entrepriseService.getEntrepriseById(99L);

        assertThat(resultat).isNull();
    }

    @Test
    void getAllEntreprises_retourneLaListe() {
        List<Entreprise> liste = List.of(
                Entreprise.builder().id(1L).nom("Esprit").build(),
                Entreprise.builder().id(2L).nom("Sofrecom").build());
        when(entrepriseRepository.findAll()).thenReturn(liste);

        List<Entreprise> resultat = entrepriseService.getAllEntreprises();

        assertThat(resultat).hasSize(2);
    }
}
