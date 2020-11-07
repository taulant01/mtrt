package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Treat.
 */
@Entity
@Table(name = "treat")
public class Treat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "crock")
    private String crock;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "purchase_link")
    private String purchaseLink;

    @Column(name = "generated_link")
    private String generatedLink;

    @ManyToOne
    @JsonIgnoreProperties(value = "treats", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrock() {
        return crock;
    }

    public Treat crock(String crock) {
        this.crock = crock;
        return this;
    }

    public void setCrock(String crock) {
        this.crock = crock;
    }

    public String getTitle() {
        return title;
    }

    public Treat title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Treat description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurchaseLink() {
        return purchaseLink;
    }

    public Treat purchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
        return this;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    public String getGeneratedLink() {
        return generatedLink;
    }

    public Treat generatedLink(String generatedLink) {
        this.generatedLink = generatedLink;
        return this;
    }

    public void setGeneratedLink(String generatedLink) {
        this.generatedLink = generatedLink;
    }

    public User getUser() {
        return user;
    }

    public Treat user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Treat)) {
            return false;
        }
        return id != null && id.equals(((Treat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Treat{" +
            "id=" + getId() +
            ", crock='" + getCrock() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", purchaseLink='" + getPurchaseLink() + "'" +
            ", generatedLink='" + getGeneratedLink() + "'" +
            "}";
    }
}
