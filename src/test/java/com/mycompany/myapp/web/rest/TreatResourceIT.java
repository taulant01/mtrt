package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MtrtApp;
import com.mycompany.myapp.domain.Treat;
import com.mycompany.myapp.repository.TreatRepository;
import com.mycompany.myapp.service.TreatService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Status;
/**
 * Integration tests for the {@link TreatResource} REST controller.
 */
@SpringBootTest(classes = MtrtApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TreatResourceIT {

    private static final String DEFAULT_CROCK = "AAAAAAAAAA";
    private static final String UPDATED_CROCK = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_LINK = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_LINK = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.NEW;
    private static final Status UPDATED_STATUS = Status.PENDING;

    @Autowired
    private TreatRepository treatRepository;

    @Autowired
    private TreatService treatService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTreatMockMvc;

    private Treat treat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treat createEntity(EntityManager em) {
        Treat treat = new Treat()
            .crock(DEFAULT_CROCK)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .purchaseLink(DEFAULT_PURCHASE_LINK)
            .generatedLink(DEFAULT_GENERATED_LINK)
            .status(DEFAULT_STATUS);
        return treat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treat createUpdatedEntity(EntityManager em) {
        Treat treat = new Treat()
            .crock(UPDATED_CROCK)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .purchaseLink(UPDATED_PURCHASE_LINK)
            .generatedLink(UPDATED_GENERATED_LINK)
            .status(UPDATED_STATUS);
        return treat;
    }

    @BeforeEach
    public void initTest() {
        treat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreat() throws Exception {
        int databaseSizeBeforeCreate = treatRepository.findAll().size();
        // Create the Treat
        restTreatMockMvc.perform(post("/api/treats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treat)))
            .andExpect(status().isCreated());

        // Validate the Treat in the database
        List<Treat> treatList = treatRepository.findAll();
        assertThat(treatList).hasSize(databaseSizeBeforeCreate + 1);
        Treat testTreat = treatList.get(treatList.size() - 1);
        assertThat(testTreat.getCrock()).isEqualTo(DEFAULT_CROCK);
        assertThat(testTreat.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTreat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTreat.getPurchaseLink()).isEqualTo(DEFAULT_PURCHASE_LINK);
        assertThat(testTreat.getGeneratedLink()).isEqualTo(DEFAULT_GENERATED_LINK);
        assertThat(testTreat.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTreatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatRepository.findAll().size();

        // Create the Treat with an existing ID
        treat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatMockMvc.perform(post("/api/treats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treat)))
            .andExpect(status().isBadRequest());

        // Validate the Treat in the database
        List<Treat> treatList = treatRepository.findAll();
        assertThat(treatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTreats() throws Exception {
        // Initialize the database
        treatRepository.saveAndFlush(treat);

        // Get all the treatList
        restTreatMockMvc.perform(get("/api/treats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treat.getId().intValue())))
            .andExpect(jsonPath("$.[*].crock").value(hasItem(DEFAULT_CROCK)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].purchaseLink").value(hasItem(DEFAULT_PURCHASE_LINK)))
            .andExpect(jsonPath("$.[*].generatedLink").value(hasItem(DEFAULT_GENERATED_LINK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTreat() throws Exception {
        // Initialize the database
        treatRepository.saveAndFlush(treat);

        // Get the treat
        restTreatMockMvc.perform(get("/api/treats/{id}", treat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(treat.getId().intValue()))
            .andExpect(jsonPath("$.crock").value(DEFAULT_CROCK))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.purchaseLink").value(DEFAULT_PURCHASE_LINK))
            .andExpect(jsonPath("$.generatedLink").value(DEFAULT_GENERATED_LINK))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTreat() throws Exception {
        // Get the treat
        restTreatMockMvc.perform(get("/api/treats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreat() throws Exception {
        // Initialize the database
        treatService.save(treat);

        int databaseSizeBeforeUpdate = treatRepository.findAll().size();

        // Update the treat
        Treat updatedTreat = treatRepository.findById(treat.getId()).get();
        // Disconnect from session so that the updates on updatedTreat are not directly saved in db
        em.detach(updatedTreat);
        updatedTreat
            .crock(UPDATED_CROCK)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .purchaseLink(UPDATED_PURCHASE_LINK)
            .generatedLink(UPDATED_GENERATED_LINK)
            .status(UPDATED_STATUS);

        restTreatMockMvc.perform(put("/api/treats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreat)))
            .andExpect(status().isOk());

        // Validate the Treat in the database
        List<Treat> treatList = treatRepository.findAll();
        assertThat(treatList).hasSize(databaseSizeBeforeUpdate);
        Treat testTreat = treatList.get(treatList.size() - 1);
        assertThat(testTreat.getCrock()).isEqualTo(UPDATED_CROCK);
        assertThat(testTreat.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTreat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTreat.getPurchaseLink()).isEqualTo(UPDATED_PURCHASE_LINK);
        assertThat(testTreat.getGeneratedLink()).isEqualTo(UPDATED_GENERATED_LINK);
        assertThat(testTreat.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTreat() throws Exception {
        int databaseSizeBeforeUpdate = treatRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatMockMvc.perform(put("/api/treats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treat)))
            .andExpect(status().isBadRequest());

        // Validate the Treat in the database
        List<Treat> treatList = treatRepository.findAll();
        assertThat(treatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreat() throws Exception {
        // Initialize the database
        treatService.save(treat);

        int databaseSizeBeforeDelete = treatRepository.findAll().size();

        // Delete the treat
        restTreatMockMvc.perform(delete("/api/treats/{id}", treat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Treat> treatList = treatRepository.findAll();
        assertThat(treatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
