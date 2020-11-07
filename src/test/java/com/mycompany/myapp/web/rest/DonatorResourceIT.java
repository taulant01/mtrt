package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MtrtApp;
import com.mycompany.myapp.domain.Donator;
import com.mycompany.myapp.repository.DonatorRepository;
import com.mycompany.myapp.service.DonatorService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DonatorResource} REST controller.
 */
@SpringBootTest(classes = MtrtApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DonatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANONYMOUS = false;
    private static final Boolean UPDATED_ANONYMOUS = true;

    @Autowired
    private DonatorRepository donatorRepository;

    @Autowired
    private DonatorService donatorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonatorMockMvc;

    private Donator donator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donator createEntity(EntityManager em) {
        Donator donator = new Donator()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .amount(DEFAULT_AMOUNT)
            .message(DEFAULT_MESSAGE)
            .anonymous(DEFAULT_ANONYMOUS);
        return donator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donator createUpdatedEntity(EntityManager em) {
        Donator donator = new Donator()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .message(UPDATED_MESSAGE)
            .anonymous(UPDATED_ANONYMOUS);
        return donator;
    }

    @BeforeEach
    public void initTest() {
        donator = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonator() throws Exception {
        int databaseSizeBeforeCreate = donatorRepository.findAll().size();
        // Create the Donator
        restDonatorMockMvc.perform(post("/api/donators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donator)))
            .andExpect(status().isCreated());

        // Validate the Donator in the database
        List<Donator> donatorList = donatorRepository.findAll();
        assertThat(donatorList).hasSize(databaseSizeBeforeCreate + 1);
        Donator testDonator = donatorList.get(donatorList.size() - 1);
        assertThat(testDonator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDonator.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testDonator.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testDonator.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDonator.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testDonator.isAnonymous()).isEqualTo(DEFAULT_ANONYMOUS);
    }

    @Test
    @Transactional
    public void createDonatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donatorRepository.findAll().size();

        // Create the Donator with an existing ID
        donator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonatorMockMvc.perform(post("/api/donators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donator)))
            .andExpect(status().isBadRequest());

        // Validate the Donator in the database
        List<Donator> donatorList = donatorRepository.findAll();
        assertThat(donatorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDonators() throws Exception {
        // Initialize the database
        donatorRepository.saveAndFlush(donator);

        // Get all the donatorList
        restDonatorMockMvc.perform(get("/api/donators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].anonymous").value(hasItem(DEFAULT_ANONYMOUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDonator() throws Exception {
        // Initialize the database
        donatorRepository.saveAndFlush(donator);

        // Get the donator
        restDonatorMockMvc.perform(get("/api/donators/{id}", donator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.anonymous").value(DEFAULT_ANONYMOUS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDonator() throws Exception {
        // Get the donator
        restDonatorMockMvc.perform(get("/api/donators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonator() throws Exception {
        // Initialize the database
        donatorService.save(donator);

        int databaseSizeBeforeUpdate = donatorRepository.findAll().size();

        // Update the donator
        Donator updatedDonator = donatorRepository.findById(donator.getId()).get();
        // Disconnect from session so that the updates on updatedDonator are not directly saved in db
        em.detach(updatedDonator);
        updatedDonator
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .message(UPDATED_MESSAGE)
            .anonymous(UPDATED_ANONYMOUS);

        restDonatorMockMvc.perform(put("/api/donators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonator)))
            .andExpect(status().isOk());

        // Validate the Donator in the database
        List<Donator> donatorList = donatorRepository.findAll();
        assertThat(donatorList).hasSize(databaseSizeBeforeUpdate);
        Donator testDonator = donatorList.get(donatorList.size() - 1);
        assertThat(testDonator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDonator.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDonator.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testDonator.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDonator.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDonator.isAnonymous()).isEqualTo(UPDATED_ANONYMOUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDonator() throws Exception {
        int databaseSizeBeforeUpdate = donatorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonatorMockMvc.perform(put("/api/donators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donator)))
            .andExpect(status().isBadRequest());

        // Validate the Donator in the database
        List<Donator> donatorList = donatorRepository.findAll();
        assertThat(donatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonator() throws Exception {
        // Initialize the database
        donatorService.save(donator);

        int databaseSizeBeforeDelete = donatorRepository.findAll().size();

        // Delete the donator
        restDonatorMockMvc.perform(delete("/api/donators/{id}", donator.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Donator> donatorList = donatorRepository.findAll();
        assertThat(donatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
