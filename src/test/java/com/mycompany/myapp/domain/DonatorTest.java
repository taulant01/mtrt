package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DonatorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donator.class);
        Donator donator1 = new Donator();
        donator1.setId(1L);
        Donator donator2 = new Donator();
        donator2.setId(donator1.getId());
        assertThat(donator1).isEqualTo(donator2);
        donator2.setId(2L);
        assertThat(donator1).isNotEqualTo(donator2);
        donator1.setId(null);
        assertThat(donator1).isNotEqualTo(donator2);
    }
}
