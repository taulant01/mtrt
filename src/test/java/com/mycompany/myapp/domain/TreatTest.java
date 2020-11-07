package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TreatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Treat.class);
        Treat treat1 = new Treat();
        treat1.setId(1L);
        Treat treat2 = new Treat();
        treat2.setId(treat1.getId());
        assertThat(treat1).isEqualTo(treat2);
        treat2.setId(2L);
        assertThat(treat1).isNotEqualTo(treat2);
        treat1.setId(null);
        assertThat(treat1).isNotEqualTo(treat2);
    }
}
