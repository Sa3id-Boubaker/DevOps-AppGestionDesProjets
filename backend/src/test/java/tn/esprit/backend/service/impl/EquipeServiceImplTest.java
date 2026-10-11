package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.EntrepriseRepository;
import tn.esprit.backend.repository.EquipeRepository;
import tn.esprit.backend.repository.ProjetRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeServiceImplTest {

    @Mock
    EquipeRepository equipeRepository;

    @Mock
    EntrepriseRepository entrepriseRepository;

    @Mock
    ProjetRepository projetRepository;

    @InjectMocks
    EquipeServiceImpl equipeService;

    @Test
    void addEquipe_enregistreEtRetourneEquipe() {
        Equipe e = Equipe.builder().nom("DevOps").specialite("CI/CD").build();
        when(equipeRepository.save(e)).thenReturn(e);

        assertThat(equipeService.addEquipe(e).getNom()).isEqualTo("DevOps");
        verify(equipeRepository).save(e);
    }

    @Test
    void updateEquipe_enregistreLesModifications() {
        Equipe e = Equipe.builder().id(1L).nom("DevOps").specialite("Cloud").build();
        when(equipeRepository.save(e)).thenReturn(e);

        assertThat(equipeService.updateEquipe(e).getSpecialite()).isEqualTo("Cloud");
        verify(equipeRepository).save(e);
    }

    @Test
    void deleteEquipe_supprimeParId() {
        equipeService.deleteEquipe(1L);

        verify(equipeRepository).deleteById(1L);
    }

    @Test
    void getEquipeById_retourneEquipeQuandElleExiste() {
        Equipe e = Equipe.builder().id(1L).nom("DevOps").build();
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(e));

        assertThat(equipeService.getEquipeById(1L)).isSameAs(e);
    }

    @Test
    void getEquipeById_retourneNullQuandElleNExistePas() {
        when(equipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(equipeService.getEquipeById(99L)).isNull();
    }

    @Test
    void getAllEquipes_retourneLaListe() {
        when(equipeRepository.findAll()).thenReturn(List.of(new Equipe(), new Equipe()));

        assertThat(equipeService.getAllEquipes()).hasSize(2);
    }

    @Test
    void getEquipesByEntreprise_retourneLesEquipesDeLEntreprise() {
        when(equipeRepository.findByEntrepriseId(1L)).thenReturn(List.of(new Equipe()));

        assertThat(equipeService.getEquipesByEntreprise(1L)).hasSize(1);
        verify(equipeRepository).findByEntrepriseId(1L);
    }

    @Test
    void assignEquipeToEntreprise_associeLEntrepriseALEquipe() {
        Equipe equipe = Equipe.builder().id(1L).nom("DevOps").build();
        Entreprise entreprise = Entreprise.builder().id(2L).nom("Esprit").build();
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));
        when(entrepriseRepository.findById(2L)).thenReturn(Optional.of(entreprise));
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe resultat = equipeService.assignEquipeToEntreprise(1L, 2L);

        assertThat(resultat.getEntreprise()).isSameAs(entreprise);
        verify(equipeRepository).save(equipe);
    }

    @Test
    void assignEquipeToProjet_ajouteLeProjetALEquipe() {
        // new Equipe() et pas Equipe.builder() : avec @Builder, la liste "projets"
        // n'est pas initialisee (null), voir le warning "@Builder will ignore the initializing expression"
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        Projet projet = Projet.builder().id(3L).sujet("Gestion des projets").build();
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));
        when(projetRepository.findById(3L)).thenReturn(Optional.of(projet));
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe resultat = equipeService.assignEquipeToProjet(1L, 3L);

        assertThat(resultat.getProjets()).containsExactly(projet);
        verify(equipeRepository).save(equipe);
    }
}
